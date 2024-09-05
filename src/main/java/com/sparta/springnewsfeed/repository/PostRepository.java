package com.sparta.springnewsfeed.repository;

import com.sparta.springnewsfeed.entity.Post;
import com.sparta.springnewsfeed.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<List<Post>> findByUserId(Long userid);

    Long findUserIdById(Long postId);

    @Query(value = "select p from Post p " +
            "where p.user IN :friends " +
            "order by p.updatedAt desc ")
    Page<Post> findPostsByUsers(@Param("friends") List<User> friends, Pageable pageable);

    Page<Post> findAllByOrderByUpdatedAtDesc(Pageable pageable);

}

