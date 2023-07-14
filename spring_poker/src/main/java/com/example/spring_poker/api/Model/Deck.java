package com.example.spring_poker.api.Model;
import java.util.ArrayList;
import java.util.Collections;


public class Deck{
    // H - Hearts, D - Diamonds, C - Clubs, S - Spades
    String[] types = {"❤️", "♦️", "♣️", "♠️"};
    ArrayList<Card> cards = new ArrayList<Card>();

    public Deck(){newDeck();}

    public void newDeck(){
        if(cards!=null){cards.clear();}
        for(int i = 0; i < 4; i++){
            String type = types[i];
            for(int y = 2; y <= 14; y++){
                Card card = new Card(y, type);
                cards.add(card);
            }
        }
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public int getLength(){
        return cards.size();
    }

    public Card drawCard(){
        Card top_card = cards.get(0);
        cards.remove(0);
        return top_card;
    }
    
    @Override
    public String toString(){
        int counter = 0;
        String all_cards = "";
        for(Card card: cards){
            all_cards += card + ",";
            counter++;
            if(counter % 13 == 0){all_cards+="\n";}
        }
        return all_cards;
    }
}