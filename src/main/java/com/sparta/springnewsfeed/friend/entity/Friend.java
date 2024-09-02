package com.sparta.springnewsfeed.friend.entity;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Friend {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private Long id;

    @Column(name = "user_id_sender")
    private User user;

    @Column(name = "user_id_receiver")
    private User user;

    @Column(length = 15)
    private String status;
}
