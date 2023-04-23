package Model;
import java.util.ArrayList;
import java.util.Collections;


public class Deck{
    // H - Hearts, D - Diamonds, C - Clubs, S - Spades
    char[] types = {'H', 'D', 'C', 'S'};
    ArrayList<Card> cards = new ArrayList<Card>();

    public Deck(){newDeck();}

    public void newDeck(){
        if(cards!=null){cards.clear();}
        for(int i = 0; i < 4; i++){
            char type = types[i];
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
    public static void main(String[] args) {
        Deck kortstokk = new Deck();
        System.out.println(kortstokk);
        System.out.println("Shuffling deck");
        kortstokk.shuffle();
        System.out.println("New Deck");
        System.out.println(kortstokk);
        System.out.println("Shuffling again");
        kortstokk.shuffle();
        System.out.println(kortstokk);
        System.out.println("Top card:");
        System.out.println(kortstokk.drawCard());
        System.out.println("Rest of deck:");
        System.out.println(kortstokk);
        System.out.println(kortstokk.getLength());
        kortstokk.newDeck();
        System.out.println(kortstokk);
        System.out.println(kortstokk.getLength());


    }
}