package com.sparta.springnewsfeed.service;


import com.sparta.springnewsfeed.dto.*;
import com.sparta.springnewsfeed.entity.Friend;
import com.sparta.springnewsfeed.entity.User;
import com.sparta.springnewsfeed.repository.FriendRepository;
import com.sparta.springnewsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    private String friendWaitStatus = "WAITING";
    private String friendAcceptStatus = "ACCEPTED";
    private String friendRejectStatus = "REJECTED";



    @Transactional
    public void friendAddRequest(Long id, FriendAddRequest friendAddRequest) {
        User fromUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        User toUser = userRepository.findByNickname(friendAddRequest.getNickname());

        if(toUser == null) {
            log.error("User Not Found");
            throw new IllegalArgumentException("User Not Found");
        }

        Friend friend = new Friend();
        friend.addRequest(fromUser, toUser, friendWaitStatus);
        friendRepository.save(friend);
    }

    public List<FriendRequestListResponse> friendRquestList(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        List<Friend> friendList = friendRepository.findAllByToUserAndStatus(user, friendWaitStatus);

        if(friendList.isEmpty()){
            log.error("Friend Request Not Found");
            throw new NoSuchElementException("Friend Request Not Found");
        }

        List<FriendRequestListResponse> friendRequestListResponses = friendList.stream().map(friend ->
                FriendRequestListResponse.of(friend.getFromUser().getNickname())).toList();

        return friendRequestListResponses;
    }

    public UserSearchFriendResponse userSearchFriend(Long id, UserSearchFriendRequest request) {
        User fromUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        User searchUser = userRepository.findByNickname(request.getNickname());
        if(searchUser == null){
            throw new NullPointerException("User Not Found");
        }

        Friend friend = friendRepository.findByFromUserAndToUser(fromUser, searchUser);

        if(friend == null){
            throw new NullPointerException("Friend Not Found");
        }else if(friend.getStatus().equals(friendWaitStatus)){
            return UserSearchFriendResponse.nicknameSearchUser(friend.getToUser().getNickname(), "요청 보냄");
        }else if(friend.getStatus().equals(friendAcceptStatus)){
            return UserSearchFriendResponse.nicknameSearchUser(friend.getToUser().getNickname(), "친구");
        }
        return UserSearchFriendResponse.nicknameSearchUser(searchUser.getNickname(), "요청 가능");
    }

    @Transactional
    public void deleteFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User Not Found"));

        Friend friend = friendRepository.findByIdAndFromUser(friendId, user);

        if(friend == null){
            log.error("Friend Not Found");
            throw new NullPointerException("Friend Not Found");
        }

        friendRepository.delete(friend);
    }

    @Transactional
    public String friendAcceptanceRejection(Long userId, Long friendId, FriendRequestStatus status) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User Not Found"));

        Friend friend = friendRepository.findByIdAndFromUserAndStatus(friendId, user, friendWaitStatus);

        if(friend == null){
            log.error("Friend Not Found WAITING");
            throw new NullPointerException("Friend Not Found WAITING");
        }

        if(status.getStatus().equals(friendAcceptStatus)){
            friend.FriendAcceptance(status.getStatus());
            return "친구 요청 수락";
        }else if(status.getStatus().equals(friendRejectStatus)){
            friend.FriendRejection(status.getStatus());
            friendRepository.delete(friend);
            return "친구 요청 거절";
        }
        return "";
    }
}
