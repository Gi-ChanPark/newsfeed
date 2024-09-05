package com.sparta.springnewsfeed.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NicknameAlreadyExistException extends RuntimeException {
    private final ErrorCode errorCode;
}
