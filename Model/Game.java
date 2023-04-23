package Model;


public class Game {
    Player[] players;
    Table table;
    Poker_Hand[] poker_hands;
    int start_chips; 

    public Game(int start_chips){
        players = new Player[]{new Player("P1"), new Player("P2")};
        this.start_chips = start_chips;
        table = new Table(players, start_chips);

    }
    public Player play(){
        Player winner;
        table.newRound();
        table.setBets();
        winner = table.getWinner();
        if(winner != null){return winner;}
        table.drawFlop();
        table.setBets();
        winner = table.getWinner();
        if(winner != null){return winner;}
        table.drawRiver();
        table.setBets();
        winner = table.getWinner();
        if(winner != null){return winner;}

        return null;
    }
    public void playerWins(Player player){
        player.addChips(table.getPot());
    }
    public int getStartChips(){
        return start_chips;
    }
    public Player getActivePlayer(){
        System.out.println(table.getActivePlayer().getUid());
        return table.getActivePlayer();
    }
    public static void main(String[] args) {
        Game g = new Game(50);
        g.play();
        System.out.println(g.table);
    }
}