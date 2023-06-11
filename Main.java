import java.util.ArrayList;

import Model.*;
import View.Player_View;
import View.Setup_View;
import View.Table_View;

public class Main {
    static Player[] players;


    public static void useSetup(){
        Setup_View sv = new Setup_View();
        ArrayList<String> player_names = sv.getPlayerNames();
        players = new Player[player_names.size()];
        for(int i = 0; i < player_names.size(); i ++){
            players[i] = new Player(player_names.get(i));
        }
    }

    public static void defaultPlayers(){
        players = new Player[4];
        players[0] = new Player("P1");
        players[1] = new Player("P2");
        players[2] = new Player("P3");
        players[3] = new Player("P4");
    }

    public static void main(String[] args) {
        defaultPlayers();
        Player_View pview = new Player_View();
        Table_View view = new Table_View();
        Table table = new Table(players, 50);

        pview.setLocation(view.getX() + view.getWidth()/2 - pview.getWidth()/2, view.getY() - pview.getHeight());
        new Controller(view, table);
    }
}
