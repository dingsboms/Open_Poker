package View;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Playing_Field extends JPanel{
    JPanel playing_field, top, right, bottom, left, center, card_panel;
    JPanel[] panel = {top, right, bottom, left};
    String[] layouts = {BorderLayout.NORTH, BorderLayout.EAST, BorderLayout.SOUTH, BorderLayout.WEST};
    JLabel player_1, player_2, player_3, player_4, pot, table, announcement;
    String[] player_label_string = {"Player 1", "Player 2", "Player 3", "Player 4"};
    JLabel[] player_label = {player_1, player_2, player_3, player_4};
    JLabel chips_1, chips_2, chips_3, chips_4;
    JLabel[] player_chips = {chips_1, chips_2, chips_3, chips_4};
    
    public Playing_Field(){
        setLayout(new BorderLayout());
        GridBagConstraints c;
        // Make Players
        for(int i = 0; i < panel.length; i++){
            JPanel current_panel = panel[i];
            c = new GridBagConstraints();

            current_panel = new JPanel(new GridBagLayout());
            current_panel.add(player_label[i] = new JLabel(player_label_string[i], JLabel.CENTER), c);
            c.gridy = 1;
            current_panel.add(player_chips[i] = new JLabel("Chips: ", JLabel.CENTER), c);
            add(current_panel, layouts[i]);
            }
        // Make Table
        c = new GridBagConstraints();
        center = new CirclePanel();
        center.setLayout(new GridBagLayout());
        card_panel = new JPanel();
        c.gridy = 1;
        center.add(card_panel, c);
        announcement = new JLabel("", JLabel.CENTER);
        pot = new JLabel("Pot: 0", JLabel.CENTER);
        c.gridy = 0;
        c.gridx = 0;
        center.add(announcement, c);
        c.gridy = 2;
        center.add(pot, c);
        add(center);
    }
    public class CirclePanel extends JPanel{
        @Override
        protected void paintComponent(Graphics g) {
            g.drawOval(0, 0, g.getClipBounds().width, g.getClipBounds().height);
        }
    }
}
