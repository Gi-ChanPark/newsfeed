package com.sparta.springnewsfeed.dto;

import lombok.Getter;

@Getter
public class UserIntroduceUpdateResponseDto {

    private String message;
    private String email;
    private String introduce;

    public UserIntroduceUpdateResponseDto(String message, String email, String introduce) {
        this.message = message;
        this.email = email;
        this.introduce = introduce;
    }
}
