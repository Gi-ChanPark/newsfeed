package com.sparta.springnewsfeed.exception;

import com.sparta.springnewsfeed.exception.custom.AlreadyExistException;
import com.sparta.springnewsfeed.exception.custom.EmailAlreadyExistsException;
import com.sparta.springnewsfeed.exception.custom.InvalidCredentialsException;
import com.sparta.springnewsfeed.exception.custom.NoEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExistsException(EmailAlreadyExistsException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<String> handleNicknameAlreadyExistException(AlreadyExistException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoEntityException.class)
    public ResponseEntity<String> handleNoEntityException(NoEntityException e){
        return new ResponseEntity<>(e.getErrorCode().getMessage(),e.getErrorCode().getHttpStatus());
    }
}
