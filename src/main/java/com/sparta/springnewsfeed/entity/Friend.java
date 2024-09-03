package com.sparta.springnewsfeed.entity;


import com.sparta.springnewsfeed.FriendStatus;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Friend {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_sender")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_receiver")
    private User toUser;

    @Column(length = 15)
    private FriendStatus status;

    public void addRequest(User fromUser, User toUser, FriendStatus friendWaittingStatus) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.status = friendWaittingStatus;
    }
}
