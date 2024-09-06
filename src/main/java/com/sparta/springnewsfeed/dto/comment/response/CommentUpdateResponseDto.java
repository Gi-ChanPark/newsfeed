package com.sparta.springnewsfeed.dto.comment.response;

import com.sparta.springnewsfeed.dto.AuthUser;
import lombok.Getter;

@Getter
public class CommentUpdateResponseDto {
    private final Long id;
    private final String content;

    public CommentUpdateResponseDto(AuthUser authUser, Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
