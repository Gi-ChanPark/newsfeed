package com.sparta.springnewsfeed.dto;

import lombok.Getter;

@Getter
public class CommentUpdateResponseDto {
    private final Long id;
    private final String content;

    public CommentUpdateResponseDto( AuthUser authUser, Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
