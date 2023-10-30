package com.ajaz.userservice.services;

import com.ajaz.userservice.dtos.UserDto;
import com.ajaz.userservice.exceptions.NotFoundException;
import com.ajaz.userservice.models.Session;
import com.ajaz.userservice.models.SessionStatus;
import com.ajaz.userservice.models.User;
import com.ajaz.userservice.repositories.SessionRepository;
import com.ajaz.userservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.sessionRepository = sessionRepository;
    }
    public UserDto signup(String email, String password){
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        User savedUser = userRepository.save(user);

        return UserDto.from(savedUser);

    }


    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserDto> usersAns = new ArrayList<>();
        users.forEach(e -> usersAns.add(
                convertUserToUserDto(e)

        ));

        return new ResponseEntity<>(usersAns, HttpStatus.OK);

    }

    private UserDto convertUserToUserDto(User e) {
        UserDto temp = new UserDto();

        temp.setEmail(e.getEmail());
        temp.setPassword(e.getPassword());

        return temp;
    }

    public UserDto updateUserById(Long id, String email) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw new NotFoundException("user DNE");
        }

        User user = userOptional.get();
        user.setEmail(email);

        userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());

        return userDto;

    }

    public String deleteUserById(Long id) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw new NotFoundException("User DNE");
        }

        userRepository.deleteById(id);

        return "User with id = " + id + " deleted successfully.";
    }


    public ResponseEntity<UserDto> login(String email, String password) throws NotFoundException{

        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new NotFoundException("User with the email does not exist.");
        }

        User user = userOptional.get();

        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new NotFoundException("Password does not match.");
        }

        String token = RandomStringUtils.randomAlphanumeric(30);

        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);

        sessionRepository.save(session);

        UserDto userDto = UserDto.from(user);

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);

        return new ResponseEntity<>(userDto, headers, HttpStatus.OK);
    }


    public String logout(Long userId, String token) throws NotFoundException{

        Optional<Session> sessionOptional = sessionRepository.findByUser_IdAndToken(userId, token);

        if(sessionOptional.isEmpty()){
            throw new NotFoundException("Session with userId = " + userId + " and given token does not exist.");
        }

        Long sessionEndedId = sessionOptional.get().getId();

        Session session = sessionOptional.get();

        session.setSessionStatus(SessionStatus.ENDED);

        sessionRepository.save(session);


        return "Session with id = " + sessionEndedId + " has been deleted.";
    }
}
