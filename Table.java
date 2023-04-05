import java.util.ArrayList;

public class Table {
    Deck deck = new Deck();
    Player[] players;
    ArrayList<Card> table_cards = new ArrayList<>();
    int start_chips;
    int pot = 0;

    public Table(Player[] players, int start_chips){
        this.players = players;
        this.start_chips = start_chips;
        for(Player player:players){
            player.setChips(start_chips);
        }
    }

    public void newRound(Player[] players){
        deck.newDeck();
        deck.shuffle();

        // Deal cards to players
        for(Player p: players){
            p.drawHand(deck);
        }
    }

    public void setBets(){
        int player_bet;
        int highest_bet = 0;
        for(Player player:players){
            System.out.println(player + ", your bet;");
            player_bet = player.setBet();
            if(player_bet > highest_bet){highest_bet = player_bet;}
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

    public static void main(String[] args) {
        Player[] players = new Player[2];
        players[0] = new Player("Tarun");
        players[1] = new Player("Peter");
        Table table = new Table(players, 50);
        
        table.newRound(players);
        table.viewHands();
        table.drawFlop();
        System.out.println(table.getTableCards());
        System.out.println(table.deck.getLength());

        table.newRound(players);
        table.viewHands();
        table.drawFlop();
        table.drawTurn();
        System.out.println(table.getTableCards());
        System.out.println(table.deck.getLength());
    }   
}