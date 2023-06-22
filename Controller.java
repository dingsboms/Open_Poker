import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTextField;

import Model.*;
import View.Command_Palette;
import View.Coordinates;
import View.Player_View;
import View.Table_View;

public class Controller {
    Table_View view;
    Table table;
    JTextField text_field;
    int round = 1;

    CircularPlayerList player_order;
    ArrayList<Player> players;
    ArrayList<Card> table_cards;
    Player active_player, highest_bidder, big_blind_player;
    int highest_bet, stage, num_of_active_players, big_blind;
    Command_Palette cp;

    ArrayList<Player_View> player_views;
    int player_view_size = 150;
    Boolean player_view_in_use = false;


    Controller(Table_View view, Table table){
        this.view = view;
        this.table = table;
        big_blind = table.getBigBlind();
        highest_bet = big_blind;
        stage = 0;
        
        cp = view.getCommand_Palette();
        addActionListeners();
        text_field = cp.getTextField();

        players = table.getPlayers();
        num_of_active_players = players.size();
        player_order = table.getPlayerOrder();
        table_cards = table.drawAllTableCards();
        for(Card c: table_cards){view.addTableCard(c.toString());}
        big_blind_player = getBigBlindPlayer();
        highest_bidder = getDealer();
        System.out.println("Round: " + round);
        
        updateView();
    }

    public void updateView(){
        getActivePlayer();
        if(player_view_in_use){cp = active_player.getPlayerView().getCommand_Palette(); text_field = cp.getTextField();}
        updateCommandPallette();
        view.setPot(table.getPot());
        view.refresh();
    }

    // Needs implementation of All-in cases
    public void showDown(){
        // Gets Poker Hands of unfolded players
        ArrayList<Player> active_players = getActivePlayers();
        ArrayList<Poker_Hand> poker_hands = makePokerHandsOfPlayers(active_players);
        ArrayList<Poker_Hand> best_hands = getBestHands(poker_hands);
       
        if(best_hands.size() > 1){splitPot(best_hands);}
        else{
            Poker_Hand winner_hand = best_hands.get(0);
            Player winner = winner_hand.getPlayer();
            System.out.println(winner.getName() + " wins with " + winner_hand.getTypeofHand());
            System.out.println(winner_hand.getBestHand());
            winner.addChips(table.getPot());
            view.setAnnouncement(winner.getName() + " wins with " + winner_hand.getTypeofHand());}
        
        cp.showButtons("n");
        }

    public void getWinnerBeforeShow(){
        for(Player p: players){if(!p.hasFolded()){active_player = p; cp = active_player.getPlayerView().getCommand_Palette();}}
        System.out.println("Winner is " + active_player.getName());
        active_player.addChips(table.getPot());
        view.setAnnouncement("Winner is " + active_player.getName());
        cp.setVisible(true); cp.showButtons("n");
    }

    public void splitPot(ArrayList<Poker_Hand> best_hands){
        int num_of_winners = best_hands.size();
        int chips_per_person = table.getPot() / num_of_winners;
        System.out.println("Pot is split between " + num_of_winners + " players;");
        String winners = "<html>";
        for(Poker_Hand ph: best_hands){
            System.out.println(ph.getPlayer().getName() + " with " + ph.getBestHand());
            winners += ph.getPlayer().getName() + " with " + ph.getBestHand() + "<br>";
            ph.getPlayer().addChips(chips_per_person);
        }
        view.setAnnouncement("Pot is split between " + num_of_winners + " players;" + winners + "</br></html>");

    }

    // Stage : 0 - Pre-Flop, 1 - Flop, 2 - Turn, 3 - River, 4 - Show Hands
    public void nextStage(){
        stage++;
        highest_bet = 0;
        for(Player p : players){p.resetBet();}
        if(stage == 1){view.showFlop(); nextPersonAfterDealersTurn();}
        else if(stage == 2){view.showTurn(); nextPersonAfterDealersTurn();}
        else if(stage == 3){view.showRiver(); nextPersonAfterDealersTurn();}
        else if(stage == 4){showDown();}
    }

    public void newRound(){
        view.hideAnnouncement();

        round++;
        num_of_active_players = players.size();
        stage = 0; // Pre-flop
        highest_bet = big_blind;

        player_order.moveDealerAndBlinds();
        highest_bidder = getDealer();
        table.newRound();
        drawNewTableCards();

        if(player_view_in_use){updatePlayerViews();}

        System.out.println("Round: " + round);

        updateView();
    }
    
    public void nextPersonsTurn(){
        if(num_of_active_players == 1){getWinnerBeforeShow();}
        else if(player_order.getNextPersonInTurn().getPlayer() == highest_bidder){nextStage();}
        else{player_order.nextPersonsTurn(); updateView();}
    }

    public void nextPersonAfterDealersTurn(){
        player_order.nextPersonAfterDealersTurn();
        highest_bet = 0;
        highest_bidder = getActivePlayer();
        updateView();
    }

    public void drawNewTableCards(){
        view.hideTableCards();
        table_cards.clear();
        table_cards = table.drawAllTableCards();
        for(Card c: table_cards){view.addTableCard(c.toString());}
    }

