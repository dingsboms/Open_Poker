package Tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Model.Deck;
import Model.Card;

public class DeckTest {
    Deck d;

    @Before
    public void setUp(){
        d = new Deck();
    }

    @Test
    public void cardsCorrectUnshuffled(){
        char[] types = {'H', 'D', 'C', 'S'};
        for(int y = 0; y < 4; y++){
            char expected_type = types[y];
            for(int i = 2; i <= 14; i++){
                Card c = d.drawCard();
                int num = c.getNum();
                char type = c.getType();
                assertEquals(i, num);
                assertEquals(expected_type, type);
        }
        }
    }
    
    @Test
    public void drawCardRemovesOneFromDeck(){
        for(int i = 51; i > 0; i--){
            d.drawCard();
            assertEquals(i, d.getLength());
        }
    }
}
