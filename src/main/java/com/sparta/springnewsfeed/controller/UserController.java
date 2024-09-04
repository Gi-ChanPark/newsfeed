package com.sparta.springnewsfeed.controller;

import com.sparta.springnewsfeed.dto.*;
import com.sparta.springnewsfeed.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/users/signup")
    public ResponseEntity<UserSignupResponseDto> signup(@RequestBody UserSignupRequestDto requestDto) {
        UserSignupResponseDto responseDto = userService.signup(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto requestDto) {
        UserLoginResponseDto responseDto = userService.login(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<Void> updatePassword(@RequestHeader("Authorization") String token,
                                               @PathVariable Long userId,
                                               @RequestBody UserPasswordUpdateRequestDto requestDto) {
        userService.updatePassword(token, requestDto);
        return ResponseEntity.ok().build();
    }

}
