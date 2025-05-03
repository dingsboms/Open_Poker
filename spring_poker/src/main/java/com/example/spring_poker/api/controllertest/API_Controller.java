package com.example.spring_poker.api.controllertest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ArrayList<Player> getPlayers(){
        return api_service.getPlayers();
    }

    @PostMapping(value = "/check")
    public void checkResponse(@RequestBody String player_id) throws UnsupportedEncodingException{
        String id;
        id = URLDecoder.decode(player_id, StandardCharsets.UTF_8.toString());
        id = id.replace("\"", "");
        id = id.replace("=", "");
        int i = Integer.parseInt(id);
        api_service.playerCheck(i);
    }
}