    public ArrayList<Poker_Hand> makePokerHandsOfPlayers(ArrayList<Player> ph_players){
        ArrayList<Poker_Hand> poker_hands = new ArrayList<>();
        for(Player p:ph_players){poker_hands.add(new Poker_Hand(p, table));}
        return poker_hands;
    }

    public ArrayList<Poker_Hand> getBestHands(ArrayList<Poker_Hand> hands_to_compare){
        ArrayList<Poker_Hand> best_hands = new ArrayList<>();

        Poker_Hand best_hand = hands_to_compare.get(0);
        best_hands.add(best_hand);
        for(Poker_Hand hand: hands_to_compare){
            if(hand.compareTo(best_hand) > 0){
                best_hands.clear();
                best_hand = hand;
                best_hands.add(best_hand);
            }
            else if(hand.compareTo(best_hand) == 0 && hand != best_hand){best_hands.add(hand);}
        }
        return best_hands;
    }

    public Player getActivePlayer(){
        active_player = table.getActivePlayer();
        System.out.println("Active Player: " + active_player.getName() + " Cards: " + active_player.getHand());
        return active_player;
    }

    public ArrayList<Player> getActivePlayers(){
        ArrayList<Player> active_players = new ArrayList<>();
        for(Player p: players){if(!p.hasFolded()){active_players.add(p);}}
        return active_players;
    }

    public Player getBigBlindPlayer(){
        for(Player p: players){if (p.isBigBlind()){return p;}}
        return null;
    }

    public Player getDealer(){
        for(Player p: players){if(p.isDealer()){return p;}}
        return null;
    }

    public void addTablePot(int bet){
        int new_pot = table.addToPot(bet);
        view.setPot(new_pot);
    }

    public void setActivePlayerBet(int player_bet){
        active_player.setBet(player_bet);
    }

    public void updateCommandPallette(){
        cp.setVisible(true);
        if(active_player.getBet() == highest_bet){cp.showButtons("bkf");}
        else{cp.showButtons("rcf");}
    }

    public void addActionListeners(){
        cp.addActionListener(new Raise(), 0);
        cp.addActionListener(new Bet(), 1);
        cp.addActionListener(new Call(), 2);
        cp.addActionListener(new Check(), 3);
        cp.addActionListener(new Fold(), 4);
        cp.addActionListener(new New_Round(), 5);
    }

    public void usePlayerViews(){
        Coordinates cords = new Coordinates(view, player_view_size);
        view.getCommand_Palette().setVisible(false);
        player_view_in_use = true;
        player_views = new ArrayList<>();
        for(int i = 0; i < players.size(); i++){
            Player p = players.get(i);
            Player_View pv = new Player_View(p, player_view_size ,cords.getPlayerViewCord(i), view.getCommand_Palette());
            p.setPlayerView(pv);
            player_views.add(pv);
            }
        updatePlayerViews();
        for(Player_View pv : player_views){
                if(pv.getPlayer().isTurn()){
                    cp = pv.getCommand_Palette();
                    text_field = cp.getTextField();
                    }}
        updateCommandPallette();
    }
    

    public void updatePlayerViews(){
        for(Player_View pv : player_views){pv.updateHand();}
    }

    class Poker_Button implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            cp.setVisible(false);
            nextPersonsTurn();
            view.resetTextLabel();
            view.refresh();
            cp = active_player.getPlayerView().getCommand_Palette();
        }

    }
    class Raise extends Poker_Button{

        @Override
        public void actionPerformed(ActionEvent e) {
            String input = text_field.getText();
            try{int raise = Integer.parseInt(input); if(raise <= 0 || raise > active_player.getChips()){throw new NumberFormatException();}
                setActivePlayerBet(highest_bet + raise);
                addTablePot(highest_bet + raise);
                highest_bet += raise;
                highest_bidder = active_player;
                super.actionPerformed(e);
                }
            catch(NumberFormatException ex){System.out.println("Invalid input");}
        }
    }
    class Bet extends Poker_Button{
    
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = text_field.getText();
            try{int bet = Integer.parseInt(input); if(bet <= 0 || bet > active_player.getChips()){throw new NumberFormatException();}
                setActivePlayerBet(bet);
                addTablePot(bet);
                highest_bet = bet;
                highest_bidder = active_player;
                super.actionPerformed(e);
                }
            catch(NumberFormatException ex){System.out.println("Invalid input");}
        }
    }

    class Call extends Poker_Button{

        @Override
        public void actionPerformed(ActionEvent e) {
            setActivePlayerBet(highest_bet - active_player.getBet());
            addTablePot(active_player.getBet());
            super.actionPerformed(e);
        }
    }

    class Check extends Poker_Button{

        @Override
        public void actionPerformed(ActionEvent e){
            super.actionPerformed(e);
        }
    }

    class Fold extends Poker_Button{

        @Override
        public void actionPerformed(ActionEvent e) {
            active_player.fold();
            num_of_active_players --;
            Player folded_player = active_player;
            super.actionPerformed(e);
            // Moves highest_bidder to next person in turn in case the folded player was the highest bidder
            if(highest_bidder == folded_player){highest_bidder = active_player;}
        }
    }
    class New_Round extends Poker_Button{

        @Override
        public void actionPerformed(ActionEvent e){
            cp.setVisible(false);
            newRound();
        }
    }
}
