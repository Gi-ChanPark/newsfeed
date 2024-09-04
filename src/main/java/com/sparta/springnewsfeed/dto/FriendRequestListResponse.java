package com.sparta.springnewsfeed.dto;

import lombok.Getter;

@Getter
public class FriendRequestListResponse {
    private String nickname;

    private FriendRequestListResponse(String nickname){
        this.nickname = nickname;
    }

    public static FriendRequestListResponse of(String nickname){
            return new FriendRequestListResponse(nickname);
    }
}


