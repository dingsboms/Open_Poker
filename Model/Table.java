package Model;
import java.util.ArrayList;

public class Table {
    Deck deck = new Deck();
    Player[] players;
    CircularPlayerList player_order = new CircularPlayerList();
    ArrayList<Card> table_cards = new ArrayList<>();
    int start_chips;
    int pot = 0;
    Player dealer;
    Player active_player;
    Player winner;

    public Table(Player[] players, int start_chips){
        this.players = players;
        this.players[0].setDealer();
        this.start_chips = start_chips;
        for(Player player:players){
            player.setChips(start_chips);
            player_order.addNode(player);
        }
    }

    public void newRound(){
        winner = null;
        player_order.moveDealerAndBlinds();
        deck.newDeck();
        deck.shuffle();
        pot = 0;

        // Deal cards to players
        for(Player p: players){
            p.drawHand(deck);
        }
    }

    // To-do
    public void setBets(){
        int highest_bet = 0;
        for(Player player: players){
            if(player.isTurn()){
             
            }
        }
    }
    public void drawFlop(){
        if(!table_cards.isEmpty()){table_cards.clear();}
        table_cards.add(deck.drawCard());
        table_cards.add(deck.drawCard());
        table_cards.add(deck.drawCard());
    }

    public void drawTurn(){
        table_cards.add(deck.drawCard());
    }

    public void drawRiver(){
        table_cards.add(deck.drawCard());
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

    public void setTableCards(String card1, String card2, String card3, String card4, String card5){
        table_cards.clear();
        Card card;
        String[] stringCards = {card1, card2, card3, card4, card5};
        for(String string_card:stringCards){
            String str[] = string_card.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
            card = new Card(Integer.parseInt(str[0]), str[1].charAt(0));
            table_cards.add(card);
        }
    }
    public ArrayList<Card> getTableCards(){
        return table_cards;
    }
    public Player[] getPlayers(){
        return players;
    }
    public int getPot(){
        return pot;
    }
    public Player getActivePlayer(){
        for(Player player: players){
            if(player.isTurn()){active_player = player;}
        }
        return active_player;
    }
    public Player getWinner(){
        int num_of_active_players = 0;
        for(Player p: players){
            if(!p.hasFolded()){
                num_of_active_players++;
                winner = p;}
        }
        if(num_of_active_players == 1){return winner;}
        return null;
    }
    @Override
    public String toString(){
        String players_string = "";
        for(Player p: players){players_string += p + " ";}
        return players_string + "Pot: " + pot;
    }

    public static void main(String[] args) {
        Player[] players = new Player[2];
        players[0] = new Player("Tarun");
        players[1] = new Player("Peter");
        Table table = new Table(players, 50);
        
        table.newRound();
        table.viewHands();
        table.drawFlop();
        System.out.println(table.getTableCards());
        System.out.println(table.deck.getLength());

        table.newRound();
        table.viewHands();
        table.drawFlop();
        table.drawTurn();
        System.out.println(table.getTableCards());
        System.out.println(table.deck.getLength());
    }   
}