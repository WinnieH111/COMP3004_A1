package core;

public class Player extends Players {
  
    public void printCards() {
        System.out.println();
        System.out.print("Player's cards are: ");
        for (int i=0; i<getCardNumber();i++) {
            System.out.print(getCard(i).getCardString()+ " ");
        }
        System.out.println("\n");
    }

    public void printSplittedCards() {
        if (splittedCardOnHand.isEmpty()) {
            System.out.println();
        }
        else {
            System.out.println();
            System.out.print("Player's Splitted cards are: ");
            for (int i=0; i<splittedCardOnHand.size();i++) {
                System.out.print(splittedCardOnHand.get(i).getCardString()+ " ");
            }
            System.out.println("\n");
        }
    }
}