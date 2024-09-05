package com.sparta.springnewsfeed.controller;

import com.sparta.springnewsfeed.annotation.Auth;
import com.sparta.springnewsfeed.config.JwtUtil;
import com.sparta.springnewsfeed.dto.*;
import com.sparta.springnewsfeed.repository.UserRepository;
import com.sparta.springnewsfeed.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<UserPasswordUpdateResponseDto> updatePassword(@Auth AuthUser user,
                                                                        @PathVariable Long userId,
                                                                        @RequestBody UserPasswordUpdateRequestDto requestDto) {
        UserPasswordUpdateResponseDto responseDto = userService.updatePassword(user.getId(), userId,requestDto);
        return ResponseEntity.ok(responseDto);
    }


    // 유저 조회
    @GetMapping("/users")
    public ResponseEntity<UserRequestDto> getUser(@Auth AuthUser user) {
        UserRequestDto responseDto = userService.getUser(user.getId());
        return ResponseEntity.ok(responseDto);

    }

    // 소개 수정
    @PutMapping("/users/{userId}/profile")
    public ResponseEntity<UserIntroduceUpdateResponseDto> updateIntroduce(@Auth AuthUser user,
                                                                          @PathVariable Long userId,
                                                                          @RequestBody UserIntroduceUpdateRequestDto requestDto) {
        UserIntroduceUpdateResponseDto responseDto = userService.updateIntroduce(user.getId(), userId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // 회원 탈퇴
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@Auth AuthUser user,
                                             @PathVariable Long userId,
                                             @RequestBody String enteredPassword) {
        userService.deleteUser(user.getId(), userId, enteredPassword);
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }

}
