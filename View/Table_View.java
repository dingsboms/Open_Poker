package View;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import Model.Player;

public class Table_View extends JFrame{
    Command_Palette command_field;
    Playing_Field playing_field;
    ArrayList<JLabel> table_cards = new ArrayList<>();

    public Table_View(){
        
        super("Open Poker");
        setLayout(new GridBagLayout());

        getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLUE));
        
        playing_field = new Playing_Field();
        command_field = new Command_Palette();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(playing_field, gbc);
        gbc.gridy = 1;
        gbc.weighty = 0.1;
        add(command_field,gbc);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    

    public void addActionListener(ActionListener listener, int button){
        // 0 - Raise, 1 - Bet, 2 - Call, 3 - Fold
        command_field.addActionListener(listener, button);
    }
    
    public void updatePlayerChips(int player_uid, int new_chips){
        playing_field.player_chips[player_uid].setText("Chips: " + new_chips);
    }

    public void setStage(String stage){
        playing_field.table.setText(stage);
    }

    public void clearTableCards(){
        table_cards.clear();
    }

    public void addTableCard(String card){
        JLabel card_label = new JLabel(card);
        card_label.setVisible(false);
        table_cards.add(card_label);
        playing_field.card_panel.add(card_label);
    }

    public void hideTableCards(){
        for(JLabel l: table_cards){l.setVisible(false);}
    }

    public void setAnnouncement(String sentence){
        playing_field.announcement.setText(sentence);
        playing_field.announcement.setVisible(true);
    }

    public void hideAnnouncement(){playing_field.announcement.setVisible(false);}

    public void showFlop(){
        table_cards.get(0).setVisible(true);
        table_cards.get(1).setVisible(true);
        table_cards.get(2).setVisible(true);
    }

    public void showTurn(){
        table_cards.get(3).setVisible(true);
    }

    public void showRiver(){
        table_cards.get(4).setVisible(true);
    }

    public void setPot(int new_pot){
        playing_field.pot.setText("Pot: " + new_pot);
    }

    public void setActivePlayer(int player_uid){
        playing_field.player_label[player_uid].setForeground(Color.RED);
    }   

    public void resetActivePlayerColor(int player_uid){
        playing_field.player_label[player_uid].setForeground(Color.BLACK);
    }

    public void setPlayerStatus(Player player){
        playing_field.player_label[player.getUid()].setText(player.getName() + " " + player.getStatus());
    }

    public void resetPlayerStatus(Player player){
        playing_field.player_label[player.getUid()].setText(player.getName());
    }

    public JTextField getTextField(){return command_field.getTextField();}

    public void resetTextLabel(){command_field.resetTextLabel();}

    // 0 - Raise, 1 - Bet, 2 - Call, 3 - Fold
    public JButton getButton(int num){return command_field.getButton(num);}
}
