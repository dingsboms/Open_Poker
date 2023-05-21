package View;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Player_View extends JFrame{
    JFrame player_frame;
    JPanel panel;
    JLabel hand;

    public Player_View(){
        super("Player View");
        panel = new JPanel();
        hand = new JLabel();
        add(panel);
        add(hand);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(100, 100);
        setResizable(false);
        setVisible(true);
    }

    public void setHand(String cards){
        hand.setText(cards);
    }
}