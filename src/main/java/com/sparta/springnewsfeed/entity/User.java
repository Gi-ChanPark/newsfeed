package com.sparta.springnewsfeed.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends Timestamped{

    @Id
    @Column(name = "user_id")
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


    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "fromUser")
    private List<Friend> friends = new ArrayList<>();

    @OneToMany(mappedBy = "toUser")
    private List<Friend> toFriends = new ArrayList<>();

    public User(String email, String password, String nickname, String introduce) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.introduce = introduce;
    }

}
