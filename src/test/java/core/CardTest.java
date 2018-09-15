package core;

import junit.framework.TestCase;

public class CardTest extends TestCase{

    public void testCardValue10() {
        Card card1 = new Card("C", "J");
        Card card2 = new Card("C", "Q");
        Card card3 = new Card("C", "K");
        Card card4 = new Card("D", "J");
        Card card5 = new Card("D", "Q");
        Card card6 = new Card("D", "K");
        Card card7 = new Card("H", "J");
        Card card8 = new Card("H", "Q");
        Card card9 = new Card("H", "K");
        Card card10 = new Card("S", "J");
        Card card11 = new Card("S", "Q");
        Card card12 = new Card("S", "K");
        assertEquals(10, card1.getValue());
        assertEquals(10, card2.getValue());
        assertEquals(10, card3.getValue());
        assertEquals(10, card4.getValue());
        assertEquals(10, card5.getValue());
        assertEquals(10, card6.getValue());
        assertEquals(10, card7.getValue());
        assertEquals(10, card8.getValue());
        assertEquals(10, card9.getValue());
        assertEquals(10, card10.getValue());
        assertEquals(10, card11.getValue());
        assertEquals(10, card12.getValue());
    }
    
    public void testCardValueAce() {
        Card card1 = new Card("C", "A");
        Card card2 = new Card("D", "A");
        Card card3 = new Card("H", "A");
        Card card4 = new Card("S", "A");
        assertEquals(1000, card1.getValue());
        assertEquals(1000, card2.getValue());
        assertEquals(1000, card3.getValue());
        assertEquals(1000, card4.getValue());
    }
    
    public void testCardValudOther() {
        Card card1 = new Card("C", "2");
        Card card2 = new Card("D", "5");
        Card card3 = new Card("H", "7");
        Card card4 = new Card("S", "6");
        Card card5 = new Card("C", "10");
        Card card6 = new Card("D", "3");
        Card card7 = new Card("H", "4");
        Card card8 = new Card("S", "9");
        assertEquals(2,  card1.getValue());
        assertEquals(5,  card2.getValue());
        assertEquals(7,  card3.getValue());
        assertEquals(6,  card4.getValue());
        assertEquals(10, card5.getValue());
        assertEquals(3,  card6.getValue());
        assertEquals(4,  card7.getValue());
        assertEquals(9,  card8.getValue());
    }
    
    public void testInvalidCard() {
        Card card1 = new Card("d", "11");
        Card card2 = new Card("k", "P");
        Card card3 = new Card("O", "q");
        Card card4 = new Card("J", "Q");
        Card card5 = new Card("D", "J");
        Card card6 = new Card("10", "K");
        assertEquals(false, card1.isValidCard());
        assertEquals(false, card2.isValidCard());
        assertEquals(false, card3.isValidCard());
        assertEquals(false, card4.isValidCard());
        assertEquals(true,  card5.isValidCard());
        assertEquals(false, card6.isValidCard());

    }
}