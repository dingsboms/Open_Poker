package View;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

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

    public Command_Palette(){
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
}
