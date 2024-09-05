package com.sparta.springnewsfeed.exception.custom;

import com.sparta.springnewsfeed.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlreadyExistException extends RuntimeException {
    private final ErrorCode errorCode;
}
