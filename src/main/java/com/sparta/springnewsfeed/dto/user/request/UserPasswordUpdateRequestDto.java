package com.sparta.springnewsfeed.dto.user.request;

import lombok.Getter;

@Getter
public class UserPasswordUpdateRequestDto {

    private String oldPassword;
    private String newPassword;
}
