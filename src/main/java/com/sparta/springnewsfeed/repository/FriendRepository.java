package com.sparta.springnewsfeed.repository;

import com.sparta.springnewsfeed.FriendStatus;
import com.sparta.springnewsfeed.entity.Friend;
import com.sparta.springnewsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findAllByToUserAndStatus(User user, FriendStatus status);

    Friend findByFromUserAndToUser(User fromUser, User toUser);

    Boolean existsByToUser(User toUser);

    Boolean existsByFromUser(User fromUser);

}
