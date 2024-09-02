package com.sparta.springnewsfeed.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "introduce")
    private String introduce;

    // 게시물 목록
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    // 친구 목록
    @OneToMany(mappedBy = "user")
    private List<Friend> friends = new ArrayList<>();
}
