package Model;
public class TestPokerHand {
    public static void main(String[] args) {
        Player[] players = {new Player("Test-player1"), new Player("Test-player2")};
        Table table = new Table(players, 50);

        table.newRound();
        table.drawFlop();
        table.drawTurn();
        table.drawRiver();

        // // Straight-flush
        // table.setTableCards("H13", "H12", "H11", "H10", "H9");

        table.setTableCards("14S", "11D", "10S", "4S", "2S");
        players[0].setHand("3D", "14H");
        players[1].setHand("4D", "3C");

        Poker_Hand player_hand1 = new Poker_Hand(players[0], table);
        Poker_Hand player_hand2 = new Poker_Hand(players[1], table);
        table.viewHands();
        table.viewTableCards();
        player_hand1.showBestHand();
        player_hand2.showBestHand();

        if(player_hand1.compareTo(player_hand2) > 0) {System.out.println(players[0] + " wins " + player_hand1.compareTo(player_hand2));}
        else if(player_hand1.compareTo(player_hand2) == 0){System.out.println("Draw");}
        else{System.out.println(players[1] + " wins " + player_hand1.compareTo(player_hand2));}
    }
}
