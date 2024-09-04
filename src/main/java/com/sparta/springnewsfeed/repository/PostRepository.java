package com.sparta.springnewsfeed.repository;

import com.sparta.springnewsfeed.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<List<Post>> findByUserId(Long userid);

    Long findUserIdById(Long postId);
}
