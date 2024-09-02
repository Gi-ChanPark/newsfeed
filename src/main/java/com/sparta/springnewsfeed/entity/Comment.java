package com.sparta.springnewsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Comment extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  String content;
    private  LocalDateTime createAt;
    private  LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(Long id, String content, LocalDateTime createAt, LocalDateTime modifiedAt, Post post, User user) {
        this.id = id;
        this.content = content;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
        this.post = post;
        this.user = user;
    }

}
