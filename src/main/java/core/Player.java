package core;

public class Player extends Players {
  
    public void printCards() {
        System.out.print("Player's cards are: ");
        for (int i=0; i<getCardNumber();i++) {
            System.out.print(getCard(i).getCardString()+ " ");
        }
        System.out.println("\n");
    }
}