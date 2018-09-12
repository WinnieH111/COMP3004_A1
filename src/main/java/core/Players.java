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
        /*
        if(score > 1000 && !bj) {
            score = aceHandler(score);
        }
        */
        return score;
    }

}