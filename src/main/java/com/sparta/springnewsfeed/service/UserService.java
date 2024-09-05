package com.sparta.springnewsfeed.service;

import com.sparta.springnewsfeed.config.EmailAlreadyExistsException;
import com.sparta.springnewsfeed.config.InvalidCredentialsException;
import com.sparta.springnewsfeed.config.JwtUtil;
import com.sparta.springnewsfeed.dto.*;
import com.sparta.springnewsfeed.entity.User;
import com.sparta.springnewsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserSignupResponseDto signup(UserSignupRequestDto requestDto) {
        // email 중복체크
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new EmailAlreadyExistsException("이미 사용중인 이메일입니다");
        }

        User user = new User(requestDto.getEmail(),requestDto.getPassword(), requestDto.getNickname(), null);
        userRepository.save(user);

        return new UserSignupResponseDto(user.getId(), user.getEmail(), user.getNickname());
    }

    @Transactional
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        Optional<User> userOptional = userRepository.findByEmail(requestDto.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (requestDto.getPassword().equals(user.getPassword())) {
                String token = jwtUtil.createToken(user.getId());
                return new UserLoginResponseDto(token, user.getEmail(), user.getNickname());
            }
        }
        throw new InvalidCredentialsException("잘못된 이메일 또는 비밀번호입니다");
    }

    @Transactional
    public UserPasswordUpdateResponseDto updatePassword(String token, UserPasswordUpdateRequestDto requestDto) {
        Long userId = jwtUtil.validateTokenAndGetUserId(token);
        User user =userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 기존 비밀번호 확인
        if (!requestDto.getOldPassword().equals(user.getPassword())) {
            throw new InvalidCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        // 새 비밀번호 업데이트
        user.setPassword(requestDto.getNewPassword());
        userRepository.save(user);

        return new UserPasswordUpdateResponseDto("비밀번호가 업데이트 되었습니다.", user.getEmail());
    }

    @Transactional
    public UserRequestDto getUser(String token) {
        Long userId = jwtUtil.validateTokenAndGetUserId(token);
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        return new UserRequestDto(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getCreatedAt() != null ? user.getCreatedAt().toString() : "생성일 없음",
                user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : "업데이트일 없음",
                user.getIntroduce()
        );
    }

    @Transactional
    public UserIntroduceUpdateResponseDto updateIntroduce(String token, UserIntroduceUpdateRequestDto requestDto) {
        Long userId = jwtUtil.validateTokenAndGetUserId(token);
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        user.setIntroduce(requestDto.getIntroduce());
        userRepository.save(user);

        return new UserIntroduceUpdateResponseDto("소개가 업데이트 되었습니다.", user.getEmail(), user.getIntroduce());
    }

    @Transactional
    public void deleteUser(String token, Long userId) {
        Long authUserId = jwtUtil.validateTokenAndGetUserId(token);

        if (!authUserId.equals(userId)) {
            throw new IllegalArgumentException("본인만 탈퇴할 수 있습니다.");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        userRepository.delete(user);
    }


}
