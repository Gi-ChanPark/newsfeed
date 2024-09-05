package com.sparta.springnewsfeed.exception.custom;

import com.sparta.springnewsfeed.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class EmailAlreadyExistsException extends RuntimeException {
    private final ErrorCode errorCode;
}
