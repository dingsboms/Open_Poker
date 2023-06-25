package View;

import java.util.ArrayList;

public class Coordinates{
    Coordinate top, bot, left, right, tl, tr, bl, br; 
    ArrayList<Coordinate> all_cords, active_cords, location;
    int x_l, x_r, x_c, y_t, y_b, y_c;

    // For Player_View
    public Coordinates(Table_View view, int player_view_size){
        location = new ArrayList<>();
        x_l = view.getX() - player_view_size;
        x_c = view.getX() + view.getWidth()/2 - player_view_size / 2;
        x_r = view.getX() + view.getWidth();
        y_t = view.getY() - player_view_size;
        y_c = view.getY() + view.getHeight()/2 - player_view_size / 2;
        y_b = view.getY() + view.getHeight();
        location.add(top = new Coordinate(x_c, y_t));
        location.add(right = new Coordinate(x_r, y_c));
        location.add(bot = new Coordinate(x_c, y_b));
        location.add(left = new Coordinate(x_l, y_c));
    }

    public Coordinate getPlayerViewCord(int i){
        return location.get(i);
    }

    // For Playing_Field
    public Coordinates(int width, int height, int num_of_players){
        x_l = 40;
        y_t = 40;
        y_b = height - 40;
        y_c = height/2 - 8;
        x_r = width - 40;
        x_c = (width)/2;

        all_cords = new ArrayList<>();
        active_cords = new ArrayList<>();

        all_cords.add(top = new Coordinate(x_c, y_t));
        all_cords.add(tr = new Coordinate(x_r - 2 * x_l, y_t + 2 * x_l));
        all_cords.add(right = new Coordinate(x_r, y_c));
        all_cords.add(br = new Coordinate(x_r - 2 * x_l, y_b - 2 * x_l));
        all_cords.add(bot = new Coordinate(x_c, y_b));
        all_cords.add(bl = new Coordinate(x_l + 2 * x_l, y_b - 2 * x_l));
        all_cords.add(left = new Coordinate(x_l, y_c));
        all_cords.add(tl = new Coordinate(3 * x_l, y_t + 2 * x_l));
        
        if(num_of_players == 2){active_cords.add(top); active_cords.add(bot);}
        if(num_of_players == 3){active_cords.add(top); active_cords.add(right); active_cords.add(bot);}
        if(num_of_players == 4){active_cords.add(top); active_cords.add(right); active_cords.add(bot); active_cords.add(left);}
        if(num_of_players == 5){active_cords.add(top); active_cords.add(tr); active_cords.add(right); active_cords.add(bot); active_cords.add(left);}
        if(num_of_players == 6){active_cords.add(top); active_cords.add(tr); active_cords.add(right); active_cords.add(br); active_cords.add(bot); active_cords.add(left);}
        if(num_of_players == 7){active_cords.add(top); active_cords.add(tr); active_cords.add(right); active_cords.add(br); active_cords.add(bot); active_cords.add(bl); active_cords.add(left);}
        if(num_of_players == 8){active_cords = all_cords;}
    }
    // For Playing_Field
    public Coordinate getPlayingFieldCord(int i){
        return active_cords.get(i);
    }
}