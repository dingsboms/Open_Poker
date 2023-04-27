package Model;

public class CircularPlayerList{
    private Node head = null;
    private Node tail = null;

    protected class Node{
        Player player;
        Node nextNode;

        public Node(Player player){
            this.player = player;
        }
    }


    public void addNode(Player player_to_be_added){
        Node newNode = new Node(player_to_be_added);
        if(head == null){head = newNode;}
        else{tail.nextNode = newNode;}
        tail = newNode;
        tail.nextNode = head;
    }

    public void deleteNode(Player player_to_be_deleted){
        if(head == null){return;}
        Node currentNode = head;
        do {
            Node nextNode = currentNode.nextNode;
            if(nextNode.player == player_to_be_deleted){
                if (tail == head) {
                    head = null; tail = null;
                } else{
                    currentNode.nextNode = nextNode.nextNode;
                    if(head == nextNode){head = head.nextNode;}
                    if (tail == nextNode){tail = currentNode;}
                }
                break;
            }
            currentNode = nextNode;
        } while(currentNode != head);
    }

    public void moveDealerAndBlinds(){
        Node currentNode = head;
        while(!currentNode.player.isSmallBlind()){
            currentNode = currentNode.nextNode;
        }
        Player last_small_blind = currentNode.player;
        last_small_blind.resetAll();
        Node new_sb_node = currentNode.nextNode;
        Player new_sb = new_sb_node.player;
        new_sb.resetAll();
        new_sb.setSmallBlind();
        // If theres only 2 players the dealer will also be small blind
        if(new_sb_node.nextNode.nextNode == new_sb_node){
            new_sb.setDealer();
            new_sb_node.nextNode.player.setBigBlind();
        }
        else{
            Player new_bb = new_sb_node.nextNode.player;
            new_bb.resetAll();
            new_bb.setBigBlind();
            Player newDealer = new_sb_node.nextNode.nextNode.player;
            newDealer.resetAll();
            newDealer.setDealer();
        }
    }

    public Node resetCurrentTurnNode(){
        Node currentNode = head;
        // Finds the player whos turn it is currently
        while(!currentNode.player.isTurn()){
            currentNode = currentNode.nextNode;
        }
        Player old_turn_player = currentNode.player;
        old_turn_player.resetIsTurn();
        return currentNode;
    }

    public void nextPersonsTurn(){
        Node currentNode = resetCurrentTurnNode();
        while(currentNode.nextNode.player.hasFolded()){
            currentNode = currentNode.nextNode;
        }
        Player new_turn_player = currentNode.nextNode.player;
        new_turn_player.setIsTurn();
    }

    public void nextPersonAfterDealersTurn(){
        resetCurrentTurnNode();
        Node currentNode = head;
        // Finds dealer
        while(!currentNode.player.isDealer()){
            currentNode = currentNode.nextNode;
        }
        // Sets dealer to next person, if person has folded, goes to next
        currentNode = currentNode.nextNode;
        while(currentNode.player.hasFolded()){
            currentNode = currentNode.nextNode;
        }
        currentNode.player.setIsTurn();
    }
}