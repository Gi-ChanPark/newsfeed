package com.sparta.springnewsfeed.controller;

import com.sparta.springnewsfeed.dto.PostRequestDto;
import com.sparta.springnewsfeed.dto.PostResponseDto;
import com.sparta.springnewsfeed.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto requestDto) {
        PostResponseDto responseDto = postService.createPost(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable Long postId) {
        PostResponseDto responseDto = postService.findById(postId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto){
        PostResponseDto responseDto = postService.updateById(postId,requestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDto);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId){
        postService.deleteById(postId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("ID : " + postId + " 게시물 삭제 완료");
    }

}
