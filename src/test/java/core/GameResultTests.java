package core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GameResultTests{

    final String filePath = "src/test/resources/";
 
    //Four cards input is tested here

    @Test
    public void testBlackJack() throws Exception {
        //Dealer and player blackjack, dealer win
        BlackJackGame game = new BlackJackGame();
        assertEquals(1, game.FileInputPlay(filePath+"blackJackBothTest.txt"));
        //Dealer Blackjack and win
        assertEquals(1, game.FileInputPlay(filePath+"/blackJackDealerWinTest.txt"));
        //Player blackjack and win
        game.FileInputPlay(filePath+"/blackJackPlayerWinTest.txt");
        assertEquals(2, game.FileInputPlay(filePath+"/blackJackPlayerWinTest.txt"));
    }
    
    @Test
    public void testBurst() throws Exception {
        BlackJackGame game = new BlackJackGame();
        //Player hit once and burst, dealer win
        assertEquals(1, game.FileInputPlay(filePath+"playerBurstTest.txt"));
        //Player hit twice and burst, dealer win
        assertEquals(1, game.FileInputPlay(filePath+"playerBurstTwiceTest.txt"));
        //Player stand, dealer hit and burst, player win 
        assertEquals(2, game.FileInputPlay(filePath+"dealerBurstTest.txt"));
    }
   
    @Test
    public void testWin() throws Exception {
        BlackJackGame game = new BlackJackGame();
        //Player stand, Dealer stand, Player Win
        assertEquals(2, game.FileInputPlay(filePath+"playerHigherTest.txt"));
        //Player stand, Dealer stand, Dealer Win
        assertEquals(1, game.FileInputPlay(filePath+"dealerHigherTest.txt"));
        //Player Hit once, Dealer stand, Player Win
        assertEquals(2, game.FileInputPlay(filePath+"playerHitHigherTest.txt"));
        //Player Hit once, Dealer stand, Dealer win
        assertEquals(1, game.FileInputPlay(filePath+"playerHitDealerHigherTest.txt"));
        //Player hit once, Dealer hit, Player win
        assertEquals(2, game.FileInputPlay(filePath+"playerHitDealerHitPlayerHigherTest.txt"));
        //Player hit once, Dealer hit, Dealer win
        assertEquals(1, game.FileInputPlay(filePath+"bothHitDealerHigherTest.txt"));
        //Push
        assertEquals(0, game.FileInputPlay(filePath+"pushTest.txt"));
    }
    
    
    public void testSplit() throws Exception {
        BlackJackGame game = new BlackJackGame();
        //player enter "D" as action, but has two different card, does not allow to split (Print error)
        game.FileInputPlay(filePath + "/playNotLegitSplit");
        assertEquals(, outContent.toString().contains("Player can not split"));
        //Player enter "D" as action, has two same card on hand, can do the split
        //Dealer has two same card on hand, can do split automatically
    }


 
}
