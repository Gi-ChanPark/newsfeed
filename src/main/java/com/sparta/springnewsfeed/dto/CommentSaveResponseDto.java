package com.sparta.springnewsfeed.dto;

import lombok.Getter;

@Getter
public class CommentSaveResponseDto {
    private final Long id;
    private final String content;

    public CommentSaveResponseDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
