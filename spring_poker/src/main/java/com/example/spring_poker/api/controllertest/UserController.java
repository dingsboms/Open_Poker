package com.example.spring_poker.api.controllertest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_poker.api.modeltest.User;
import com.example.spring_poker.service.UserService;

@RestController
public class UserController {
    
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user")
    public User getUser(@RequestParam Integer id){
        return userService.getUser(id);
    }

    @GetMapping("/age")
    public User getAge(@RequestParam Integer age){
        return userService.getAge(age);
    }
}
