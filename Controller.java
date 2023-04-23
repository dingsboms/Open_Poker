import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import Model.*;

public class Controller {
    View view;
    Game game;
    Player active_player;
    JTextField text_field;
    
    Controller(View view, Game game){
        this.view = view;
        this.game = game;
        text_field = view.getTextField();
        addActionListeners();
        view.setStartChips(game.getStartChips());
    }

    public void play(){
        active_player = game.getActivePlayer();
        view.setActivePlayer(active_player);
        game.play();
    }

    public void addActionListeners(){
        view.addActionListener(new Raise(), 0);
        view.addActionListener(new Bet(), 1);
        view.addActionListener(new Call(), 2);
        view.addActionListener(new Fold(), 3);
    }
    class Raise implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
    }
    class Bet implements ActionListener{
    
        @Override
        public void actionPerformed(ActionEvent e) {
            String input = text_field.getText();
            try{int bet = Integer.parseInt(input);
                active_player = game.getActivePlayer();
                active_player.setBet(bet);}
            catch(NumberFormatException ex){System.out.println("Invalid input");}
        }
    }
    class Call implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }
        
    }
    class Fold implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }
        
    }
}
