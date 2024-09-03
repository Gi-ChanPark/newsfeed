package com.sparta.springnewsfeed.dto;

import lombok.Getter;

@Getter
public class UserSearchFriendResponse {

    private String nickname;
    private String state;


    private UserSearchFriendResponse(String nickname, String state){
        this.nickname = nickname;
        this.state = state;
    }

    public static UserSearchFriendResponse nicknameSearchUser(String nickname, String state){
        return new UserSearchFriendResponse(nickname, state);
    }
}
