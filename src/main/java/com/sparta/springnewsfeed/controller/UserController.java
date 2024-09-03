package com.sparta.springnewsfeed.controller;

import com.sparta.springnewsfeed.dto.UserLoginRequestDto;
import com.sparta.springnewsfeed.dto.UserLoginResponseDto;
import com.sparta.springnewsfeed.dto.UserSignupRequestDto;
import com.sparta.springnewsfeed.dto.UserSignupResponseDto;
import com.sparta.springnewsfeed.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
