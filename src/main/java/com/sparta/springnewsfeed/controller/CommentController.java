package com.sparta.springnewsfeed.controller;

import com.sparta.springnewsfeed.dto.CommentSaveRequestDto;
import com.sparta.springnewsfeed.dto.CommentSaveResponseDto;
import com.sparta.springnewsfeed.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/{postId}")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public CommentSaveResponseDto saveComment(@PathVariable Long postId , @RequestBody CommentSaveRequestDto commentSaveRequestDto){
        return commentService.saveComment(postId, commentSaveRequestDto);
    }
}
