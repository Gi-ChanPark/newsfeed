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
    public ResponseEntity<String> friendAddRequest(@PathVariable Long id, @RequestBody FriendAddRequest friendAddRequest){
        friendService.friendAddRequest(id, friendAddRequest);
        return ResponseEntity.ok().body("친구 추가 요청 성공");
    }


    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<FriendRequestListResponse>> getListFriendRequest(@PathVariable Long id){
        List<FriendRequestListResponse> friendRequestListResponses = friendService.friendRquestList(id);

        return ResponseEntity.ok().body(friendRequestListResponses);
    }


    @GetMapping("/{userId}/friends/search")
    public ResponseEntity<UserSearchFriendResponse> searchFriend(@PathVariable Long id, @RequestBody UserSearchFriendRequest request){
        UserSearchFriendResponse userSearchFriendResponse = friendService.userSearchFriend(id,request);

        return ResponseEntity.ok().body(userSearchFriendResponse);
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
