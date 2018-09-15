package core;

import java.util.ArrayList;
import java.util.List;

public class Players {
    
    protected List<Card> cardOnHand;
    protected List<String> action;
    protected List<Card> splittedCardOnHand;
    
    Players(){
        cardOnHand = new ArrayList<Card>();
        action = new ArrayList<String>();
        } 
    
    public void addCard(Card card) {
        if(card.isValidCard()) {
            cardOnHand.add(card);
        }
        else {
            throw new IllegalArgumentException("The new card can not be added,"
                    + " because it is an invalid card");
        }
    }
    
    public Card getCard(int index) {
        return cardOnHand.get(index);
    }
    
    protected int getCardNumber() {
        return cardOnHand.size();
    }
    
    public void addAction(String newAction) {
        action.add(newAction);
    }
    
    public int getScore(boolean bj) {
        int score = 0;
        for(Card card:cardOnHand) {
            score+=card.getValue();
            }
        if(score > 1000 && !bj) {
            score = aceHandler(score);
        }
        return score;
    }

    private int aceHandler(int score) {
        int adjustedScore = 0;
        if(score < 2000) {
            score-=1000;
            if(score + 11 <= 21) {
                adjustedScore = score + 11;
            }
            else {
                adjustedScore = score + 1;
            }
        }
        if(score >= 2000 && score < 3000) {
            score-=2000;
            //deal with 2 ACEs, one and anothers
            if(score+12 <= 21) {
                adjustedScore = score + 12;
            }
            else {
                adjustedScore = score + 2;
            }
        }
        if(score >= 3000 && score < 4000) {
            score -= 3000;
            if(score+13 <= 21) {
                adjustedScore = score + 13;
            }
            else {
                adjustedScore = score + 3;
            }
        }
        //If player has 4 Aces on hand, very very rare case
        if(score >= 4000) {
            score -=4000;
            if(score+14 <= 21) {
                adjustedScore = score +14;
            }
            else {
                adjustedScore = score + 4;
            }
        }
        return adjustedScore;
    }

}