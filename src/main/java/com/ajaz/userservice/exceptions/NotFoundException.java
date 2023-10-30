package com.ajaz.userservice.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class NotFoundException extends Exception{


    public NotFoundException(String message){
        super(message);
    }
}
