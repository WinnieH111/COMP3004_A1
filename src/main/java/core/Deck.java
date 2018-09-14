package core;

import java.util.ArrayList;
import java.util.Random;

public class Deck {

    private String[] suits = {"C", "S", "H", "D"};
    private String[] ranks = {"A", "2", "3", "4", "5", 
            "6", "7", "8", "9", "10", "J", "Q", "K"};
    
    private ArrayList<Card> deck;
    
    Deck(){
        deck = new ArrayList<Card>();
        for (int i = 0; i<suits.length; i++) {
            for(int j = 0; j<ranks.length; j++) {
                deck.add(new Card(suits[i], ranks[j]));
            }
        }
        shuffleCard();
    }

    private void shuffleCard() {
        Random rand = new Random();
        int deckSize = deck.size();
        int shuffleTime = 10000;
        for(int i=0; i < shuffleTime; i++) {
            int randomIndex = rand.nextInt(deckSize-1) + 0;
            deck.add(deck.remove(randomIndex));
        }
    }
   
    public Card getCard() throws Exception {
        Card card = null;
        if(deck.size()>0) {
            card = deck.remove(0);
        }
        else {
            throw new Exception("No card available on the deck");
        }
       return card;
    }
    
    public Card getCard(int index) throws Exception{
        if(index > 52) {
            throw new Exception ("The index does not exist");
        }
        return deck.get(index);
         
    }
    
    public int getNumberOfCardOnDeck() {
        return deck.size();
    }
}