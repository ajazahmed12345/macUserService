package com.ajaz.userservice.controllers;

import com.ajaz.userservice.dtos.SetUserRolesRequestDto;
import com.ajaz.userservice.dtos.UserDto;
import com.ajaz.userservice.exceptions.NotFoundException;
import com.ajaz.userservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") Long id) throws NotFoundException {
        return userService.getUserDetails(id);
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<UserDto> setUserRoles(@PathVariable("id") Long id, @RequestBody SetUserRolesRequestDto setUserRolesRequestDto) throws NotFoundException{
        return userService.setUserRoles(id, setUserRolesRequestDto.getRoleIds());
    }

}
