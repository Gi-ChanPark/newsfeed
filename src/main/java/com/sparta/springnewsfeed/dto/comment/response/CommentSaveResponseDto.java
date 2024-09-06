package com.sparta.springnewsfeed.dto.comment.response;

import com.sparta.springnewsfeed.dto.AuthUser;
import com.sparta.springnewsfeed.entity.Comment;
import lombok.Getter;

@Getter
public class CommentSaveResponseDto {
    private final Long id;
    private final String content;
    private final AuthUser authUser;

    public CommentSaveResponseDto(AuthUser authUser, Long id, String content) {
        this.authUser = authUser;
        this.id = id;
        this.content = content;
    }

    public CommentSaveResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.authUser = new AuthUser(comment.getUser().getId(), comment.getUser().getEmail());
    }
}
