package com.sparta.springnewsfeed.dto;

public class UserPasswordUpdateResponseDto {

    private String message;
    private String email;

    public UserPasswordUpdateResponseDto(String message, String email) {
        this.message = message;
        this.email = email;
    }
}
