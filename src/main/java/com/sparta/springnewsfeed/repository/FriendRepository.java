package com.sparta.springnewsfeed.repository;

import com.sparta.springnewsfeed.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
