package Tests;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Model.Player;

public class PlayerTest {
    
    Player p;

    @Before
    public void setUp(){
        p = new Player("Test-Player");
    }

    @Test
    public void playerNameIsTestPlayer(){
        assertEquals("Test-Player", p.getName());
    }
}
