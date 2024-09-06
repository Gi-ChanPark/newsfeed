package com.sparta.springnewsfeed.controller;

import com.sparta.springnewsfeed.annotation.Auth;
import com.sparta.springnewsfeed.dto.AuthUser;
import com.sparta.springnewsfeed.dto.comment.request.CommentSaveRequestDto;
import com.sparta.springnewsfeed.dto.comment.request.CommentUpdateRequstDto;
import com.sparta.springnewsfeed.dto.comment.response.CommentSaveResponseDto;
import com.sparta.springnewsfeed.dto.comment.response.CommentUpdateResponseDto;
import com.sparta.springnewsfeed.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comments")
    public List<CommentSaveResponseDto> findAllComment(@PathVariable Long postId){
        return commentService.findAllComment(postId);
    }

    @PostMapping("/comments")
    public CommentSaveResponseDto saveComment(@Auth AuthUser authUser, @PathVariable Long postId, @RequestBody CommentSaveRequestDto commentSaveRequestDto) {
        return commentService.saveComment(authUser, postId, commentSaveRequestDto);
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
