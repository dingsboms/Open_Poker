import java.util.ArrayList;

public class Player {
    String name;
    ArrayList<Card> hand = new ArrayList<>();

    public Player(String name){
        this.name = name;
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

    @Override
    public String toString(){
        return name;
    }
}
