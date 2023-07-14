package com.example.spring_poker.api.Model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class Poker_Hand implements Comparable<Poker_Hand>{
    Player player;
    Table table;
    ArrayList<Card> table_cards;
    ArrayList<Card> player_cards;
    ArrayList<Card> available_cards = new ArrayList<>();
    ArrayList<Card> best_hand = new ArrayList<>();
    ArrayList<Card> rest_high_cards = new ArrayList<>();
    TreeMap<Integer, Integer> occurances = new TreeMap<Integer, Integer>(Collections.reverseOrder());
    String type_of_hand;
    int score;
    
    boolean is_pair = false;
    boolean is_two_pairs = false;
    boolean is_three_of_a_kind = false;
    boolean is_straight = false;
    boolean is_flush = false;
    boolean is_full_house = false;
    boolean is_four_of_a_kind = false;
    boolean is_straight_flush = false;

    public Poker_Hand(Player player, Table table){
        this.player = player;
        this.table = table;
        this.table_cards = table.getTableCards();
        player_cards = player.getHand();
        available_cards.addAll(table_cards);
        available_cards.addAll(player_cards);
        // Substitute with .sort() method for arraylist
        available_cards = getSortedCards(available_cards);
        setBestHand();
    }

    public Player getPlayer(){return player;}

    // Delete
    public ArrayList<Card> getSortedCards(ArrayList<Card> unsorted_cards){
        ArrayList<Integer> sorted_numbers = new ArrayList<Integer>();
        ArrayList<Card> sorted_cards = new ArrayList<Card>();
        for(Card card:unsorted_cards){sorted_numbers.add(card.getNum());} 
        Collections.sort(sorted_numbers);
        
        for(int num:sorted_numbers){
            for(Card card:unsorted_cards){
                if(card.getNum() == num && !sorted_cards.contains(card)){
                    sorted_cards.add(card);}}}
        return sorted_cards;}

    public void setAvailableCards(ArrayList<Card> cards){
        available_cards = cards;}

    public ArrayList<Card> getAvailableCards(){
        return available_cards;}


    public String getTypeofHand(){
        return type_of_hand;}

    public int getScore(){
        return score;}

    public ArrayList<Card> getBestHand(){
        return best_hand;
    }
    public void setBestHand(){
        countOccurances();
        check_flush();
        check_straight();
        check_of_a_kind();
        if(is_straight_flush){best_hand = check_straight(); type_of_hand = "Straight Flush"; score = 8;}
        else if(is_four_of_a_kind){ System.out.println("Whatdafaq");;best_hand = check_of_a_kind();setRestHighCards(); type_of_hand = "Four Of A Kind"; score = 7;}
        else if(is_full_house){best_hand = check_of_a_kind(); type_of_hand = "Full House"; score = 6;}
        else if(is_flush){best_hand = check_flush(); type_of_hand = "Flush"; score = 5;}
        else if(is_straight){best_hand = check_straight(); type_of_hand = "Straight"; score = 4;}
        else if(is_three_of_a_kind){best_hand = check_of_a_kind();setRestHighCards();type_of_hand = "Three Of A Kind";score = 3;}
        else if(is_two_pairs){best_hand = check_of_a_kind();setRestHighCards();type_of_hand = "Two Pair"; score = 2;}
        else if(is_pair){best_hand = check_of_a_kind(); setRestHighCards();type_of_hand = "A Pair"; score = 1;}
        else{setRestHighCards(); type_of_hand = "High Card"; score = 0;}

    }


    public ArrayList<Card> removeDuplicates(ArrayList<Card> cards_to_be_checked){
        ArrayList<Card> no_duplicates = new ArrayList<>();
        for(Card card: cards_to_be_checked){
            if(!no_duplicates.contains(card)){
                no_duplicates.add(card);}}
        return no_duplicates;}

    public String getDominantType(ArrayList<Card> straight){
        String dominant_type = "N";
        class Char_int{
            private String c;
            private int i;

            public Char_int(String c){
                this.c = c;
                this.i = 0;
            }
            void int_up(){i++;}
            int getI(){return i;}
            String getChar(){return c;}
        }
        Char_int[] num_of_chars ={new Char_int("❤️"), new Char_int("♦️"), new Char_int("♣️"), new Char_int("♠️")};
        for(Card card:straight){
            if(card.getType() == "❤️"){num_of_chars[0].int_up();}
            else if(card.getType() == "♦️"){num_of_chars[1].int_up();}
            else if(card.getType() == "♣️"){num_of_chars[2].int_up();}
            else if(card.getType() == "♠️"){num_of_chars[3].int_up();}
        }
        int highest_value = 0;
        for(Char_int char_type:num_of_chars){
            if(char_type.getI() > highest_value){highest_value = char_type.getI(); dominant_type = char_type.getChar();}
        }
        return dominant_type;
    }   
    public void setCardOccurance(Card card, int occurance){
        for(Map.Entry<Integer, Integer> set: occurances.entrySet()){
            if(set.getKey() == card.getNum()){
                occurances.put(set.getKey(), occurance);
            }
        }
    }
    public ArrayList<Card> getCardsOfOccurance(int occurance){
        ArrayList<Card> cards_of_occurance = new ArrayList<>();
        for(Map.Entry<Integer, Integer> set : occurances.entrySet()){
            if(set.getValue() == occurance){
                for(Card card:available_cards){
                    if(card.getNum() == set.getKey()){cards_of_occurance.add(card);}}
            }
        }return cards_of_occurance;
    }
    // Methods used for comparing poker hands
    public Card getPairCard(){
        Card pairCard = getCardsOfOccurance(2).get(0);
        return pairCard;
    }
    // In a five card hand, the three of a kind hand will always be in the middle
    public Card getThreeOfaKindCard(){
        return best_hand.get(2);
    }

    public Card getFourOfaKindCard(){
        Card fourKindCard = getCardsOfOccurance(4).get(0);
        return fourKindCard;
    }

    public ArrayList<Card> getCardsOfSameType(String type){
        ArrayList<Card> cards_of_same_type = new ArrayList<>();
        for(Card card:available_cards){
            if(card.getType() == type){cards_of_same_type.add(card);}}
        return cards_of_same_type;}

    // In case of pair, two pairs, three of a kind, four of a kind or high card only, returns the other cards of highest value
    public void setRestHighCards(){
        ArrayList<Card> high_cards = new ArrayList<>();
        int i = 1;
        while(best_hand.size() < 5){
            Card card = available_cards.get(available_cards.size()-i);
            if(!best_hand.contains(card)){high_cards.add(card); best_hand.add(card);}
            i++;
        }rest_high_cards = high_cards;
    }

    public ArrayList<Card> getRestHighCards(){
        return rest_high_cards;
    }
    public TreeMap<Integer, Integer> countOccurances(){
    for(Card card: available_cards){
        if(!occurances.containsKey(card.getNum())){
            occurances.put(card.getNum(), 1);}
        else{occurances.put(card.getNum(), occurances.get(card.getNum())+1);}}

    return occurances;
    }

    public ArrayList<Card> check_of_a_kind(){
        ArrayList<Card> of_a_kind = new ArrayList<>();
        ArrayList<Card> four_of_a_kind_cards = getCardsOfOccurance(4);
        ArrayList<Card> three_of_a_kind_cards = getCardsOfOccurance(3);
        ArrayList<Card> pair_cards = getCardsOfOccurance(2);

        if(!four_of_a_kind_cards.isEmpty()){is_four_of_a_kind = true; return four_of_a_kind_cards;}
        else if(!three_of_a_kind_cards.isEmpty() && !pair_cards.isEmpty()){
            is_full_house = true;
            of_a_kind.addAll(three_of_a_kind_cards);
            of_a_kind.addAll(pair_cards);
        }else if(!three_of_a_kind_cards.isEmpty()){is_three_of_a_kind = true; of_a_kind = three_of_a_kind_cards;}
        else if(!pair_cards.isEmpty()){
            if(pair_cards.size() > 2){is_two_pairs = true; of_a_kind.addAll(pair_cards);}
            else{is_pair = true; return pair_cards;}
        }

        Collections.sort(three_of_a_kind_cards); Collections.sort(pair_cards);
        int i = 0;
        while(of_a_kind.size() > 5){
            if(three_of_a_kind_cards.size() == 6){
                is_full_house = true;
                setCardOccurance(three_of_a_kind_cards.get(0), 2);
                of_a_kind.remove(three_of_a_kind_cards.get(0));}
            else{of_a_kind.remove(pair_cards.get(i));}
            i++;
        }
        return of_a_kind;
    }

    public boolean is_same_number(ArrayList<Card> of_a_kind){
        int same_num = of_a_kind.get(0).getNum();
        boolean is_same_number = false;
        int num_of_same = 0;
        for(Card card: of_a_kind){if(same_num == card.getNum()){num_of_same++;}}
        if(num_of_same == 4){is_same_number = true;}

        return is_same_number;}

    public boolean is_two_pair(ArrayList<Card> of_a_kind){
        int same_num = of_a_kind.get(0).getNum();
        boolean is_two_pair = false;
        int num_of_same = 0;
        for(Card card:of_a_kind){if(same_num == card.getNum()){num_of_same++;}}
        if(num_of_same == 2){is_two_pair = true;}

        return is_two_pair;}

    public boolean is_straight_flush(ArrayList<Card> straight){
        if(straight.isEmpty()){return false;}
        int num_of_type = 0;
        String type = straight.get(0).getType();
        for(Card card:straight){if(type == card.getType()){num_of_type++;}}
        if(num_of_type == 5){is_straight_flush = true;}

        return is_straight_flush;}

    public ArrayList<Card> check_flush(){
        int num_of_hearts = 0;
        int num_of_diamonds = 0;
        int num_of_clubs = 0;
        int num_of_spades = 0;
        ArrayList<Card> flush = new ArrayList<>();

        for(Card card:available_cards){
            String type = card.getType();
            if(type == "❤️"){num_of_hearts++;}
            else if(type == "♦️"){num_of_diamonds++;}
            else if(type == "♣️"){num_of_clubs++;}
            else if(type == "♠️"){num_of_spades++;}
        }
        if(num_of_hearts >= 5){flush = getCardsOfSameType("❤️"); is_flush = true;}
        else if(num_of_diamonds >= 5){flush = getCardsOfSameType("♦️"); is_flush = true;}
        else if(num_of_clubs >= 5){flush = getCardsOfSameType("♣️"); is_flush = true;}
        else if(num_of_spades >= 5){flush = getCardsOfSameType("♠️"); is_flush = true;}
        Collections.reverse(flush);
        int i = 1;
        while(flush.size() > 5){
            flush.remove(flush.size()-i);
            i++;
        }
        return flush;
    }

    public ArrayList<Card> check_straight(){
        ArrayList<Card> straight = new ArrayList<>();
        ArrayList<Card> straight2 = new ArrayList<>();
        ArrayList<Card> straight3 = new ArrayList<>();
        ArrayList<Integer> straight_num = new ArrayList<>();
        ArrayList<Integer> straight_num2 = new ArrayList<>();
        ArrayList<Integer> straight_num3 = new ArrayList<>();
        ArrayList<Integer> possible_straight_nums = new ArrayList<>();
        int num_of_consecutives = 0;

        // Get rids of any duplicate number
        for(Card card:available_cards){
            if(!possible_straight_nums.contains(card.getNum())){possible_straight_nums.add(card.getNum());}
        }
        Collections.sort(possible_straight_nums);

        // Handles ace as one exception
        if(possible_straight_nums.contains(14) &&
            possible_straight_nums.get(0) == 2 &&
            possible_straight_nums.get(1) == 3 &&
            possible_straight_nums.get(2) == 4 &&
            possible_straight_nums.get(3) == 5){
                is_straight = true;
                List<Integer> first_straight = Arrays.asList(14,2,3,4,5);
                straight_num.addAll(first_straight);
            }

        for(int i = 0;i<possible_straight_nums.size();i++){
            try{
                if(possible_straight_nums.get(i+1)-possible_straight_nums.get(i)==1){
                    num_of_consecutives++;

                    if(num_of_consecutives == 4){
                        is_straight = true;
                        for(int y = -3; y < 2; y++){
                            straight_num.add(possible_straight_nums.get(i+y));}
                    }else if(num_of_consecutives == 5){
                        for(int y = -3; y < 2; y++){
                            straight_num2.add(possible_straight_nums.get(i+y));}
                    }else if(num_of_consecutives == 6){
                        for(int y = -3; y < 2; y++){
                            straight_num3.add(possible_straight_nums.get(i+y));}
                    }
                }
                else{num_of_consecutives = 0;}
            }catch(Exception e){}
        }
        if(is_straight){
            straight = getRelevantStraight(straight_num);
            if(!straight_num2.isEmpty()){straight2 = getRelevantStraight(straight_num2);}
            if(!straight_num3.isEmpty()){straight3 = getRelevantStraight(straight_num3);}
        }
        Collections.sort(straight2);
        Collections.sort(straight3);
        if(is_straight_flush(straight3)){return straight3;}
        else if(!straight3.isEmpty() && (!is_straight_flush(straight2) || !is_straight_flush(straight))){return straight3;}
        else if(is_straight_flush(straight2)){return straight2;}
        else if(!straight2.isEmpty() && !is_straight_flush(straight)){return straight2;}
        else if(is_straight_flush(straight)){return straight;}

        else{return straight;}
    }

    public ArrayList<Card> getRelevantStraight(ArrayList<Integer> straight_num){
        ArrayList<Card> relevant_straight = new ArrayList<>();
        String dominant_type = getDominantType(available_cards);
        ArrayList<Card> duplicates = check_of_a_kind();
        for(int n:straight_num){
            for(Card c:available_cards){
                if(c.getNum() == n && !duplicates.contains(c)){
                    relevant_straight.add(c);
                }
            }
        }
        for(Card c: duplicates){
            if(c.getType()==dominant_type){relevant_straight.add(c);}
        }
        int i = 0;
        while(relevant_straight.size() < 5){
            Card card = duplicates.get(i);
            if(!relevant_straight.contains(card)){relevant_straight.add(card);}
            i++;
        }
        while(relevant_straight.size()> 5){
            Collections.sort(relevant_straight);
            relevant_straight.remove(0);
        }
        return relevant_straight;
    }
    public void showBestHand(){
        System.out.println(this.player.getName() + "'s best hand is " + getTypeofHand());
        System.out.println(best_hand);
    }

    @Override
    // Four of a kind and down can be replaced with else-block
    public int compareTo(Poker_Hand other){
        int difference;
        difference = this.score - other.getScore();
        if(difference==0){
            if(this.best_hand == other.best_hand){return 0;}
            else if(this.type_of_hand == "Straight" || this.type_of_hand == "Straight Flush"){
                difference = best_hand.get(4).compareTo(other.getBestHand().get(4));
            }else if(this.type_of_hand == "Four Of A Kind"){
                difference = this.getFourOfaKindCard().compareTo(other.getFourOfaKindCard());
                if(difference == 0){difference = this.best_hand.get(4).compareTo(other.getBestHand().get(4));}
            }else if(this.type_of_hand == "Full House"){
                difference = this.getThreeOfaKindCard().compareTo(other.getThreeOfaKindCard());
                if(difference==0){difference = this.getPairCard().compareTo(other.getPairCard());}
            }else if(this.type_of_hand == "Flush"){
                for(int i = 0; i < best_hand.size(); i++){
                    if(best_hand.get(i).compareTo(other.best_hand.get(i)) > 0){return 1;}
                    else if(best_hand.get(i).compareTo(other.best_hand.get(i)) < 0){return -1;}
                }
            }else if(this.type_of_hand == "Three Of A Kind"){
                difference = this.getThreeOfaKindCard().compareTo(other.getThreeOfaKindCard());
                if(difference == 0){
                    for(int i = 0; i < rest_high_cards.size(); i++){
                        if(this.rest_high_cards.get(i).compareTo(other.getRestHighCards().get(i)) > 0){return 1;}
                        else if(this.rest_high_cards.get(i).compareTo(other.getRestHighCards().get(i)) < 0){return -1;}
                    }
                }
            }else if(this.type_of_hand == "Two Pair" || this.type_of_hand == "Pair"){
                difference = this.getPairCard().compareTo(other.getPairCard());
                if(difference == 0){
                    for(int i = 0;i<this.best_hand.size();i++){
                        if(this.best_hand.get(i).compareTo(other.getBestHand().get(i)) > 0){return 1;}
                        else if(this.best_hand.get(i).compareTo(other.getBestHand().get(i)) < 0){return -1;}
                    }
                }
            }
            else{
                for(int i = 0;i<this.best_hand.size();i++){
                    if(this.best_hand.get(i).compareTo(other.getBestHand().get(i)) > 0){return 1;}
                    else if(this.best_hand.get(i).compareTo(other.getBestHand().get(i)) < 0){return -1;}}
            }
        }return difference;
    }

}