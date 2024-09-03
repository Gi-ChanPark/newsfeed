package com.sparta.springnewsfeed.repository;

import com.sparta.springnewsfeed.entity.Friend;
import com.sparta.springnewsfeed.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findAllByToUserAndStatus(User user, String status);

    Friend findByIdAndFromUser(Long id, User user);

    Friend findByIdAndFromUserAndStatus(Long id, User user, String status);

    Friend findByFromUserAndToUser(User fromUser, User toUser);
}
