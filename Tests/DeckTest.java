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
    public void firstCardIsTwoOfHearts(){
        Card c1 = d.drawCard();
        int num = c1.getNum();
        char type = c1.getType();
        assertEquals(2, num);
        assertEquals('H', type);
    }
    
}
