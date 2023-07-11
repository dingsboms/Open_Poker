package com.example.spring_poker.api.View;

import java.util.ArrayList;

public class Coordinates{
    Coordinate top, bot, left, right, tl, tr, bl, br;
    int x_l, x_r, x_c, y_t, y_b, y_c;
    ArrayList<Coordinate> all_cords, active_cords, all_location, location;

    public class Coordinate {
    float x;
    float y;
    public Coordinate(float x, float y){
        this.x = x;
        this.y = y;
    }
    public float getX(){return x;}
    public float getY(){return y;}
    
}

    // For Player_View
    public Coordinates(Table_View view, int player_view_size, int num_of_players){

        location = new ArrayList<>();
        all_location = new ArrayList<>();

        x_l = view.getX() - player_view_size;
        x_c = view.getX() + view.getWidth()/2 - player_view_size / 2;
        x_r = view.getX() + view.getWidth();
        y_t = view.getY() - player_view_size;
        y_c = view.getY() + view.getHeight()/2 - player_view_size / 2;
        y_b = view.getY() + view.getHeight();

        all_location.add(top = new Coordinate(x_c, y_t)); 
        all_location.add(tr = new Coordinate(x_r, y_t));
        all_location.add(right = new Coordinate(x_r, y_c));
        all_location.add(br = new Coordinate(x_r, y_b));
        all_location.add(bot = new Coordinate(x_c, y_b));
        all_location.add(bl = new Coordinate(x_l, y_b));
        all_location.add(left = new Coordinate(x_l, y_c));
        all_location.add(tl = new Coordinate(x_l, y_t));

        if(num_of_players == 2){location.add(top); location.add(bot);}
        if(num_of_players == 3){location.add(top); location.add(right) ; location.add(bot);}
        if(num_of_players == 4){location.add(top); location.add(right); location.add(bot); location.add(left);}
        if(num_of_players == 5){location.add(top); location.add(tr); location.add(right); location.add(bot); location.add(left);}
        if(num_of_players == 6){location.add(top); location.add(tr); location.add(right); location.add(br); location.add(bot); location.add(left);}
        if(num_of_players == 7){location.add(top); location.add(tr); location.add(right); location.add(br); location.add(bot); location.add(bl); location.add(left);}
        if(num_of_players == 8){location = all_location;}

    }

    public float getPlayerViewCordX(int i){return location.get(i).getX();}

    public float getPlayerViewCordY(int i){return location.get(i).getY();}

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
        all_cords.add(tr = new Coordinate(x_r - 40 , y_t + 40));
        all_cords.add(right = new Coordinate(x_r, y_c));
        all_cords.add(br = new Coordinate(x_r - 40 , y_b - 40));
        all_cords.add(bot = new Coordinate(x_c, y_b));
        all_cords.add(bl = new Coordinate(x_l + 40, y_b - 40));
        all_cords.add(left = new Coordinate(x_l, y_c));
        all_cords.add(tl = new Coordinate(x_l + 40, y_t + 40));
        
        if(num_of_players == 2){active_cords.add(top); active_cords.add(bot);}
        if(num_of_players == 3){active_cords.add(top); active_cords.add(right); active_cords.add(bot);}
        if(num_of_players == 4){active_cords.add(top); active_cords.add(right); active_cords.add(bot); active_cords.add(left);}
        if(num_of_players == 5){active_cords.add(top); active_cords.add(tr); active_cords.add(right); active_cords.add(bot); active_cords.add(left);}
        if(num_of_players == 6){active_cords.add(top); active_cords.add(tr); active_cords.add(right); active_cords.add(br); active_cords.add(bot); active_cords.add(left);}
        if(num_of_players == 7){active_cords.add(top); active_cords.add(tr); active_cords.add(right); active_cords.add(br); active_cords.add(bot); active_cords.add(bl); active_cords.add(left);}
        if(num_of_players == 8){active_cords = all_cords;}
    }

    public float getPlayingFieldCordX(int i){return active_cords.get(i).getX();}

    public float getPlayingFieldCordY(int i){return active_cords.get(i).getY();}

}