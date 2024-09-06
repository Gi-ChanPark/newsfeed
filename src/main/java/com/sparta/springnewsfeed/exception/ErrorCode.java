package com.sparta.springnewsfeed.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시물이 존재하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다." ),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글이 없습니다."),
    FRIEND_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND,"친구 요청이 없습니다." ),
    USER_NOT_MATCH(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    INVALID_EMAIL_PASSWORD(HttpStatus.UNAUTHORIZED,"잘못된 이메일이나 비밀번호 입니다." );



    private HttpStatus httpStatus;
    private String message;
}
