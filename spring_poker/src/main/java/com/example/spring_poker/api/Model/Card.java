package com.example.spring_poker.api.Model;
public class Card implements Comparable<Card>{
    int card_num;
    String type;
    public Card(int card_num, String type){
        this.card_num = card_num;
        this.type = type;
    }
    public int getNum(){return card_num;}
    public String getType(){return type;}

    @Override
    public String toString(){
        return "" + card_num + type;
    }
    @Override
    public int compareTo(Card otherCard){
        return this.card_num - otherCard.getNum();
    }
}
