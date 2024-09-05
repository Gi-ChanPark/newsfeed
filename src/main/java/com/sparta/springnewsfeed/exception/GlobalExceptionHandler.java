package com.sparta.springnewsfeed.exception;

import com.sparta.springnewsfeed.exception.custom.AlreadyExistException;
import com.sparta.springnewsfeed.exception.custom.InvalidCredentialsException;
import com.sparta.springnewsfeed.exception.custom.NoEntityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException e) {
        return new ResponseEntity<>(e.getErrorCode().getMessage(), e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<String> handleAlreadyExistException(AlreadyExistException e) {
        return new ResponseEntity<>(e.getErrorCode().getMessage(), e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(NoEntityException.class)
    public ResponseEntity<String> handleNoEntityException(NoEntityException e) {
        return new ResponseEntity<>(e.getErrorCode().getMessage(), e.getErrorCode().getHttpStatus());
    }
}
