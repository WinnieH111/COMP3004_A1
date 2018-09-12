package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class BlackJackGame {

    public void FileInputPlay() throws IllegalArgumentException, IOException {
        System.out.println("Please enter the file input for this game: ");
        Scanner scan = new Scanner(System.in);
        String inputFile = scan.nextLine();
        FileInputPlay(inputFile);
    }
    
    public void FileInputPlay(String filePath) 
            throws IllegalArgumentException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        StringBuffer sbf = new StringBuffer("");
        String line = null;
        while ((line = br.readLine()) != null) {
                sbf.append(line);
            }
        String[] cardsAndInstruction = sbf.toString().split(" "); 
        br.close();
        
        if(cardsAndInstruction.length < 4) {
            throw new IllegalArgumentException ("The input number of card is not enough, game paused");
        }
        
        readCardsAndInstructions(cardsAndInstruction);
    }
    
    public void readCardsAndInstructions(String[] readContent) {
        Player player = new Player();
        Dealer dealer = new Dealer();
        player.addCard(new Card(readContent[0].substring(0, 1), (readContent[0].substring(1))));
        player.addCard(new Card(readContent[1].substring(0, 1), (readContent[1].substring(1))));
        dealer.addCard(new Card(readContent[2].substring(0, 1), (readContent[2].substring(1))));
        dealer.addCard(new Card(readContent[3].substring(0, 1), (readContent[3].substring(1))));

        player.printCards();
        dealer.printCards();

        if(blackJack(player, dealer)) {
            System.exit(0);
        }
    }
    public boolean blackJack(Player player, Dealer dealer) {
        boolean endOfGame = false;
        int playerScore = player.getScore(true);
        int dealerScore = dealer.getScore(true);

        if (dealerScore == 1010 && playerScore == 1010) {
            System.out.println("Dealer Wins with BlackJack!");
            endOfGame = true;
        }
        else if (dealerScore == 1010 && playerScore != 1010) {
            System.out.println("Dealer Wins with BlackJack!");
            endOfGame = true;
        }
        else if (dealerScore != 1010 && playerScore == 1010) {
            System.out.println("Player Wins with BlackJack!");
            endOfGame = true;
        }
        return endOfGame;
    }
    
    public static void main(String[] args) throws Exception {
        //Ask for if user prefer console or file input
        System.out.println("Please indicate if you prefer "
                + "Console input (C) or file input (F) by "
                + "enter C or F)");
        Scanner scan = new Scanner(System.in);
        String inputMethod = scan.nextLine();
        /*
        if(inputMethod.equalsIgnoreCase("C")) {
            new BlackJackGame().ConsoleInputPlay();
        }
        
        else*/
        if(inputMethod.equalsIgnoreCase("F")) {
            new BlackJackGame().FileInputPlay();
        }
        else {
            throw new IllegalArgumentException("The input method is not valid");
        }
    }
}
