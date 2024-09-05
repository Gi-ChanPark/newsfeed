package com.sparta.springnewsfeed.service;

import com.sparta.springnewsfeed.config.JwtUtil;
import com.sparta.springnewsfeed.config.PasswordEncoder;
import com.sparta.springnewsfeed.dto.*;
import com.sparta.springnewsfeed.entity.User;
import com.sparta.springnewsfeed.exception.ErrorCode;
import com.sparta.springnewsfeed.exception.custom.AlreadyExistException;
import com.sparta.springnewsfeed.exception.custom.InvalidCredentialsException;
import com.sparta.springnewsfeed.exception.custom.NoEntityException;
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
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public UserSignupResponseDto signup(UserSignupRequestDto requestDto) {
        // email 중복체크
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new AlreadyExistException(ErrorCode.EMAIL_DUPLICATED);
        }
        if (userRepository.existsByNickname(requestDto.getNickname())) {
            throw new AlreadyExistException(ErrorCode.NICKNAME_DUPLICATED);
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
                String token = jwtUtil.createToken(user.getId(), user.getEmail());
                return new UserLoginResponseDto(token, user.getEmail(), user.getNickname());
            }
        }
        throw new InvalidCredentialsException(ErrorCode.INVALID_EMAIL_PASSWORD);
    }

    // 비밀번호 수정
    @Transactional
    public UserPasswordUpdateResponseDto updatePassword(Long authUserId, Long userId, UserPasswordUpdateRequestDto requestDto) {
        if (!userId.equals(authUserId)) {
            throw new InvalidCredentialsException(ErrorCode.USER_NOT_MATCH);
        }
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NoEntityException(ErrorCode.USER_NOT_FOUND)
        );
        //권한 체크
        String oldPassword = requestDto.getOldPassword();
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidCredentialsException(ErrorCode.INVALID_EMAIL_PASSWORD);
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
                () -> new NoEntityException(ErrorCode.USER_NOT_FOUND)
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
    public UserIntroduceUpdateResponseDto updateIntroduce(Long authUserId, Long userId, UserIntroduceUpdateRequestDto
            requestDto) {
        if (!userId.equals(authUserId)) {
            throw new InvalidCredentialsException(ErrorCode.USER_NOT_MATCH);
        }
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NoEntityException(ErrorCode.USER_NOT_FOUND)
        );
        // 비밀번호 체크
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException(ErrorCode.INVALID_EMAIL_PASSWORD);
        }

        user.setIntroduce(requestDto.getIntroduce());
        userRepository.save(user);

        return new UserIntroduceUpdateResponseDto("소개가 업데이트 되었습니다.", user.getEmail(), user.getIntroduce());
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(Long authUserId, Long userId, String enteredPassword) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NoEntityException(ErrorCode.USER_NOT_FOUND)
        );
        if (!userId.equals(authUserId)) {
            throw new InvalidCredentialsException(ErrorCode.USER_NOT_MATCH);
        }
        if (user.isDeleted()) {
            throw new IllegalArgumentException("이미 탈퇴한 사용자입니다.");
        }
        if (!passwordEncoder.matches(enteredPassword, user.getPassword())) {
            throw new InvalidCredentialsException(ErrorCode.INVALID_EMAIL_PASSWORD);
        }

        user.setDeleted(true);
        userRepository.save(user);
    }

}
