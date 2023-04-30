import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JTextField;

import Model.*;

public class Controller {
    View view;
    Table table;
    JTextField text_field;
    HashMap<Character, Integer> button_map;
    int round = 1;

    CircularPlayerList player_order;
    Player[] players;
    ArrayList<Card> table_cards;
    Player active_player;
    Player highest_bidder;
    Player big_blind_player;
    int highest_bet;
    int stage;
    int num_of_active_players;
    int big_blind;

    Controller(View view, Table table){
        this.view = view;
        this.table = table;
        text_field = view.getTextField();
        addActionListeners();
        big_blind = table.getBigBlind();
        highest_bet = big_blind;
        stage = 0;

        button_map = new HashMap<>();
        // k is for check
        button_map.put('r', 0);
        button_map.put('b', 1);
        button_map.put('c', 2);
        button_map.put('k', 3);
        button_map.put('f', 4);

        players = table.getPlayers();
        num_of_active_players = players.length;
        player_order = table.getPlayerOrder();
        table_cards = table.drawAllTableCards();
        big_blind_player = getBigBlindPlayer();
        highest_bidder = getDealer();
        System.out.println("Round: " + round);
        getActivePlayer();

        // Implement method to use in newRound()
        for(Card c: table_cards){view.addTableCard(c.toString());}
        setPlayersStatus();
        updateView();
    }

    // For example "rcf" would show Raise, Call, Fold. (k is for check)
    public void showButtons(String button_chars){
        hideAllButtons();
        for(int i = 0; i < button_chars.length(); i++){
            char c = button_chars.charAt(i);
            showButton(button_map.get(c));
        }
    }

    public void hideAllButtons(){
        for(int i = 0; i < 5; i++){
            hideButton(i);
        }
    }

    // 0 - Raise, 1 - Bet, 2 - Call, 3 - Fold
    public void showButton(int button_num){
        JButton b = view.getButton(button_num);
        b.setVisible(true);
    }

    public void hideButton(int button_num){
        JButton b = view.getButton(button_num);
        b.setVisible(false);
    }

    public void updateView(){
        for(Player p: players){
            view.updatePlayerChips(p.getUid(), p.getChips());
        }
        view.setPot(table.getPot());
    }

    public void showDown(){
        // Gets Poker Hands of unfolded players
        ArrayList<Poker_Hand> poker_hands = new ArrayList<>();
        for(Player p: players){
            if(!p.hasFolded()){poker_hands.add(new Poker_Hand(p, table));}
        }

        ArrayList<Poker_Hand> best_hands = new ArrayList<>();
        best_hands.addAll(poker_hands);
        Poker_Hand best_hand = poker_hands.get(0);
        for(Poker_Hand h1: poker_hands){
            if(h1.compareTo(best_hand) > 0){
                best_hands.remove(best_hand);
                best_hand = h1; 
            }
            else if(h1 == best_hand){}
            else if(h1.compareTo(best_hand) == 0 && h1 != best_hand){}
            else{best_hands.remove(h1);}
        }

        if(best_hands.size() > 1 && best_hands.contains(best_hand)){
            int num_of_winners = best_hands.size();
            System.out.println("Pot is split between " + num_of_winners + " players;");
            for(Poker_Hand ph: best_hands){
                System.out.println(ph.getPlayer().getName() + " with " + ph.getBestHand());
            }
        }
        else{
            System.out.println(best_hand.getPlayer().getName() + " wins with " + best_hand.getTypeofHand());
            System.out.println(best_hand.getBestHand());
            best_hand.getPlayer().addChips(table.getPot());}
        newRound();
    }

    public Player getWinnerBeforeShow(){
        System.out.println("Winner is " + active_player.getName());
        active_player.addChips(table.getPot());
        newRound();
        return active_player;
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
        view.resetActivePlayerColor(active_player);

        round++;
        num_of_active_players = players.length;
        stage = 0; // Pre-flop
        highest_bet = big_blind;

        player_order.moveDealerAndBlinds();
        setPlayersStatus();
        highest_bidder = getDealer();
        table.newRound();
        drawNewTableCards();

        System.out.println("Round: " + round);
        getActivePlayer();

        updateView();
    }
    
    public void nextPersonsTurn(){
        view.resetActivePlayerColor(active_player);
        if(player_order.getNextPersonInTurn().getPlayer() == highest_bidder){nextStage();}
        else{
            player_order.nextPersonsTurn();
            getActivePlayer();
        }
        if(num_of_active_players == 1){getWinnerBeforeShow();}
    }

    public void nextPersonAfterDealersTurn(){
        view.resetActivePlayerColor(active_player);
        player_order.nextPersonAfterDealersTurn();
        getActivePlayer();
        highest_bet = 0;
        highest_bidder = active_player;
    }

    public void drawNewTableCards(){
        view.hideTableCards();
        table_cards.clear();
        table_cards = table.drawAllTableCards();
        for(Card c: table_cards){view.addTableCard(c.toString());}
    }

    public void setPlayersStatus(){
        for(Player p: players){
            if(p.getStatus() != "N"){view.setPlayerStatus(p);}
            else{view.resetPlayerStatus(p);}
        }
    }

    public Player getActivePlayer(){
        active_player = table.getActivePlayer();
        System.out.println("Active Player: " + active_player.getName() + " Cards: " + active_player.getHand());
        view.setActivePlayer(active_player);
        if(active_player.getBet() == highest_bet){showButtons("bkf");}
        else{showButtons("rcf");}
        return active_player;
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
        updateView();
    }

    public void addActionListeners(){
        view.addActionListener(new Raise(), 0);
        view.addActionListener(new Bet(), 1);
        view.addActionListener(new Call(), 2);
        view.addActionListener(new Check(), 3);
        view.addActionListener(new Fold(), 4);
    }

    class Raise implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String input = text_field.getText();
            try{int raise = Integer.parseInt(input); if(raise <= 0 || raise > active_player.getChips()){throw new NumberFormatException();}
                setActivePlayerBet(highest_bet + raise);
                addTablePot(highest_bet + raise);
                highest_bet += raise;
                highest_bidder = active_player;
                nextPersonsTurn();
                view.resetTextLabel();
                }
            catch(NumberFormatException ex){System.out.println("Invalid input");}
        }
    }
    class Bet implements ActionListener{
    
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = text_field.getText();
            try{int bet = Integer.parseInt(input); if(bet <= 0 || bet > active_player.getChips()){throw new NumberFormatException();}
                setActivePlayerBet(bet);
                addTablePot(bet);
                highest_bet = bet;
                highest_bidder = active_player;
                showButtons("rcf");
                nextPersonsTurn();
                view.resetTextLabel();
                }
            catch(NumberFormatException ex){System.out.println("Invalid input");}
        }
    }

    class Call implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            setActivePlayerBet(highest_bet - active_player.getBet());
            addTablePot(active_player.getBet());
            nextPersonsTurn();
        }
    }

    class Check implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            nextPersonsTurn();
        }
    }

    class Fold implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            active_player.fold();
            num_of_active_players --;
            Player folded_player = active_player;
            nextPersonsTurn();
            // Moves highest_bidder to next person in turn in case the folded player was the highest bidder
            if(highest_bidder == folded_player){highest_bidder = active_player;}
        }
    }
}
