import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import Model.Player;

public class View extends JFrame{
    JPanel playing_field, command_field, top, right, bottom, left, center;
    JPanel[] panel = {top, right, bottom, left};
    String[] layouts = {BorderLayout.NORTH, BorderLayout.EAST, BorderLayout.SOUTH, BorderLayout.WEST};
    JLabel player_1, player_2, player_3, player_4, pot, table;
    String[] player_label_string = {"Player 1", "Player 2", "Player 3", "Player 4"};
    JLabel[] player_label = {player_1, player_2, player_3, player_4};
    JLabel chips_1, chips_2, chips_3, chips_4;
    JLabel[] player_chips = {chips_1, chips_2, chips_3, chips_4};

    JTextField user_input;

    JPanel card_panel;
    ArrayList<JLabel> table_cards = new ArrayList<>();

    Player active_player;
    Border blackline = BorderFactory.createLineBorder(Color.black);

    JButton raise, bet, call, check, fold;
    JButton[] buttons = {raise, bet, call, check, fold};
    String[] button_labels = {"Raise", "Bet", "Call", "Check", "Fold"};

    GridBagConstraints gbc;
    public View(){
        
        super("Open Poker");
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLUE));
        
        playing_field = new JPanel(new BorderLayout());
        command_field = new JPanel(new GridBagLayout());

        makeTable();
        makeButtons();

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        command_field.add(user_input = new JTextField("0"), gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;        
        add(playing_field, gbc);
        gbc.gridy = 1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        add(command_field, gbc);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public JButton[] getButtons(){
        return buttons;
    }
    public void makeButtons(){
        gbc.gridy = 0;
        gbc.weighty = 0.8;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        for(int i = 0; i < buttons.length; i++){
            gbc.gridx = i;
            buttons[i] = new JButton(button_labels[i]);
            command_field.add(buttons[i], gbc);
        }
    }
    public void makeTable(){
        GridBagConstraints c;
        // Make Players
        for(int i = 0; i < panel.length; i++){
            JPanel current_panel = panel[i];
            c = new GridBagConstraints();

            current_panel = new JPanel();
            current_panel.setLayout(new GridBagLayout());
            current_panel.setBorder(blackline);
            current_panel.add(player_label[i] = new JLabel(player_label_string[i], JLabel.CENTER), c);
            c.gridy = 1;
            current_panel.add(player_chips[i] = new JLabel("Chips: ", JLabel.CENTER), c);
            playing_field.add(current_panel, layouts[i]);
            }
        // Make Table
        c = new GridBagConstraints();
        center = new JPanel();
        center.setLayout(new GridBagLayout());
        center.setBorder(blackline);
        card_panel = new JPanel();
        center.add(card_panel, c);
        pot = new JLabel("Pot: 0", JLabel.CENTER);
        c.gridy = 1;
        center.add(pot, c);
        playing_field.add(center);
    }
    public void addActionListener(ActionListener listener, int button){
        // 0 - Raise, 1 - Bet, 2 - Call, 3 - Fold
        buttons[button].addActionListener(listener);
        
    }
    public void updatePlayerChips(){
        int player_num = active_player.getUid();
        //player_chips[player_num].setText("Chips: " + active_player.getChips());
        player_chips[player_num].setText("Chips: " + active_player.getChips());
    }
    public void setStartChips(int start_chips){
        for(JLabel label: player_chips){
            label.setText("Chips: " + start_chips);
        }
    }
    public void setStage(String stage){
        table.setText(stage);
    }
    public void addTableCard(String card){
        JLabel card_label = new JLabel(card);
        card_label.setVisible(false);
        table_cards.add(card_label);
        card_panel.add(card_label);
    }
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
        pot.setText("Pot: " + new_pot);
    }
    public void resetPot(){
        pot.setText("Pot: " + 0);
    }
    public void setActivePlayer(Player active){
        active_player = active;
        player_label[active_player.getUid()].setForeground(Color.RED);
    }   
    public void resetActivePlayerColor(Player active){
        player_label[active_player.getUid()].setForeground(Color.BLACK);
    }
    public void setPlayerStatus(Player player){
        player_label[player.getUid()].setText(player.getName() + " " + player.getStatus());
    }
    public void resetPlayerStatus(Player player){
        player_label[player.getUid()].setText(player.getName());
    }
    public JTextField getTextField(){
        return user_input;
    }
    public void resetTextLabel(){
        user_input.setText("0");
    }
    // 0 - Raise, 1 - Bet, 2 - Call, 3 - Fold
    public JButton getButton(int num){
        return buttons[num];
    }
}
