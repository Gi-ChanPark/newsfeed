package com.sparta.springnewsfeed.dto;

import lombok.Getter;

@Getter
public class UserPasswordUpdateResponseDto {

    private String message;
    private String email;

    public UserPasswordUpdateResponseDto(String message, String email) {
        this.message = message;
        this.email = email;
    }
}
