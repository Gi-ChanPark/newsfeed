package com.sparta.springnewsfeed.repository;

import com.sparta.springnewsfeed.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query(value = "select f.toUser from Friend f " +
            "where f.status = ? and f.fromUser = ?")
    List<Long> findFriends(String status, Long userId);
}
