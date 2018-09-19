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
    
    public void printAllCards() {
        System.out.println();
        System.out.print("Dealer's cards are: ");
        for (int i=0; i<getCardNumber();i++) {
            System.out.print(getCard(i).getCardString()+ " ");
        }
        System.out.println("\n");
    }
    
    public boolean dealerHit() {
        boolean hit = false;
        int score = getScore(true); 
        if(score < 17) {
            return true;
        }
        else if (score > 1000 && score < 2000) {
            score-=1000;
            if(score+11 <= 17) {
                return true;
            }
            if(getCardNumber()>2 && (score+1) < 17) {
                return true;
            }
        }
        else if(score > 2000 && score < 3000) {
            score-=2000;
            if(getCardNumber() == 2 && score+12 <=17) {
                return true;
            }
            if(getCardNumber() > 2 && score+2 < 17) {
                return true;
            }
        }
        return hit;
    }
    
    public boolean dealerSplitHit() {
        boolean hit = false;
        int score = getSplitScore(true); 
        if(score < 17) {
            hit = true;
        }
        else if (score > 1000 && score < 2000) {
            score-=1000;
            if(score+11<=17) {
                hit = true;
            }
        }
        else if(score > 2000 && score < 3000) {
            score-=2000;
            if(score+12 <=17) {
                hit = true;
            }
            else if(score+2<=17) {
                hit = true;
            }
        }
        return hit;
    }

    public void printSplittedCards() {
        if (splittedCardOnHand.isEmpty()) {
            System.out.println();
        }
        else {
            System.out.println();
            System.out.print("Dealer' Splitted cards are: ");
            for (int i=0; i<splittedCardOnHand.size();i++) {
                System.out.print(splittedCardOnHand.get(i).getCardString()+ " ");
            }
            System.out.println("\n");
        }
    }
    
}