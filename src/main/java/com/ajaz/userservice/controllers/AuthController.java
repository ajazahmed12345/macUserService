package com.ajaz.userservice.controllers;

import com.ajaz.userservice.dtos.LoginRequestDto;
import com.ajaz.userservice.dtos.LogoutRequestDto;
import com.ajaz.userservice.dtos.SignUpRequestDto;
import com.ajaz.userservice.dtos.UserDto;
import com.ajaz.userservice.exceptions.NotFoundException;
import com.ajaz.userservice.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        UserDto userDto = authService.signup(signUpRequestDto.getEmail(), signUpRequestDto.getPassword());

        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) throws NotFoundException {
        return authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());


    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequestDto request) throws NotFoundException{
        String response = authService.logout(request.getUserId(), request.getToken());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @ExceptionHandler(value = {NotFoundException.class})
//    public ResponseEntity<NotFoundException> handleNotFoundException(){
//        return new ResponseEntity<>(new NotFoundException(
//                "User does not exist."
//        ), HttpStatus.OK);
//    }



}
