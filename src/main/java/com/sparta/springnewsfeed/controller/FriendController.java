package com.sparta.springnewsfeed.controller;


import com.sparta.springnewsfeed.annotation.Auth;
import com.sparta.springnewsfeed.dto.*;
import com.sparta.springnewsfeed.dto.friend.request.FriendAddRequest;
import com.sparta.springnewsfeed.dto.friend.request.FriendRequestListResponse;
import com.sparta.springnewsfeed.dto.friend.request.FriendRequestStatus;
import com.sparta.springnewsfeed.dto.user.request.UserSearchFriendRequest;
import com.sparta.springnewsfeed.dto.user.response.UserSearchFriendResponse;
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

    @PostMapping("/friends")
    public ResponseEntity<String> friendAddRequest(@Auth AuthUser authUser, @RequestBody FriendAddRequest friendAddRequest){
        friendService.friendAddRequest(authUser.getId(), friendAddRequest);
        return ResponseEntity.ok().body("친구 추가 요청 성공");
    }


    @GetMapping("/friends")
    public ResponseEntity<List<FriendRequestListResponse>> getListFriendRequest(@Auth AuthUser authUser){
        List<FriendRequestListResponse> friendRequestListResponses = friendService.friendRequestList(authUser.getId());

        return ResponseEntity.ok().body(friendRequestListResponses);
    }


    @GetMapping("/friends/search")
    public ResponseEntity<List<UserSearchFriendResponse>> searchFriend(@Auth AuthUser authUser, @RequestBody UserSearchFriendRequest request){
        List<UserSearchFriendResponse> responseList = friendService.userSearchFriend(authUser.getId(), request);

        return ResponseEntity.ok().body(responseList);
    }

    @DeleteMapping("/friends/{friendId}")
    public ResponseEntity<String> deleteFriend(@Auth AuthUser authUser, @PathVariable Long friendId){
        friendService.deleteFriend(authUser.getId(), friendId);
        return ResponseEntity.ok().body("친구 삭제 완료");
    }

    @PutMapping("/friends/{friendId}")
    public ResponseEntity<String> friendAcceptanceRejection(@Auth AuthUser authUser, @PathVariable Long friendId, @RequestBody FriendRequestStatus status){
              String state = friendService.friendAcceptanceRejection(authUser.getId(), friendId, status);
        return ResponseEntity.ok().body(state);
    }


}
