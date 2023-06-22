package View;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import Model.Player;

public class Playing_Field extends JPanel{
    ArrayList<Player> players;
    int num_of_players, st_le;

    String card_panel, pot, announcement;
    Coordinates cords;


    public Playing_Field(ArrayList<Player> players, int playing_field_size){
        this.players = players;
        setSize(playing_field_size, playing_field_size);
        num_of_players = players.size();
        cords = new Coordinates(this.getWidth(), players.size());

        pot = "0";
        card_panel = "";
        announcement = "";
        }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int cbl = 60;
        int width = g2d.getClipBounds().width;
        int height = g2d.getClipBounds().height;
        FontMetrics metrics = g2d.getFontMetrics();

        // Draws table
        g2d.drawOval(cbl, cbl, width-cbl*2, height-cbl*2);
        st_le = metrics.stringWidth(pot);
        g2d.drawString(pot, (width-st_le)/2, height/2 + 15);
        st_le = metrics.stringWidth(card_panel);
        g2d.drawString(card_panel, (width-st_le)/2, height/2 - 20);
        st_le = metrics.stringWidth(announcement);
        g2d.drawString(announcement, (width-st_le)/2, height/2 + 40);


        for(int i = 0; i < num_of_players; i++){
            Player p = players.get(i);
            st_le = metrics.stringWidth(p.getName());
            Coordinate c = cords.getPlayingFieldCord(i);

            if(p.isTurn()){g2d.setColor(Color.RED);}
            g2d.drawString(p.getName() + " " + p.getStatus(), c.getX(), c.getY());
            g2d.setColor(Color.BLACK);
            g2d.drawString("" + p.getChips(), c.getX(), c.getY() + 15);
        }
    }

    public void refresh(){
        repaint();
    }
}
