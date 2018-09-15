package core;

import junit.framework.TestCase;

public class PlayersTest extends TestCase{
//Dealer Hit when the dealer has 16 scores or soft 17 scores on hand 
    public void testDealerHit() throws Exception {
        Dealer dealer = new Dealer();
        //Dealer hit when score is 16
        Card card1 = new Card("D","K");
        Card card2 = new Card("H", "6");
        dealer.addCard(card1);
        dealer.addCard(card2);
        assertEquals(true, dealer.dealerHit());
        //Dealer not hit when score is over 16
        dealer.cardOnHand.clear();
        Card card3 = new Card("C", "Q");
        Card card4 = new Card("S", "7");
        dealer.addCard(card3);
        dealer.addCard(card4);
        assertEquals(false, dealer.dealerHit());
        //Dealer hit when score is 17, but one of the card is Ace
        dealer.cardOnHand.clear();
        Card card5 = new Card("C", "A");
        Card card6 = new Card("S", "6");
        dealer.addCard(card5);
        dealer.addCard(card6);
        assertEquals(true, dealer.dealerHit());
    }
    
}