package View;

import java.awt.TextArea;
import java.awt.TextField;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Setup_View implements ChangeListener{
    TextField text_field;
    int num_of_players;
    ArrayList<String> player_names;
    TextArea player_name_display;
    JSlider slider;



    public Setup_View(){
        player_names = new ArrayList<>();
        slider = getSlider();
        setNumOfPlayersPane(slider);
        setPlayerNames(num_of_players);
    }

    public void setPlayerNames(int num_of_players){
        for(int player_num = 1; player_num <= num_of_players; player_num++){
            try{
                String name = JOptionPane.showInputDialog("What's player " + player_num +  "'s name?");
                if(name == null || (name != null && ("".equals(name)))){throw new Exception();}
                player_names.add(name);
            }catch(Exception e){System.out.println("Invalid name");}
            
        }
    }

    public void setNumOfPlayersPane(JSlider slider){
        JOptionPane optionPane = new JOptionPane();
        optionPane.setMessage(new Object[]{"How many players?", slider});
        optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
        optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = optionPane.createDialog(optionPane, "Poker Setup");
        dialog.setVisible(true);
        try{int a = Integer.parseInt(""+optionPane.getValue());
        if(a == 2){System.exit(0);}}
        catch(Exception e){System.out.println("exited"); System.exit(0);}
    }

    public JSlider getSlider(){
        JSlider slider = new JSlider(2, 8, 2);
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(1);
        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(1);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.addChangeListener(this);
        num_of_players = slider.getValue();
        return slider;
    }

    public void nameOfPlayers(){
        text_field.setVisible(true);
        player_name_display.setVisible(true);
        text_field.setMaximumSize(text_field.getPreferredSize(20));
        text_field.setText("");
    }

    public ArrayList<String> getPlayerNames(){
        return player_names;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        num_of_players = slider.getValue();
        }
}
