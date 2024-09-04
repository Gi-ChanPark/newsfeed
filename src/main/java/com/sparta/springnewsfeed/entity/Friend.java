package com.sparta.springnewsfeed.entity;


import com.sparta.springnewsfeed.FriendStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
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
    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    private Friend(User fromUser, User toUser, FriendStatus status){
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.status = status;
    }

    public void addRequest(User fromUser, User toUser, FriendStatus status) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.status = status;
    }


    public void FriendAcceptance(FriendStatus status){
        this.status = status;
    }

    public void FriendRejection(FriendStatus status){
        this.status = status;
    }
}
