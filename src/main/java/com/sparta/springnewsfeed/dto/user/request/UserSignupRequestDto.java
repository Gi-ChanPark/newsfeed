package com.sparta.springnewsfeed.dto.user.request;

import lombok.Getter;

@Getter
public class UserSignupRequestDto {

    private String email;
    private String password;
    private String nickname;

}
