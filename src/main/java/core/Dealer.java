package core;

import java.util.ArrayList;
import java.util.Random;

public class Dealer extends Players {

    public void printCards() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(getCardNumber() - 1) + 0;
        System.out.print("One of the Dealer's card is ");
        System.out.println(getCard(randomIndex).getCardString()+ " ");
        System.out.print("\n");
    }
    
    public boolean dealerHit() {
        boolean hit = false;
        int score = getScore(true); 
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
        }
        return hit;
    }
}