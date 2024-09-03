package com.sparta.springnewsfeed.dto;

import lombok.Getter;

@Getter
public class UserSignupResponseDto {
    
    private Long id;
    private String email;
    private String nickname;

    public UserSignupResponseDto(Long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }
}
