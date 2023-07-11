package com.example.spring_poker.api.Model;
public class Card implements Comparable<Card>{
// A card consists of a number and type; H, D, C, S
    int card_num;
    char type;
    public Card(int card_num, char type){
        this.card_num = card_num;
        this.type = type;
    }
    public int getNum(){return card_num;}
    public char getType(){return type;}

    @Override
    public String toString(){
        return "" + card_num + type;
    }
    @Override
    public int compareTo(Card otherCard){
        return this.card_num - otherCard.getNum();
    }
}
