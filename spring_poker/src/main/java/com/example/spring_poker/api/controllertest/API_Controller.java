package com.example.spring_poker.api.controllertest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_poker.api.Model.Player;
import com.example.spring_poker.service.API_Service;

@RestController
public class API_Controller {

    private API_Service api_service;

    public API_Controller(API_Service api_service){
        this.api_service = api_service;
    }

    @GetMapping("/player")
    public Player getPlayer(@RequestParam Integer id){
        return api_service.getPlayer(id);
    }
}
