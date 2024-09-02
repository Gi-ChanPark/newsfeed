package com.sparta.springnewsfeed.repository;

import com.sparta.springnewsfeed.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
