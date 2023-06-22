import java.util.ArrayList;

import Model.*;
import View.Setup_View;
import View.Table_View;

public class Main {
    static ArrayList<Player> players = new ArrayList<>();

    public static void useSetup(){
        Setup_View sv = new Setup_View();
        ArrayList<String> player_names = sv.getPlayerNames();
        for(String name: player_names){
            players.add(new Player(name));
        }
    }

    public static void defaultPlayers(){
        for(int i = 1; i < 5; i++){
            players.add(new Player("P" + i));
        }
    }

    public static void main(String[] args) {
        defaultPlayers();
        Table_View view = new Table_View(players, 150);
        Table table = new Table(players, 50);

        Controller c = new Controller(view, table);
        c.usePlayerViews();
    }
}
