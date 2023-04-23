import Model.*;

public class Main {
    public static void main(String[] args) {
        View view = new View();
        Game game = new Game(50);

        Controller c =new Controller(view, game);
        c.play();
    }
}
