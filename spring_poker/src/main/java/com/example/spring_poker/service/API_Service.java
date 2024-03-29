package com.example.spring_poker.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.spring_poker.api.Controller;
import com.example.spring_poker.api.Model.Player;
import com.example.spring_poker.api.Model.Table;
import com.example.spring_poker.api.View.Setup_View;
import com.example.spring_poker.api.View.Table_View;

@Service
public class API_Service {

    static ArrayList<Player> players = new ArrayList<>();


    public static void useSetup(){
        Setup_View sv = new Setup_View();
        ArrayList<String> player_names = sv.getPlayerNames();
        for(String name: player_names){
            players.add(new Player(name));
        }
    }

    public static void defaultPlayers(){
        for(int i = 1; i < 3; i++){
            players.add(new Player("Player " + i));
        }
    }

    public API_Service(){
        defaultPlayers();
        Table_View view = new Table_View(players, 150);
        Table table = new Table(players, 50);
        Controller c = new Controller(view, table);
        c.usePlayerViews();

    }

    public Player getPlayer(Integer id) {
        for(Player player: players){
            if(id == player.getId()){
                return player;
            }
        }
        return null;
    }
    
}
