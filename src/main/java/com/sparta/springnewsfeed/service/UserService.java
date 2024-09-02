package com.sparta.springnewsfeed.service;

import com.sparta.springnewsfeed.dto.UserSignupRequestDto;
import com.sparta.springnewsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void signup(UserSignupRequestDto requestDto) {

    }
}
