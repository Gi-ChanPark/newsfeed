package com.sparta.springnewsfeed.controller;

import com.sparta.springnewsfeed.annotation.Auth;
import com.sparta.springnewsfeed.dto.AuthUser;
import com.sparta.springnewsfeed.dto.PostRequestDto;
import com.sparta.springnewsfeed.dto.PostResponseDto;
import com.sparta.springnewsfeed.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Auth AuthUser authUser, @RequestBody PostRequestDto requestDto) {
        PostResponseDto responseDto = postService.createPost(requestDto, authUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable Long postId) {
        PostResponseDto responseDto = postService.findById(postId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/mypost")
    public ResponseEntity<List<PostResponseDto>> findMyPosts(@Auth AuthUser authUser) {
        List<PostResponseDto> responseDtos = postService.findMyPost(authUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(@Auth AuthUser authUser, @PathVariable Long postId, @RequestBody PostRequestDto requestDto) {
        PostResponseDto responseDto = postService.updateById(postId, requestDto, authUser.getId());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDto);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@Auth AuthUser authUser, @PathVariable Long postId) {
        postService.deleteById(postId, authUser.getId());
        StringBuilder message = new StringBuilder();
        message.append("ID : ").append(postId).append(" 게시물 삭제 완료");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(message.toString());
    }

}
