package com.sparta.springnewsfeed.service;

import com.sparta.springnewsfeed.config.PasswordEncoder;
import com.sparta.springnewsfeed.exception.EmailAlreadyExistsException;
import com.sparta.springnewsfeed.exception.InvalidCredentialsException;
import com.sparta.springnewsfeed.config.JwtUtil;
import com.sparta.springnewsfeed.dto.*;
import com.sparta.springnewsfeed.entity.User;
import com.sparta.springnewsfeed.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public UserSignupResponseDto signup(UserSignupRequestDto requestDto) {
        // email 중복체크
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new EmailAlreadyExistsException("이미 사용중인 이메일입니다");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(requestDto.getEmail(), encodedPassword, requestDto.getNickname(), null);
        userRepository.save(user);

        return new UserSignupResponseDto(user.getId(), user.getEmail(), user.getNickname());
    }

    // 로그인
    @Transactional
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        Optional<User> userOptional = userRepository.findByEmail(requestDto.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
                String token = jwtUtil.createToken(user.getId());
                return new UserLoginResponseDto(token, user.getEmail(), user.getNickname());
            }
        }
        throw new InvalidCredentialsException("잘못된 이메일 또는 비밀번호입니다");
    }

    // 비밀번호 수정
    @Transactional
    public UserPasswordUpdateResponseDto updatePassword(Long userId, UserPasswordUpdateRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("사용자가 없습니다.")
        );
        // 기존 비밀번호 확인
        if (!passwordEncoder.matches(requestDto.getOldPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        // 새 비밀번호 업데이트
        String newEncodedPassword = passwordEncoder.encode(requestDto.getNewPassword());
        user.setPassword(newEncodedPassword);
        userRepository.save(user);

        return new UserPasswordUpdateResponseDto("비밀번호가 업데이트 되었습니다.", user.getEmail());
    }

    // 유저 조회
    @Transactional
    public UserRequestDto getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("사용자가 없습니다.")
        );
        return new UserRequestDto(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getCreatedAt() != null ? user.getCreatedAt().toString() : "생성일 없음",
                user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : "업데이트일 없음",
                user.getIntroduce()
        );
    }

    // 소개 수정
    @Transactional
    public UserIntroduceUpdateResponseDto updateIntroduce(Long authId, Long userId, UserIntroduceUpdateRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("사용자가 없습니다.")
        );
        // 비밀번호 체크
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        user.setIntroduce(requestDto.getIntroduce());
        userRepository.save(user);

        return new UserIntroduceUpdateResponseDto("소개가 업데이트 되었습니다.", user.getEmail(), user.getIntroduce());
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(Long authUserId, Long userId, String enteredPassword) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("사용자가 없습니다.")
        );
        if (!userId.equals(authUserId)) {
            throw new InvalidCredentialsException("삭제 권한이 없는 사용자입니다.");
        }
        if (user.isDeleted()) {
            throw new IllegalArgumentException("이미 탈퇴한 사용자입니다.");
        }
        if (!passwordEncoder.matches(enteredPassword, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        user.setDeleted(true);
        userRepository.save(user);

    }

}
