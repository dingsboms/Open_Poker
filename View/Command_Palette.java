package View;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Command_Palette extends JPanel{
    JButton raise, bet, call, check, fold, new_round;
    JButton[] buttons = {raise, bet, call, check, fold, new_round};
    String[] button_labels = {"Raise", "Bet", "Call", "Check", "Fold", "New Round"};
    GridBagLayout gbl;
    GridBagConstraints gbc;
    JTextField user_input;
    HashMap<Character, Integer> button_map;


    public Command_Palette(){
        button_map = new HashMap<>();
        // k is for check
        button_map.put('r', 0);
        button_map.put('b', 1);
        button_map.put('c', 2);
        button_map.put('k', 3);
        button_map.put('f', 4);
        button_map.put('n', 5);

        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();
        setLayout(gbl);
        gbc.gridy = 0;
        gbc.weighty = 0.8;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        for(int i = 0; i < buttons.length; i++){
            gbc.gridx = i;
            buttons[i] = new JButton(button_labels[i]);
            add(buttons[i], gbc);
        }
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = buttons.length;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(user_input = new JTextField("0"), gbc);
    }
    // 0 - Raise, 1 - Bet, 2 - Call, 3 - Fold
    public JButton getButton(int num){return buttons[num];}

    public void addActionListener(ActionListener listener, int button){
        // 0 - Raise, 1 - Bet, 2 - Call, 3 - Fold
        buttons[button].addActionListener(listener);
    }
    public JTextField getTextField(){return user_input;}
    
    public void resetTextLabel(){
        user_input.setText("0");
    }

    // For example "rcf" would show Raise, Call, Fold. (k is for check)
    public void showButtons(String button_chars){
        hideAllButtons();
        for(int i = 0; i < button_chars.length(); i++){
            char c = button_chars.charAt(i);
            showButton(button_map.get(c));
        }
    }

    public void hideAllButtons(){for(int i = 0; i < button_map.size(); i++){hideButton(i);}}

    // 0 - Raise, 1 - Bet, 2 - Call, 3 - Fold
    public void showButton(int button_num){
        buttons[button_num].setVisible(true);
    }

    public void hideButton(int button_num){
        buttons[button_num].setVisible(false);
    }
}
