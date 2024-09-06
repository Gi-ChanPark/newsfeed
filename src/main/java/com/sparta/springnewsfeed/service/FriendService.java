package com.sparta.springnewsfeed.service;


import com.sparta.springnewsfeed.FriendStatus;
import com.sparta.springnewsfeed.dto.friend.request.FriendAddRequest;
import com.sparta.springnewsfeed.dto.friend.request.FriendRequestListResponse;
import com.sparta.springnewsfeed.dto.friend.request.FriendRequestStatus;
import com.sparta.springnewsfeed.dto.user.request.UserSearchFriendRequest;
import com.sparta.springnewsfeed.dto.user.response.UserSearchFriendResponse;
import com.sparta.springnewsfeed.entity.Friend;
import com.sparta.springnewsfeed.entity.User;
import com.sparta.springnewsfeed.exception.ErrorCode;
import com.sparta.springnewsfeed.exception.custom.NoEntityException;
import com.sparta.springnewsfeed.repository.FriendRepository;
import com.sparta.springnewsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    private final FriendStatus friendWaitStatus = FriendStatus.WAITING;
    private final FriendStatus friendAcceptStatus = FriendStatus.ACCEPTED;
    private final FriendStatus friendRejectStatus = FriendStatus.REJECTED;


    @Transactional
    public void friendAddRequest(Long userId, FriendAddRequest friendAddRequest) {
        User fromUser = userRepository.findById(userId).orElseThrow(() -> new NoEntityException(ErrorCode.USER_NOT_FOUND));
        User toUser = userRepository.findByEmailAndNickname(friendAddRequest.getEmail(), friendAddRequest.getNickname());

        if (fromUser.equals(toUser)) {
            log.error("자신에게는 친구 요청을 보내지 못합니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자신에게는 친구 요청을 보내지 못합니다.");
        }

        if (friendRepository.existsByToUser(fromUser) && friendRepository.existsByFromUser(toUser)) {
            Friend friend = friendRepository.findByFromUserAndToUser(toUser, fromUser);
            if (friend.getStatus().equals(friendAcceptStatus)) {
                log.error("친구입니다");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "친구입니다.");
            } else if (friend.getStatus().equals(friendWaitStatus)) {
                log.error("요청을 받았습니다.");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "요청을 받았습니다.");
            }
        }

        if (toUser == null) {
            log.error("사용자를 찾지 못했습니다.");
            throw new NoEntityException(ErrorCode.USER_NOT_FOUND);
        }


        Friend friend2 = friendRepository.findByFromUserAndToUser(fromUser, toUser);
        if (friend2 != null) {
            if (friend2.getStatus().equals(friendAcceptStatus)) {
                log.error("친구입니다.");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "친구입니다.");
            } else if (friend2.getStatus().equals(friendWaitStatus)) {
                log.error("요청을 보냈습니다.");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "요청을 보냈습니다.");
            }
        }


        Friend friend = new Friend();
        friend.addRequest(fromUser, toUser, friendWaitStatus);
        friendRepository.save(friend);
    }

    public List<FriendRequestListResponse> friendRequestList(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoEntityException(ErrorCode.USER_NOT_FOUND));
        List<Friend> friendList = friendRepository.findAllByToUserAndStatus(user, friendWaitStatus);

        if (friendList.isEmpty()) {
            log.error("친구 요청이 없습니다.");
            throw new NoEntityException(ErrorCode.FRIEND_REQUEST_NOT_FOUND);
        }

        return friendList.stream().map(friend ->
                FriendRequestListResponse.of(friend.getFromUser().getNickname())).toList();
    }

    public List<UserSearchFriendResponse> userSearchFriend(Long userId, UserSearchFriendRequest request) {
        User fromUser = userRepository.findById(userId).orElseThrow(() -> new NoEntityException(ErrorCode.USER_NOT_FOUND));

        List<User> searchUser = userRepository.findByNicknameContaining(request.getNickname());
        if (searchUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 이름입니다.");
        }

        List<UserSearchFriendResponse> responseList = new ArrayList<>();

        for (User user : searchUser) {
            UserSearchFriendResponse response = new UserSearchFriendResponse();
            response.setNickname(user.getNickname());

            if (request.getNickname().equals(user.getNickname())) {
                response.setState("본인");
            } else {
                response.setState("친구 아님");
            }


            Friend friend = friendRepository.findByFromUserAndToUser(fromUser, user);
            if (friend != null) {

                if (friend.getStatus().equals(friendAcceptStatus)) {
                    response.setState("친구");
                } else if (friend.getStatus().equals(friendWaitStatus)) {
                    response.setState("요청 보냄");
                } else {
                    response.setState("친구 아님");
                }
            } else {
                friend = friendRepository.findByFromUserAndToUser(user, fromUser);
                if (friend != null) {
                    if (friend.getStatus().equals(friendAcceptStatus)) {
                        response.setState("친구");
                    } else if (friend.getStatus().equals(friendWaitStatus)) {
                        response.setState("요청 받음");
                    } else {
                        response.setState("친구 아님");
                    }
                }
            }

            responseList.add(response);
        }

        return responseList;
    }

    @Transactional
    public void deleteFriend(Long userId, Long friendId) {
        Friend friend = friendRepository.findById(friendId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾지 못했습니다."));

        if (!friend.getFromUser().getId().equals(userId) && !friend.getToUser().getId().equals(userId)) {
            log.error("친구가 아닙니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "친구가 아닙니다.");
        }

        if (friend.getStatus().equals(friendWaitStatus) || !friend.getStatus().equals(friendAcceptStatus)) {
            log.error("친구가 아닙니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "친구가 아닙니다.");
        }

        friendRepository.delete(friend);
    }

    @Transactional
    public String friendAcceptanceRejection(Long userId, Long friendId, FriendRequestStatus status) {
        Friend friend = friendRepository.findById(friendId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾지 못했습니다."));

        if (!friend.getToUser().getId().equals(userId)) {
            log.error("받은 요청이 없습니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "받은 요청이 없습니다.");
        }

        if (status.getStatus().equals(friendAcceptStatus)) {
            friend.FriendAcceptance(status.getStatus());
            return "친구 요청 수락";
        } else if (status.getStatus().equals(friendRejectStatus)) {
            friend.FriendRejection(status.getStatus());
            friendRepository.delete(friend);
            return "친구 요청 거절";
        }

        log.error("잘못된 친구 요청입니다.");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 친구 요청입니다.");
    }
}
