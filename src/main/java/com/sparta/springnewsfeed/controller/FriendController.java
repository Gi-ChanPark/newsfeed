package com.sparta.springnewsfeed.controller;


import com.sparta.springnewsfeed.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;
}
