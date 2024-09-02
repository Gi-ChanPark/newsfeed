package com.sparta.springnewsfeed.controller;

import com.sparta.springnewsfeed.service.PostService;
import org.springframework.stereotype.Controller;

@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
}
