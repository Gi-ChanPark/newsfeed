package com.sparta.springnewsfeed.service;

import com.sparta.springnewsfeed.exception.EmailAlreadyExistsException;
import com.sparta.springnewsfeed.exception.InvalidCredentialsException;
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
    public void updatePassword(String token, UserPasswordUpdateRequestDto requestDto) {
        Long userId = jwtUtil.validateTokenAndGetUserId(token);
        User user =userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 기존 비밀번호 확인
        if (!requestDto.getOldPassword().equals(user.getPassword())) {
            throw new InvalidCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        // 새 비밀번호 업데이트
        user.setPassword(requestDto.getNewPassword());
        userRepository.save(user);
    }


}
