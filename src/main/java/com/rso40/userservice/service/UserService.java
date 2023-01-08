package com.rso40.userservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rso40.userservice.dto.UserRequest;
import com.rso40.userservice.dto.UserResponse;
import com.rso40.userservice.model.User;
import com.rso40.userservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public void createUser(UserRequest userRequest){
        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .address(userRequest.getAddress())
                .admin(userRequest.getAdmin())
                .build();


        userRepository.save(user);
        log.info("User {} is saved.", user.getId());
    }

    public List<UserResponse> getAllUsers(){
        List<User> users = userRepository.findAll();

        return users.stream().map(this::mapToUserResponse).toList();
    }

    private UserResponse mapToUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .address(user.getAddress())
                .admin(user.getAdmin())
                .build();
    }
    
}
