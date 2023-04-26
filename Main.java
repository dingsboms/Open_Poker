import Model.*;

public class Main {
    static Player[] players;

    public static void main(String[] args) {
        players = new Player[]{new Player("P1"), new Player("P2"), new Player("P3"), new Player("P4")};

        View view = new View();
        Table table = new Table(players, 50);

        Controller c =new Controller(view, table);
    }
}
