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
    
    @Test
    public void testSplit() throws Exception {
        BlackJackGame game = new BlackJackGame();
        //Player enter "D" as action, has two same card on hand, can do the split, and one of the hands burst
        assertEquals(2, game.FileInputPlay(filePath+"playerSplitWin.txt"));
        //Dealer has two same card on hand, can do split automatically
        assertEquals(2, game.FileInputPlay(filePath+"dealerSplit.txt"));
        //Player split and burst, dealer win 
        assertEquals(1, game.FileInputPlay(filePath+"playerSplitloss.txt"));
        //Dealer Split and burst, player win
        assertEquals(2, game.FileInputPlay(filePath+"dealerSplitLoss.txt"));
        //Player hit twice on one hand, hit once on another, dealer has initial score 20. 
        //choose the best hand of player to bit dealer's hand, dealer wins
        assertEquals(1, game.FileInputPlay(filePath+"playerSplitHitDealerWin.txt"));
        //Both Dealer and Player hit, the best of hands chose and player wins
        assertEquals(2, game.FileInputPlay(filePath+"playerSplitHitPlayerWin.txt"));
    }
}
