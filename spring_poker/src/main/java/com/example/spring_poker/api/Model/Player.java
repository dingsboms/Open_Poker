package com.example.spring_poker.api.Model;
import java.util.ArrayList;
import java.util.Scanner;


public class Player {
    String name;
    ArrayList<Card> hand = new ArrayList<>();
    int chips;
    int bet = 0;
    boolean bigBlind = false;
    boolean smallBlind = false;
    boolean dealer = false;
    String status = "";
    boolean folded = false;
    boolean is_turn = false;
    static int pid = 0;
    int id;
    Scanner user_input;


    public Player(String name){
        this.name = name;
        user_input = new Scanner(System.in);
        this.id = pid;
        pid++;
    }
    public void setHand(String c1, String c2){
        String[] card1 = c1.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        String[] card2 = c2.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        Card k = new Card(Integer.parseInt(card1[0]), card1[1].charAt(0));
        Card l = new Card(Integer.parseInt(card2[0]), card2[1].charAt(0));
        hand.clear();
        hand.add(k);
        hand.add(l);
    }
    public ArrayList<Card> getHand(){return hand;}
    
    public void drawHand(Deck deck){
        folded = false;
        bet = 0;
        if(!hand.isEmpty()){hand.clear();}
        hand.add(deck.drawCard());
        hand.add(deck.drawCard());
    }

    public void setChips(int amount){chips = amount;}

    public void addChips(int pot){chips += pot;}

    public void setBet(int bet_inp){
            chips -= bet_inp;
            bet = bet_inp;
    }

    public void fold(){folded = true;}

    public void call(int bet_to_call){
        bet = bet_to_call;
        chips -= bet;
    }

    public void resetBet(){bet = 0;}

    public void setDealer(){
        is_turn = true;
        dealer = true;
        status = "D";
    }

    public void setSmallBlind(){
        smallBlind = true;
        if(status == "D"){status += " SB";}
        else{status = "SB";}
    }

    public void setBigBlind(){
        bigBlind = true;
        status = "BB";
    }

    public void resetAll(){
        is_turn = false;
        dealer = false;
        smallBlind = false;
        bigBlind = false;
        status = "";
    }

    public int getChips(){return chips;}

    public int getBet(){return bet;}

    public Boolean isFolded(){return folded;}

    public Boolean isSmallBlind(){return smallBlind;}

    public Boolean isBigBlind(){return bigBlind;}

    public Boolean isDealer(){return dealer;}

    public Boolean isTurn(){return is_turn;}

    public void setIsTurn(){is_turn = true;}

    public void resetIsTurn(){is_turn = false;}

    public int getId(){return id;}

    public String getName(){return name;}

    public String getStatus(){return status;}  

    @Override
    public String toString(){
        return "Player-name: " + name + " with hand " + hand.get(0) + hand.get(1) + " Chips: " + chips + " Status: " + status;
    }
}
