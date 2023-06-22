package View;

import java.awt.GridLayout;
import java.lang.ModuleLayer.Controller;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Model.Player;

public class Player_View extends JFrame{
    JPanel panel;
    JLabel hand;
    Command_Palette cm;
    int x, y;
    Player player;
    Controller controller;

    public Player_View(Player player, int size, Coordinate cord, Command_Palette command_field){
        super("Player View");
        this.player = player;
        cm = new Command_Palette();
        copyCommandButtons(cm, command_field);
        x = (int) cord.getX();
        y = (int) cord.getY();
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

    public void updateHand(){
        hand.setText(player.getHand().toString());
        if(player.isTurn()){cm.setVisible(true);}
    }
    public Player getPlayer(){return player;}

    public Command_Palette getCommand_Palette(){return cm;}
}