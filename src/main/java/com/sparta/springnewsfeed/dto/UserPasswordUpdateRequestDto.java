package com.sparta.springnewsfeed.dto;

import lombok.Getter;

@Getter
public class UserPasswordUpdateRequestDto {

    private String oldPassword;
    private String newPassword;
}
