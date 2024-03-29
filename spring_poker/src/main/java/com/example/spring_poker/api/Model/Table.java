package com.example.spring_poker.api.Model;
import java.util.ArrayList;

public class Table {
    Deck deck = new Deck();
    ArrayList<Player> players;
    CircularPlayerList player_order = new CircularPlayerList();
    ArrayList<Card> table_cards = new ArrayList<>();
    int start_chips;
    int pot = 0;
    int big_blind = 2;
    Player dealer;
    Player active_player;

    public Table(ArrayList<Player> players, int start_chips){
        this.players = players;
        this.start_chips = start_chips;
        for(Player player:players){
            player.setChips(start_chips);
            player_order.addNode(player);
        }
        this.players.get(1).setSmallBlind();
        player_order.moveDealerAndBlinds();
        deck.shuffle();
        dealHands();
        setBlinds();
    }

    public void newRound(){
        table_cards.clear();
        deck.newDeck();
        deck.shuffle();
        pot = 0;

        dealHands();
        setBlinds();
    }

    public void dealHands(){
        for(Player p: players){
            p.drawHand(deck);
        }
    }

    public void setBlinds(){
        for(Player p: players){
            if(p.isBigBlind()){p.setBet(big_blind); addToPot(big_blind);}
            else if(p.isSmallBlind()){p.setBet(big_blind/2); addToPot(big_blind/2);}
            else{p.setBet(0);}
        }
    }

    public ArrayList<Card> drawFlop(){
        table_cards.add(deck.drawCard());
        table_cards.add(deck.drawCard());
        table_cards.add(deck.drawCard());
        return table_cards;
    }

    public ArrayList<Card> drawTurn(){
        table_cards.add(deck.drawCard());
        return table_cards;
    }

    public ArrayList<Card> drawRiver(){
        table_cards.add(deck.drawCard());
        return table_cards;
    }

    public ArrayList<Card> drawAllTableCards(){
        drawFlop();
        drawTurn();
        drawRiver();
        return table_cards;
    }

    public void viewHands(){
        String hands = "";
        for(Player p: players){
            hands = hands + p + " " + p.getHand() + " , ";
        }
        System.out.println(hands);
    }

    public void viewTableCards(){
        System.out.println("Table cards \n" + table_cards);
    }

    // Artificially place table cards
    public void setTableCards(String card1, String card2, String card3, String card4, String card5){
        table_cards.clear();
        Card card;
        String[] stringCards = {card1, card2, card3, card4, card5};
        for(String string_card:stringCards){
            String str[] = string_card.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
            card = new Card(Integer.parseInt(str[0]), str[1]);
            table_cards.add(card);
        }
    }
    public ArrayList<Card> getTableCards(){return table_cards;}

    public ArrayList<Player> getPlayers(){return players;}

    public int addToPot(int bet){
        pot += bet;
        return pot;
    }

    public int getPot(){return pot;}

    public Player getActivePlayer(){
        for(Player player: players){
            if(player.isTurn()){active_player = player;}
        }
        return active_player;
    }

    public CircularPlayerList getPlayerOrder(){return player_order;}

    public int getStartChips(){return start_chips;}

    public int getBigBlind(){return big_blind;}
    
    @Override
    public String toString(){
        String players_string = "";
        for(Player p: players){players_string += p + " ";}
        return players_string + "Pot: " + pot;
    }
}