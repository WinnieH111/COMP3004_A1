package core;

import java.util.Random;

public class Dealer extends Players {

    public void printCards() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(getCardNumber() - 1) + 0;
        System.out.print("One of the Dealer's card is ");
        System.out.println(getCard(randomIndex).getCardString()+ " ");
        System.out.print("\n");
    }
    
}