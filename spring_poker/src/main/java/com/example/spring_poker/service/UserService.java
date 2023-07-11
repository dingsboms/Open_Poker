package com.example.spring_poker.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.spring_poker.api.modeltest.User;

@Service
public class UserService {

    private List<User> userList;

    public UserService(){
        userList = new ArrayList<>();

        User user1 = new User(1, "Bon", 2, "bon@gmail.com");
        User user2 = new User(2, "Flun", 8, "flun@gmail.com");
        User user3 = new User(3, "Gan", 16, "gan@gmail.com");
        User user4 = new User(4, "Richard", 25, "roich@gmail.com");

        userList.addAll(Arrays.asList(user1, user2, user3, user4));
    }

    public User getUser(Integer id) {
        for(User user: userList){
            if(id == user.getId()){
                return user;
            }
        }
        return null;
    }

    public User getAge(Integer age) {
        for(User user: userList){
            if(age == user.getAge()){
                return user;
            }
        }
        return null;
    }
    
}
