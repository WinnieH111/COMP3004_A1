package core;

import junit.framework.TestCase;

//REMEBER to change all the file place holder to test file path
public class DeckTest extends TestCase{

    //Get the number of card in Deck when new deck built
    public void testCardNumber() {
        Deck deck = new Deck();
        assertEquals(52, deck.getNumberOfCardOnDeck());
    }
    
    public void testCardShuffled() throws Exception {
        Deck deck = new Deck();
        assertEquals(false, deck.getCard().getCardString().equals("CA"));
    }
}
