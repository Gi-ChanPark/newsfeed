package com.sparta.springnewsfeed.controller;

import com.sparta.springnewsfeed.annotation.Auth;
import com.sparta.springnewsfeed.dto.AuthUser;
import com.sparta.springnewsfeed.dto.NewsfeedResponseDto;
import com.sparta.springnewsfeed.service.NewsfeedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/newsfeed")
public class NewsfeedController {
    private final NewsfeedService newsfeedService;

    public NewsfeedController(NewsfeedService newsfeedService) {
        this.newsfeedService = newsfeedService;
    }

    @GetMapping()
    public NewsfeedResponseDto getNewsfeed(@Auth AuthUser authUser, @RequestParam(name = "page") Long page){
        newsfeedService.getNewsfeed(page);
        return null;
    }
}
