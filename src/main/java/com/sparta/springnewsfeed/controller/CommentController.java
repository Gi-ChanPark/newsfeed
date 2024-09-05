package com.sparta.springnewsfeed.controller;

import com.sparta.springnewsfeed.annotation.Auth;
import com.sparta.springnewsfeed.dto.*;
import com.sparta.springnewsfeed.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public CommentSaveResponseDto saveComment(@PathVariable Long postId, @RequestBody CommentSaveRequestDto commentSaveRequestDto) {
        return commentService.saveComment(postId, commentSaveRequestDto);
    }

    @PutMapping("/comments/{commentId}")
    public CommentUpdateResponseDto updateComment(@Auth AuthUser authUser, @PathVariable Long commentId, @RequestBody CommentUpdateRequstDto commentUpdateRequstDto) {
        return commentService.updateComment(authUser, commentId, commentUpdateRequstDto);
    }

    @DeleteMapping("/comments/{commentId}")
    public void CommentDelete(@Auth AuthUser authUser, @PathVariable Long commentId) {
        commentService.deleteComment(authUser, commentId);
    }

}
