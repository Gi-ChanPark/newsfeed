package com.sparta.springnewsfeed.service;


import com.sparta.springnewsfeed.dto.FriendAddRequest;
import com.sparta.springnewsfeed.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;

    public void friendAdd(Long id, FriendAddRequest friendAddRequest) {
        
    }
}
