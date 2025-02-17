package com.sparta.springnewsfeed.controller;

import com.sparta.springnewsfeed.annotation.Auth;
import com.sparta.springnewsfeed.dto.AuthUser;
import com.sparta.springnewsfeed.dto.post.response.PostThumbnailResponseDto;
import com.sparta.springnewsfeed.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/newsfeed")
public class NewsfeedController {
    private final PostService postService;

    public NewsfeedController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping()
    public ResponseEntity<List<PostThumbnailResponseDto>> getNewsfeed(@Auth AuthUser authUser, @RequestParam(name = "page") int page) {
        Page<PostThumbnailResponseDto> newsfeeds = postService.getNewsfeed(authUser.getId(), page);

        return ResponseEntity.status(HttpStatus.OK).body(newsfeeds.getContent());
    }
}