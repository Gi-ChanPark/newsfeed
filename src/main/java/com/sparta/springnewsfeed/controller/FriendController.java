package com.sparta.springnewsfeed.controller;


import com.sparta.springnewsfeed.dto.FriendAddRequest;
import com.sparta.springnewsfeed.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/{userId}/friends")
    public ResponseEntity<String> FriendAddRequest(@PathVariable Long id, @RequestBody FriendAddRequest friendAddRequest){
        friendService.friendAddRequest(id, friendAddRequest);
        return ResponseEntity.ok("친구 추가 요청 성공");
    }


}
