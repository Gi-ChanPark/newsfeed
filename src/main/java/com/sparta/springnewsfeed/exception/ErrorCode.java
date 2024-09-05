package com.sparta.springnewsfeed.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,"게시물이 존재하지 않습니다." );


    private HttpStatus httpStatus;
    private String message;
}
