package com.sparta.springnewsfeed.service;

import com.sparta.springnewsfeed.config.EmailAlreadyExistsException;
import com.sparta.springnewsfeed.config.InvalidCredentialsException;
import com.sparta.springnewsfeed.config.PasswordEncoderUtil;
import com.sparta.springnewsfeed.dto.UserLoginRequestDto;
import com.sparta.springnewsfeed.dto.UserSignupRequestDto;
import com.sparta.springnewsfeed.dto.UserSignupResponseDto;
import com.sparta.springnewsfeed.entity.User;
import com.sparta.springnewsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoderUtil passwordEncoderUtil;

    @Transactional
    public UserSignupResponseDto signup(UserSignupRequestDto requestDto) {
        // email 중복체크
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new EmailAlreadyExistsException("이미 사용중인 이메일입니다");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoderUtil.encode(requestDto.getPassword());

        User user = new User(requestDto.getEmail(), requestDto.getPassword(), requestDto.getNickname(), null);
        userRepository.save(user);

        return new UserSignupResponseDto(user.getId(), user.getEmail(), user.getNickname());
    }

    @Transactional
    public String login(UserLoginRequestDto requestDto) {
        Optional<User> userOptional = userRepository.findByEmail(requestDto.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoderUtil.matches(requestDto.getPassword(), user.getPassword())) {
                return generateToken(user);
            }
        }
        throw new InvalidCredentialsException("잘못된 이메일 또는 비밀번호입니다");
    }

    private String generateToken(User user) {
        return "generated-jwt-token";
    }


}
