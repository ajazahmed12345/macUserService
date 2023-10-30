package com.ajaz.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvices {

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<String> handleNotFoundException(NotFoundException e){
//        return new ResponseEntity<>(new NotFoundException(
//                "user does not exist"
//        ), HttpStatus.OK);

        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
