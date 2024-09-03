package com.sparta.springnewsfeed.service;


import com.sparta.springnewsfeed.FriendStatus;
import com.sparta.springnewsfeed.dto.FriendAddRequest;
import com.sparta.springnewsfeed.entity.Friend;
import com.sparta.springnewsfeed.entity.User;
import com.sparta.springnewsfeed.repository.FriendRepository;
import com.sparta.springnewsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    private FriendStatus friendWaittingStatus = FriendStatus.WAITING;
    private FriendStatus friendAcceptStatus = FriendStatus.ACCEPT;
    private FriendStatus friendRejectStatus = FriendStatus.REJECTED;

    @Transactional
    public void friendAddRequest(Long id, FriendAddRequest friendAddRequest) {
        User fromUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        User toUser = userRepository.findByNickname(friendAddRequest.getNickname());

        if(toUser == null) {
            log.error("User Not Found");
            throw new IllegalArgumentException("User Not Found");
        }

        Friend friend = new Friend();
        friend.addRequest(fromUser, toUser, friendWaittingStatus);
        friendRepository.save(friend);
    }
}
