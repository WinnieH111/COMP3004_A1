package core;

import org.junit.Test;

import junit.framework.TestCase;

//REMEBER to change all the file place holder to test file path
public class FileInputTests extends TestCase{
 
    final String filePath = "src/test/resources/";
    
    public void testFileInputEmpty() throws Exception {
        try {
            BlackJackGame game = new BlackJackGame();
            game.fileInputPlay(filePath+"/emptyFile.txt");
        } catch (Exception expected) {
            
        }
    }
    
    @Test
    public void testFileOnly1Card() throws Exception {
        try {
            BlackJackGame game = new BlackJackGame();
            game.fileInputPlay(filePath+"/oneCardFile.txt");
        } catch (Exception expected) {
            }
        }
        
    public void testFile2Cards() throws Exception {
        try{
            BlackJackGame game = new BlackJackGame();
            game.fileInputPlay(filePath+"/twoCardFile.txt");
        } catch (Exception expected) {
            }
        }
    
    public void testFile3Cards() throws Exception {
        try {
            BlackJackGame game = new BlackJackGame();
            game.fileInputPlay(filePath+"/threeCardFile.txt");
        } catch (Exception expected) {
            }
        }
    
    //Four or more cards input, but at least one is invalid card
    public void testInvalidCardInput() throws Exception {
        try{
            BlackJackGame game = new BlackJackGame();
            game.fileInputPlay(filePath+"/invalidCardInput.txt");
        } catch (Exception expected) {
            }
        }
    
    public void testDuplicateCardInitial() throws Exception {
        try{
            BlackJackGame game = new BlackJackGame();
            game.fileInputPlay(filePath+"/duplicateCardInput.txt");
        } catch(Exception expected) {
            }
        }
    
    public void testDuplicateCardPlayer() throws Exception {
        try{
            BlackJackGame game = new BlackJackGame();
            game.fileInputPlay(filePath+"/duplicateCardInputPlayerHit.txt");
        } catch (Exception expected) {
            }
        }
    
    public void testDuplicateCardDealer() throws Exception {
        try{
            BlackJackGame game = new BlackJackGame();
            game.fileInputPlay(filePath+"/duplicateCardInputDealerHit.txt");
        } catch (Exception expected) {
        }
    }
    
    public void testSplitDifferentCards() throws Exception{
        try {
            BlackJackGame game = new BlackJackGame();
            game.fileInputPlay(filePath+"splitDifferentCard.txt");
        } catch(Exception expected) {
            
        }
    }
    
    public void testNoBlackJackNofutureCards() throws Exception{
        try {
            BlackJackGame game = new BlackJackGame();
            game.fileInputPlay(filePath+"noBJButNoMoreCards.txt");
        } catch(Exception expected) {
            
        }
    }
}
