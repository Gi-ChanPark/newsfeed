package com.sparta.springnewsfeed.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String nickname;

    private String introduce;

    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    @PrePersist
    public void onCreate()  {
        this.createAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }

}
