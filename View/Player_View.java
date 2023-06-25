package View;

import java.awt.GridLayout;
import java.lang.ModuleLayer.Controller;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Player_View extends JFrame{
    JPanel panel;
    JLabel hand;
    Command_Palette cm;
    int x, y;
    String player_name;
    Controller controller;

    public Player_View(String player_name, int size, int x, int y,  Command_Palette command_field){
        super("Player View");
        this.player_name = player_name;
        cm = new Command_Palette();
        copyCommandButtons(cm, command_field);
        this.x = x;
        this.y = y;
        panel = new JPanel(new GridLayout(2,1));
        hand = new JLabel("", SwingConstants.CENTER);
        cm.setVisible(false);
        panel.add(hand);
        panel.add(cm);
        add(panel);

        setLocation(x, y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(size, size);
        setResizable(false);
        setVisible(true);
    }

    public void copyCommandButtons(Command_Palette cp, Command_Palette cpy){
        for(int i = 0; i < 6; i++){cp.addActionListener(cpy.getButton(i).getActionListeners()[0], i);}
    }

    public void updateHand(String s_hand){
        hand.setText(s_hand);
    }

    public Command_Palette getCommand_Palette(){return cm;}
}