package com.sparta.springnewsfeed.service;

import com.sparta.springnewsfeed.config.EmailAlreadyExistsException;
import com.sparta.springnewsfeed.config.PasswordEncoderUtil;
import com.sparta.springnewsfeed.dto.UserSignupRequestDto;
import com.sparta.springnewsfeed.dto.UserSignupResponseDto;
import com.sparta.springnewsfeed.entity.User;
import com.sparta.springnewsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoderUtil passwordEncoder;

    @Transactional
    public UserSignupResponseDto signup(UserSignupRequestDto requestDto) {
        // email 중복체크
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new EmailAlreadyExistsException("이미 사용중인 이메일입니다");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(requestDto.getEmail(), requestDto.getPassword(), requestDto.getNickname(), null);
        userRepository.save(user);

        return new UserSignupResponseDto(user.getId(), user.getEmail(), user.getNickname());

    }
}
