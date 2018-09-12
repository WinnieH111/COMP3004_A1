package core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import junit.framework.TestCase;

//REMEBER to change all the file place holder to test file path
public class BJTest extends TestCase{
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    final String filePath = "src/test/resources/";
    
    public void testFileInputEmpty() throws IllegalArgumentException, IOException {
        BlackJackGame game = new BlackJackGame();
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("not enough");
        game.FileInputPlay(filePath+"/emptyFile.txt");
    }
    
    @Test
    public void testFileOnly1Card() throws Exception {
        BlackJackGame game = new BlackJackGame();
        exception.expect(Exception.class);
        exception.expectMessage("not enough");
        game.FileInputPlay(filePath+"/oneCardFile.txt");
    }
    @Test(expected = Exception.class)
    public void testFile2Cards() throws Exception {
        BlackJackGame game = new BlackJackGame();
        game.FileInputPlay(filePath+"/twoCardFile.txt");
    }
    
    @Test(expected = Exception.class)
    public void testFile3Cards() throws Exception {
        BlackJackGame game = new BlackJackGame();
        game.FileInputPlay(filePath+"/threeCardFile.txt");
    }
    
    //Four or more cards input, but at least one is invalid card
    @Test(expected = Exception.class)
    public void testInvalidCardInput() throws Exception {
        BlackJackGame game = new BlackJackGame();
        game.FileInputPlay(filePath+"/invalidCardInput.txt");
    }
    
    //Four cards input is tested here
    public void testBlackJack() throws Exception {
        BlackJackGame game = new BlackJackGame();
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        //Dealer and player blackjack, dealer win
        game.FileInputPlay(filePath+"/blackJackBothTest.txt");
        assertEquals(true, outContent.toString().contains("Dealer Wins with BlackJack"));
        //Dealer Blackjack and win
        game.FileInputPlay(filePath+"/blackJackDealerWinTest.txt");
        assertEquals(true, outContent.toString().contains("Dealer Wins with BlackJack"));
        //Player blackjack and win
        game.FileInputPlay(filePath+"/blackJackPlayerWinTest.txt");
        assertEquals(true, outContent.toString().contains("Player Wins with BlackJack"));
    }
    
    public void testBurst() throws Exception {
        BlackJackGame game = new BlackJackGame();
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        //Player hit once and burst, dealer win
        game.FileInputPlay(filePath+"/playerBurstTest");
        assertEquals(true, outContent.toString().contains("Dealer Wins"));
        //Player hit twice and burst, dealer win
        game.FileInputPlay(filePath+"/playerBurstTwiceTest");
        assertEquals(true, outContent.toString().contains("Dealer Wins"));
        //Player stand, dealer hit and burst, player win 
        game.FileInputPlay(filePath+"/dealerBurstTest");
        assertEquals(true, outContent.toString().contains("Player Wins"));
    }
    
    public void testWin() throws Exception {
        BlackJackGame game = new BlackJackGame();
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        //Player stand, Dealer stand, Player Win
        game.FileInputPlay(filePath+"/playerHigherTest");
        assertEquals(true, outContent.toString().contains("Player Wins"));
        //Player stand, Dealer stand, Dealer Win
        game.FileInputPlay(filePath+"/dealerHigherTest");
        assertEquals(true, outContent.toString().contains("Dealer Wins"));
        //Player Hit once, Dealer stand, Player Win
        game.FileInputPlay(filePath+"/playerHitHigherTest");
        assertEquals(true, outContent.toString().contains("Player Wins"));
        //Player Hit once, Dealer stand, Dealer win
        game.FileInputPlay(filePath+"/playerHitDealerHigherTest");
        assertEquals(true, outContent.toString().contains("Dealer Wins"));
        //Player hit once, Dealer hit, Player win
        game.FileInputPlay(filePath+"/playerHitHigherTest");
        assertEquals(true, outContent.toString().contains("Player Wins"));
        //Player hit once, Dealer hit, Dealer win
        game.FileInputPlay(filePath+"/bothHitDealerHigherTest");
        assertEquals(true, outContent.toString().contains("Dealer Wins"));
    }
    
    public void testSplit() throws IllegalArgumentException, IOException {
        BlackJackGame game = new BlackJackGame();
        //player enter "D" as action, but has two different card, does not allow to split (Print error)
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        game.FileInputPlay(filePath + "/playNotLegitSplit");
        assertEquals(true, outContent.toString().contains("Player can not split"));
        //Player enter "D" as action, has two same card on hand, can do the split
        //Dealer has two same card on hand, can do split automatically
    }
}
