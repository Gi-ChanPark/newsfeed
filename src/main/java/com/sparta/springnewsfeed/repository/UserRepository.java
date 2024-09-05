package com.sparta.springnewsfeed.repository;

import com.sparta.springnewsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    User findByEmailAndNickname(String email, String nickname);

    List<User> findByNicknameContaining(String nickname);

    boolean existsByNickname(String nickname);
}
