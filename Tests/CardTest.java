package Tests;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.example.spring_poker.api.Model.*;

public class CardTest {

    Card c;
    Card c2;
    
    @Before
    public void setUp(){
        c = new Card(3, 'H');
        c2 = new Card(12, 'D');
    }
    
    @Test
    public void cardNumIsThree(){
        assertEquals(3, c.getNum());
    }

    @Test
    public void cardTypeIsHearts(){
        assertEquals('H', c.getType());
    }

    @Test
    public void queenOfDiamondsComparedToThreeOfHeartsIsNine(){
        assertEquals(9, c2.compareTo(c));
    }
}
