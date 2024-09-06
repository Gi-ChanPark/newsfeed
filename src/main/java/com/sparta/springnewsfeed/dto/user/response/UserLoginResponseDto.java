package com.sparta.springnewsfeed.dto.user.response;

import lombok.Getter;

@Getter
public class UserLoginResponseDto {

    private String token;
    private String email;
    private String nickname;

    public UserLoginResponseDto(String token, String email, String nickname) {
        this.token = token;
        this.email = email;
        this.nickname = nickname;
    }
}
