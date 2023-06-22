package View;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import Model.Player;

public class Table_View extends JFrame{
    Command_Palette command_field;
    Playing_Field playing_field;
    int playing_field_size, player_view_size;
    ArrayList<Player> players;
    ArrayList<String> table_cards;

    public Table_View(ArrayList<Player> players, int player_view_size){
        super("Open Poker");
        this.players = players;
        this.player_view_size = player_view_size;

        playing_field_size = 500;

        table_cards = new ArrayList<>();
        setLayout(new GridBagLayout());

        getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLUE));
        
        playing_field = new Playing_Field(players, playing_field_size);
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
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(playing_field_size, playing_field_size);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void clearTableCards(){
        table_cards.clear();
    }

    public void addTableCard(String card){
        table_cards.add(card);
    }

    public void hideTableCards(){
        playing_field.card_panel = "";
    }

    public void setAnnouncement(String sentence){
        playing_field.announcement = sentence;
    }

    public void hideAnnouncement(){playing_field.announcement = "";}

    public void showFlop(){
        playing_field.card_panel = table_cards.get(0) + " " + table_cards.get(1) + " " + table_cards.get(2)+ " ";
    }

    public void showTurn(){
        playing_field.card_panel = playing_field.card_panel + table_cards.get(3) + " ";
    }

    public void showRiver(){
        playing_field.card_panel = playing_field.card_panel + table_cards.get(4);
    }

    public void setPot(int new_pot){
        playing_field.pot = "Pot: " + new_pot;
    }

    public void resetTextLabel(){command_field.resetTextLabel();}

    // 0 - Raise, 1 - Bet, 2 - Call, 3 - Fold
    public JButton getButton(int num){return command_field.getButton(num);}

    public Command_Palette getCommand_Palette(){return command_field;}

    public void refresh(){
        playing_field.refresh();}
}
