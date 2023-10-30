package com.ajaz.userservice.dtos;

import com.ajaz.userservice.models.Role;
import com.ajaz.userservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDto {

    private String email;
    private String password;
    Set<Role> roles = new HashSet<>();

    public static UserDto from(User user){
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRoles(user.getRoles());

        return userDto;
    }

}
