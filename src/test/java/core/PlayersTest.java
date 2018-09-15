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
        //Dealer not hit when score is 18, but one of the card is Ace
        dealer.cardOnHand.clear();
        Card card7 = new Card("C", "A");
        Card card8 = new Card("S", "7");
        dealer.addCard(card7);
        dealer.addCard(card8);
        assertEquals(false, dealer.dealerHit());
        //Dealer receive a Ace and a 5, hit get another Ace, 
        //should be able to hit again
        dealer.cardOnHand.clear();
        Card card9 = new Card("C", "A");
        Card card10 = new Card("S", "5");
        Card card11 = new Card("D", "A");
        dealer.addCard(card9);
        dealer.addCard(card10);
        dealer.addCard(card11);
        assertEquals(true, dealer.dealerHit());
    }

    public void testGetScoreAndAceHandler() throws Exception{
        Player player = new Player();
        //Player's score is 1009 when initial 2 cards 
        //received for BlackJack check
        player.cardOnHand.clear();
        Card card1 = new Card("D", "9");
        Card card2 = new Card("H", "A");
        player.addCard(card1);
        player.addCard(card2);
        assertEquals(1009, player.getScore(true));
        //Player's score is 17 which A and 6 
        //without BlackJack Checking 
        Card card3 = new Card("D","A");
        Card card4 = new Card("S", "6");
        player.addCard(card3);
        player.addCard(card4);
        assertEquals(17, player.getScore(false));
        //Player's score is 21, Ace count as 1
        player.cardOnHand.clear();
        Card card5 = new Card("S","A");
        Card card6 = new Card("H", "Q");
        Card card7 = new Card("C", "K");
        player.addCard(card5);
        player.addCard(card6);
        player.addCard(card7);
        assertEquals(21, player.getScore(false));
        //Player's score is 20, with ACE count as 11
        player.cardOnHand.clear();
        Card card8= new Card("D","A");
        Card card9 = new Card("H", "2");
        Card card10 = new Card("C", "7");
        player.addCard(card8);
        player.addCard(card9);
        player.addCard(card10);
        assertEquals(20, player.getScore(false));
        //Player's score is 20, with ACE count as 11 and 1
        player.cardOnHand.clear();
        Card card11= new Card("D","A");
        Card card12 = new Card("H", "7");
        Card card13 = new Card("S", "A");
        player.addCard(card11);
        player.addCard(card12);
        player.addCard(card13);
        assertEquals(19, player.getScore(false));
        //Verify both Aces can count as 1
        player.cardOnHand.clear();
        Card card14 = new Card("D","A");
        Card card15 = new Card("H", "A");
        Card card16 = new Card("C", "K");
        player.addCard(card14);
        player.addCard(card15);
        player.addCard(card16);
        assertEquals(12, player.getScore(false));
        //Verify three Aces, two of them can count as 1
        player.cardOnHand.clear();
        Card card17 = new Card("D","A");
        Card card18 = new Card("H", "A");
        Card card19 = new Card("C", "5");
        Card card20 = new Card("S", "A");
        player.addCard(card17);
        player.addCard(card18);
        player.addCard(card19);
        player.addCard(card20);
        assertEquals(18, player.getScore(false));
        //Verify three Aces, all of them can count as 1
        player.cardOnHand.clear();
        Card card30 = new Card("D","A");
        Card card31 = new Card("H", "A");
        Card card32 = new Card("C", "Q");
        Card card33 = new Card("S", "A");
        player.addCard(card30);
        player.addCard(card31);
        player.addCard(card32);
        player.addCard(card33);
        assertEquals(13, player.getScore(false));
        //Verify 4 Aces on hand, 
        //3 of them can be count as 1
        player.cardOnHand.clear();
        Card card21 = new Card("D","A");
        Card card22 = new Card("H", "A");
        Card card23 = new Card("C", "A");
        Card card24 = new Card("S", "A");
        player.addCard(card21);
        player.addCard(card22);
        player.addCard(card23);
        player.addCard(card24);
        assertEquals(14, player.getScore(false));
        //Verify 4 Aces on hand,
        //All of them can be count as 1
        player.cardOnHand.clear();
        Card card25 = new Card("D","A");
        Card card26 = new Card("H", "A");
        Card card27 = new Card("C", "A");
        Card card28 = new Card("S", "A");
        Card card29 = new Card("C", "8");
        player.addCard(card25);
        player.addCard(card26);
        player.addCard(card27);
        player.addCard(card28);
        player.addCard(card29);
        assertEquals(12, player.getScore(false));
    }
}