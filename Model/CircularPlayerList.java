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
        while(!currentNode.player.isDealer()){
            currentNode = currentNode.nextNode;
        }
        Player last_dealer = currentNode.player;
        last_dealer.resetAll();
        Node new_dealer_node = currentNode.nextNode;
        Player new_dealer = new_dealer_node.player;
        new_dealer.resetAll();
        new_dealer.setDealer();
        // If theres only 2 players the dealer will also be small blind
        if(new_dealer_node.nextNode.nextNode == new_dealer_node){
            new_dealer.setSmallBlind();
            new_dealer_node.nextNode.player.setBigBlind();
        }
        else{
            Player newSmallBlind = new_dealer_node.nextNode.player;
            newSmallBlind.resetAll();
            newSmallBlind.setSmallBlind();
            Player newBigBlind = new_dealer_node.nextNode.nextNode.player;
            newBigBlind.resetAll();
            newBigBlind.setBigBlind();
        }
    }

    public void nextTurn(){
        Node currentNode = head;
        while(!currentNode.player.isTurn()){
            currentNode = currentNode.nextNode;
        }
        currentNode.player.resetIsTurn();
        currentNode.nextNode.player.setIsTurn();
    }
}
    



// class LinkedListIterator implements Iterator<Player>{

//     @Override
//     public boolean hasNext() {
//         return true;
//     }
//     @Override
//     public Player next() {
//         return nextNode.player;
//     }
//     public Iterator<Player> iterator(){
//         return new LinkedListIterator();
//     }
// }