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
        assertEquals(1, game.fileInputPlay(filePath+"blackJackBothTest.txt"));
        //Dealer Blackjack and win
        assertEquals(1, game.fileInputPlay(filePath+"/blackJackDealerWinTest.txt"));
        //Player blackjack and win
        game.fileInputPlay(filePath+"/blackJackPlayerWinTest.txt");
        assertEquals(2, game.fileInputPlay(filePath+"/blackJackPlayerWinTest.txt"));
    }
    
    @Test
    public void testBust() throws Exception {
        BlackJackGame game = new BlackJackGame();
        //Player hit once and bust, dealer win
        assertEquals(1, game.fileInputPlay(filePath+"playerBustTest.txt"));
        //Player hit twice and bust, dealer win
        assertEquals(1, game.fileInputPlay(filePath+"playerBustTwiceTest.txt"));
        //Player stand, dealer hit and bust, player win 
        assertEquals(2, game.fileInputPlay(filePath+"dealerBustTest.txt"));
        //Player hit once and get 21, dealer bust, player win
        assertEquals(2, game.fileInputPlay(filePath+"player21DealerBust.txt"));
    }
   
    @Test
    public void testWin() throws Exception {
        BlackJackGame game = new BlackJackGame();
        //Player stand, Dealer stand, Player Win
        assertEquals(2, game.fileInputPlay(filePath+"playerHigherTest.txt"));
        //Player stand, Dealer stand, Dealer Win
        assertEquals(1, game.fileInputPlay(filePath+"dealerHigherTest.txt"));
        //Player Hit once, Dealer stand, Player Win
        assertEquals(2, game.fileInputPlay(filePath+"playerHitHigherTest.txt"));
        //Player Hit once, Dealer stand, Dealer win
        assertEquals(1, game.fileInputPlay(filePath+"playerHitDealerHigherTest.txt"));
        //Player hit once, Dealer hit, Player win
        assertEquals(2, game.fileInputPlay(filePath+"playerHitDealerHitPlayerHigherTest.txt"));
        //Player hit once, Dealer hit, Dealer win
        assertEquals(1, game.fileInputPlay(filePath+"bothHitDealerHigherTest.txt"));
        //player hit twice, dealer hit once, player wins
        assertEquals(1, game.fileInputPlay(filePath+"playerHitTwiceWin.txt"));
        //Push
        assertEquals(1, game.fileInputPlay(filePath+"pushTest.txt"));
    }
    
    @Test
    public void testSplit() throws Exception {
        BlackJackGame game = new BlackJackGame();
        //Player enter "D" as action, has two same card on hand, can do the split, 
        //and one of the hands bust
        assertEquals(2, game.fileInputPlay(filePath+"playerSplitWin.txt"));
        //Dealer has two same card on hand, can do split automatically
        assertEquals(2, game.fileInputPlay(filePath+"dealerSplit.txt"));
        //Player split and bust, dealer win 
        assertEquals(1, game.fileInputPlay(filePath+"playerSplitloss.txt"));
        //Dealer Split and bust, player win
        assertEquals(2, game.fileInputPlay(filePath+"dealerSplitLoss.txt"));
        //Player hit twice on one hand, hit once on another, dealer has initial score 20. 
        //choose the best hand of player to bit dealer's hand, dealer wins
        assertEquals(1, game.fileInputPlay(filePath+"playerSplitHitDealerWin.txt"));
        //Both Dealer and Player hit, the best of hands chose and player wins
        assertEquals(2, game.fileInputPlay(filePath+"playerSplitHitPlayerWin.txt"));
    }
}
