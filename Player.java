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
    Scanner user_input;

    public Player(String name){
        this.name = name;
        user_input = new Scanner(System.in);
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
    public ArrayList<Card> getHand(){
        return hand;
    }
    public void drawHand(Deck deck){
        if(!hand.isEmpty()){hand.clear();}
        hand.add(deck.drawCard());
        hand.add(deck.drawCard());
    }
    public int getChips(){
        return chips;
    }
    public void setChips(int amount){
        chips = amount;
    }
    public void addChips(int pot){
        chips += pot;
    }

    public int setBet(){
        try {
            int bet = Integer.parseInt(user_input.nextLine());
            chips -= bet;
            this.bet = bet;
            return bet;
        } catch (Exception e) {
            return 0;
        }
    }
    public int getBet(){
        return bet;
    }

    public void resetBet(){
        bet = 0;
    }
    @Override
    public String toString(){
        return name;
    }
}
