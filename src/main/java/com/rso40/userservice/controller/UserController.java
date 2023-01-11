package com.rso40.userservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.rso40.userservice.dto.UserRequest;
import com.rso40.userservice.dto.UserResponse;
import com.rso40.userservice.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView index (){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView user_info (){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user_info");
        return modelAndView;
    }


    //End point
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserRequest userRequest){
        userService.createUser(userRequest);
    }

    //End point - popravi (tudi v UserResponse) za prijavo
    /*@GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }*/
}
