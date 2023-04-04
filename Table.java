import java.util.ArrayList;

public class Table {
    Deck deck = new Deck();
    Player[] players;
    ArrayList<Card> table_cards = new ArrayList<>();

    public Table(Player[] players){
        this.players = players;
    }

    public void newRound(Player[] players){
        deck.newDeck();
        deck.shuffle();

        // Deal cards to players
        for(Player p: players){
            p.drawHand(deck);
        }
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

    public static void main(String[] args) {
        Player[] players = new Player[2];
        players[0] = new Player("Tarun");
        players[1] = new Player("Peter");
        Table table = new Table(players);
        
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