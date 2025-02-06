package com.user.api.UsersRestApi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionPer {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String>notFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User could not be found!");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String>illegalArgumentException(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong arguments in the request!");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String>generalException(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
    }
}
