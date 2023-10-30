package com.ajaz.userservice.services;

import com.ajaz.userservice.dtos.SetUserRolesRequestDto;
import com.ajaz.userservice.dtos.UserDto;
import com.ajaz.userservice.exceptions.NotFoundException;
import com.ajaz.userservice.models.Role;
import com.ajaz.userservice.models.User;
import com.ajaz.userservice.repositories.RoleRepository;
import com.ajaz.userservice.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    public ResponseEntity<UserDto> getUserDetails(Long id) throws NotFoundException{
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new NotFoundException("User with id = " + id + " does not exist");
        }

        User user = userOptional.get();

        return new ResponseEntity<>(UserDto.from(user), HttpStatus.OK);
    }

    public ResponseEntity<UserDto> setUserRoles(Long id, List<Long> roleIds) throws NotFoundException{
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new NotFoundException("User with id = " + id + " does not exist");
        }

        User user = userOptional.get();

        List<Role> roles = roleRepository.findAllByIdIn(roleIds);

        roles.forEach(e -> user.getRoles().add(e));

        User savedUser = userRepository.save(user);



        return new ResponseEntity<>(UserDto.from(savedUser), HttpStatus.OK);
    }
}
