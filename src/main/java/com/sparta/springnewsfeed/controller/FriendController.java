package com.sparta.springnewsfeed.controller;


import com.sparta.springnewsfeed.dto.*;
import com.sparta.springnewsfeed.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/{userId}/friends")
    public ResponseEntity<String> friendAddRequest(@PathVariable Long userId, @RequestBody FriendAddRequest friendAddRequest){
        friendService.friendAddRequest(userId, friendAddRequest);
        return ResponseEntity.ok().body("친구 추가 요청 성공");
    }


    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<FriendRequestListResponse>> getListFriendRequest(@PathVariable Long userId){
        List<FriendRequestListResponse> friendRequestListResponses = friendService.friendRequestList(userId);

        return ResponseEntity.ok().body(friendRequestListResponses);
    }


    @GetMapping("/{userId}/friends/search")
    public ResponseEntity<List<UserSearchFriendResponse>> searchFriend(@PathVariable Long userId, @RequestBody UserSearchFriendRequest request){
        List<UserSearchFriendResponse> usered = friendService.userSearchFriend(userId,request);

        return ResponseEntity.ok().body(usered);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<String> deleteFriend(@PathVariable Long userId, @PathVariable Long friendId){
        friendService.deleteFriend(userId,friendId);
        return ResponseEntity.ok().body("친구 삭제 완료");
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<String> friendAcceptanceRejection(@PathVariable Long userId, @PathVariable Long friendId, @RequestBody FriendRequestStatus status){
              String state = friendService.friendAcceptanceRejection(userId,friendId, status);
        return ResponseEntity.ok().body(state);
    }


}
