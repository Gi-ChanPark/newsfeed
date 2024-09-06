package com.sparta.springnewsfeed.dto.user.request;

import lombok.Getter;

@Getter
public class UserRequestDto {

    private Long id;
    private String email;
    private String nickname;
    private String createdAt;
    private String updatedAt;
    private String introduce;

    public UserRequestDto(Long id, String email, String nickname, String createdAt, String updatedAt, String introduce) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.introduce = introduce;
    }
}
