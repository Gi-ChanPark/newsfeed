package com.sparta.springnewsfeed.controller;

import com.sparta.springnewsfeed.config.JwtUtil;
import com.sparta.springnewsfeed.dto.*;
import com.sparta.springnewsfeed.entity.User;
import com.sparta.springnewsfeed.repository.UserRepository;
import com.sparta.springnewsfeed.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    // 회원가입
    @PostMapping("/users/signup")
    public ResponseEntity<UserSignupResponseDto> signup(@RequestBody UserSignupRequestDto requestDto) {
        UserSignupResponseDto responseDto = userService.signup(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 로그인
    @PostMapping("/users/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto requestDto) {
        UserLoginResponseDto responseDto = userService.login(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 비밀번호 수정
    @PutMapping("/users/{userId}")
    public ResponseEntity<UserPasswordUpdateResponseDto> updatePassword(@RequestHeader("Authorization") String token,
                                                                        @PathVariable Long userId,
                                                                        @RequestBody UserPasswordUpdateRequestDto requestDto) {
        UserPasswordUpdateResponseDto responseDto = userService.updatePassword(token, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 유저 조회
    @GetMapping("/users")
    public ResponseEntity<UserRequestDto> getUser(@RequestHeader("Authorization") String token) {
        UserRequestDto responseDto = userService.getUser(token);
        return ResponseEntity.ok(responseDto);

    }


}
