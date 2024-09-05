package com.sparta.springnewsfeed.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity handleInvalidCredentialsException(InvalidCredentialsException e) {
        return null;
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity handleEmailAlreadyExistsException(EmailAlreadyExistsException e){
        return null;
    }

    @ExceptionHandler(NicknameAlreadyExistException.class)
    public ResponseEntity handleNicknameAlreadyExistException(NicknameAlreadyExistException e){
        return null;
    }
}
