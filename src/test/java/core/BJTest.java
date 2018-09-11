package core;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import junit.framework.TestCase;

//REMEBER to change all the file place holder to test file path
public class BJTest extends TestCase{
    @Test(expected = RuntimeException.class)
    public void testFileInputEmpty() {
        BlackJackGame game = new BlackJackGame();
        game.FileInputPlay(emptyFile);
    }
    
    @Test(expected = RuntimeException.class)
    public void testFileOnly1Card() {
        BlackJackGame game = new BlackJackGame();
        game.FileInputPlay(oneCardFile);
    }
    @Test(expected = RuntimeException.class)
    public void testFile2Cards() {
        BlackJackGame game = new BlackJackGame();
        game.FileInputPlay(twoCardFile);
    }
    
    @Test(expected = RuntimeException.class)
    public void testFile3Cards() {
        BlackJackGame game = new BlackJackGame();
        game.FileInputPlay(threeCardFile);
    }
    
    //Four cards input is tested here
    public void testBlackJack() throws Exception {
        BlackJackGame game = new BlackJackGame();
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        //Dealer and player blackjack, dealer win
        game.readFile(blackJackBothTest);
        assertEquals(true, outContent.toString().contains("Dealer Wins with BlackJack"));
        //Dealer Blackjack and win
        game.readFile(blackJackDealerWinTest);
        assertEquals(true, outContent.toString().contains("Dealer Wins with BlackJack"));
        //Player blackjack and win
        game.readFile(blackJackPlayererWinTest);
        assertEquals(true, outContent.toString().contains("Player Wins with BlackJack"));
    }
    
    public void testBurst() throws Exception {
        BlackJackGame game = new BlackJackGame();
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        //Player hit once and burst, dealer win
        game.readFile(playerBurstTest);
        assertEquals(true, outContent.toString().contains("Dealer Wins"));
        //Player hit twice and burst, dealer win
        game.readFile(playerBurstTwiceTest);
        assertEquals(true, outContent.toString().contains("Dealer Wins"));
        //Player stand, dealer hit and burst, player win 
        game.readFile(dealerBurstTest);
        assertEquals(true, outContent.toString().contains("Player Wins"));
    }
    
    public void testWin() throws Exception {
        BlackJackGame game = new BlackJackGame();
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        //Player stand, Dealer stand, Player Win
        game.readFile(playerHigherTest);
        assertEquals(true, outContent.toString().contains("Player Wins"));
        //Player stand, Dealer stand, Dealer Win
        game.readFile(dealerHigherTest);
        assertEquals(true, outContent.toString().contains("Dealer Wins"));
        //Player Hit once, Dealer stand, Player Win
        game.readFile(playerHitHigherTest);
        assertEquals(true, outContent.toString().contains("Player Wins"));
        //Player Hit once, Dealer stand, Dealer win
        game.readFile(playerHitDealerHigherTest);
        assertEquals(true, outContent.toString().contains("Dealer Wins"));
        //Player hit once, Dealer hit, Player win
        game.readFile(playerHitHigherTest);
        assertEquals(true, outContent.toString().contains("Player Wins"));
        //Player hit once, Dealer hit, Dealer win
        game.readFile(bothHitDealerHigherTest);
        assertEquals(true, outContent.toString().contains("Dealer Wins"));
    }
}
