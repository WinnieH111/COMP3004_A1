package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class BlackJackGame {


    public void ConsoleInputPlay() throws Exception {
        //Initial the deck
        Deck deck = new Deck();
        //Initially provide 2 cards to player and dealer
        Player player = new Player();
        Dealer dealer = new Dealer();
        player.addCard(deck.getCard());
        player.addCard(deck.getCard());
        dealer.addCard(deck.getCard());
        dealer.addCard(deck.getCard());

        //Both player's cards visible
        player.printCards();
        dealer.printCards();

        //Calculate value and judge BlackJack 
        if (blackJack(player, dealer)!=0) {
            System.exit(0);
        }
        else if (player.splitLegit()) {
            //Split and go through the split flow
            System.out.println("Press D to split.");
            Scanner scan = new Scanner(System.in);
            String split = scan.nextLine();
            if(split.equalsIgnoreCase("D")) {
                player.cardOnHand.add(deck.getCard());
                player.printCards();
                player.splittedCardOnHand.add(deck.getCard());
                System.out.println("Please indicate your action. Enter H for hit, Enter S for stand.");
                String inputAction = scan.nextLine();
                while(inputAction.equalsIgnoreCase("H")) {
                    player.addCard(deck.getCard());
                    player.printCards();
                    if(burst(player, dealer, false) == 0) {
                        System.out.println("Please indicate your action. Enter H for hit, Enter S for stand.");
                        inputAction = scan.nextLine();
                    }
                    else {
                        System.exit(0);
                    }
                }
                if(!inputAction.equalsIgnoreCase("S")) {
                    System.out.println("Please indicate your action. Enter H for hit, Enter S for stand.");
                    inputAction = scan.nextLine();
                }
                player.printSplittedCards();
                System.out.println("Please indicate your action. Enter H for hit, Enter S for stand.");
                inputAction = scan.nextLine();
                while(inputAction.equalsIgnoreCase("H")) {
                    player.addCard(deck.getCard());
                    player.printCards();
                    if(burst(player, dealer, false) == 0) {
                        System.out.println("Please indicate your action. Enter H for hit, Enter S for stand.");
                        inputAction = scan.nextLine();
                    }
                    else {
                        System.exit(0);
                    }
                }
                if(!inputAction.equalsIgnoreCase("S")) {
                    System.out.println("Please indicate your action. Enter H for hit, Enter S for stand.");
                    inputAction = scan.nextLine();
                }

            }
        }
        //if no BlackJack, and no split necessary, ask for player's action
        System.out.println("Please indicate your action. Enter H for hit, Enter S for stand.");
        Scanner scan = new Scanner(System.in);
        String inputAction = scan.nextLine();
        while(inputAction.equalsIgnoreCase("H")){
            player.addCard(deck.getCard());
            player.printCards();
            if(burst(player, dealer, false)==0) {
                System.out.println("Please indicate your action. Enter H for hit, Enter S for stand.");
                inputAction = scan.nextLine();
            }
            else {
                System.exit(0);
            }   
        }

        if(!inputAction.equalsIgnoreCase("S")) {
            System.out.println("Please check and Enter again.");
            inputAction = scan.nextLine();
        }

        while(dealer.dealerHit()) {
            dealer.addCard(deck.getCard());
            if(burst(player, dealer, false)!=0) {
                System.exit(0);
            }
        }
        winner(player, dealer);
        System.exit(0);
    }

    public void FileInputPlay() throws Exception {
        System.out.println("Please enter the file input for this game: ");
        Scanner scan = new Scanner(System.in);
        String inputFile = scan.nextLine();
        FileInputPlay(inputFile);
    }

    public int FileInputPlay(String filePath) 
            throws Exception {
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

        return readCardsAndInstructions(cardsAndInstruction);
    }

    public int readCardsAndInstructions(String[] readContent) throws Exception {
        int winner = 0;
        Player player = new Player();
        Dealer dealer = new Dealer();
        for(int i = 0; i <4; i++) {
            Card newCard = new Card(readContent[i].substring(0, 1), (readContent[i].substring(1)));
            if(!cardAdded(player, dealer, newCard)) {
                if(i<2) {
                    player.addCard(newCard);
                }
                else 
                    dealer.addCard(newCard);
            }
            else {
                throw new Exception("The card has been used.");
            }
        }

        player.printCards();
        dealer.printCards();

        //Test if any player blackjacked
        winner = blackJack(player, dealer);
        if(winner!=0) return winner;
        else if(readContent.length == 4 && winner == 0) {
            throw new Exception("No enough Card Information to play the game.");
        }

        //Receive player's playing instruction
        int i = 4;
        while(readContent[i]!=null) {
            if(readContent[i].equalsIgnoreCase("H")){
                Card newCard = new Card(readContent[i+1].substring(0, 1), (readContent[i+1].substring(1)));
                if(!cardAdded(player, dealer, newCard)){
                    player.addCard(newCard);
                    if(burst(player, dealer, false)!=0){
                        return burst(player, dealer, false);
                    }
                }
            }
            else if (readContent[i].equalsIgnoreCase("D")){
                if(player.splitLegit() && i == 4) {
                    //Do the Split 
                }
                else {
                    throw new Exception("User is not legit for split, please check the input file. Game terminate...");
                }

            }
            else if (readContent[i].equalsIgnoreCase("S")){
                if(dealer.dealerHit() && !dealer.splitLegit()){
                    int j = 1;
                    while (burst(player, dealer, false)==0 && dealer.dealerHit() && readContent[i+j]!=null){
                        Card newCard = new Card(readContent[i+j].substring(0, 1), (readContent[i+j].substring(1)));
                        if(!cardAdded(player, dealer, newCard)){
                            dealer.addCard(newCard);
                            j++;
                        }
                        else {
                            throw new Exception ("This card has been used");
                        }
                    }
                    if(burst(player, dealer, false)!=0){
                        return burst(player, dealer, false);
                    }
                    if(!dealer.dealerHit()){
                        return winner(player, dealer);
                    }
                    if(readContent[i+j]==null && dealer.dealerHit()) {
                        throw new Exception ("No enough information to continue this game");
                    }
                }
               
                else {
                    return winner(player, dealer);
                }
            }
            else {
                throw new Exception ("Not enough information from the player to continue this game");
            }
            i+=2;
        }
        return winner;
    }

    public int blackJack(Player player, Dealer dealer) {
        int endOfGame = 0;
        int playerScore = player.getScore(true);
        int dealerScore = dealer.getScore(true);

        if (dealerScore == 1010 && playerScore == 1010) {
            System.out.print("Dealer Wins with BlackJack!");
            player.printCards();
            dealer.printAllCards();
            endOfGame = 1;
        }
        else if (dealerScore == 1010 && playerScore != 1010) {
            System.out.print("Dealer Wins with BlackJack!\n");
            player.printCards();
            dealer.printAllCards();
            endOfGame = 1;
        }
        else if (dealerScore != 1010 && playerScore == 1010) {
            System.out.print("Player Wins with BlackJack!\n");
            player.printCards();
            dealer.printAllCards();
            endOfGame = 2;
        }
        return endOfGame;
    }

    public int burst(Player player, Dealer dealer, boolean splittedPlay) {
        int endOfGame = 0;
        int playerScore = player.getScore(false);
        int dealerScore = dealer.getScore(false);
        if(!splittedPlay) {
            if (playerScore>21 && dealerScore < 21) {
                System.out.print("Player burst, dealer wins.\nDealer's score is " + dealerScore + "\nPlayer's score is " + playerScore + "\n");
                player.printCards();
                dealer.printAllCards();
                endOfGame = 1;
            }
            else if (playerScore < 21 && dealerScore > 21) {
                System.out.println("Dealer burst, Player wins.\nPlayer's score is " + playerScore + "\nDealer's score is " + dealerScore + "\n");
                player.printCards();
                dealer.printAllCards();
                endOfGame = 2;
            }
        }
        else {
            if (playerScore > 21 && dealerScore < 21){
                endOfGame = 1;
            }
            else if (playerScore < 21 && dealerScore > 21){
                endOfGame = 2;
            }
        }
        return endOfGame;
    }

    public int winner(Player player, Dealer dealer) 
            throws Exception {
        int winner = 0;
        final int playerFinalScore = player.getScore(false);
        final int dealerFinalScore = dealer.getScore(false);

        if(playerFinalScore > dealerFinalScore) {
            winner=2;
            System.out.println("Player wins!\nPlayer's score is : " + playerFinalScore + "\nDealer's score is : " + dealerFinalScore + "\n");
            player.printCards();
            dealer.printAllCards();
        }
        else if(playerFinalScore < dealerFinalScore) {
            System.out.println("Dealer wins!\nPlayer's score is : " + playerFinalScore + "\nDealer's score is : " + dealerFinalScore + "\n");
            winner = 1;
            player.printCards();
            dealer.printAllCards();
        }
        else if(playerFinalScore == dealerFinalScore){
            System.out.println("Dealer wins on a Push!\nPlayer's and Dealer's score are: " + playerFinalScore + "\n");
            player.printCards();
            dealer.printAllCards();
        }
        return winner;
    }

    public int splitWinner(Player player, Dealer dealer){
        int winner = 0;
        Map<String, Integer> playersScore = new HashMap<String, Integer>(); 
        playersScore.put("playerScore1", player.getScore(false));
        playersScore.put("playerScore2", player.getSplitScore(false));
        playersScore.put("dealerScore1", dealer.getScore(false));
        playersScore.put("dealerScore2", dealer.getSplitScore(false));
        int playerScore = 0;
        int dealerScore = 0;
        for(Entry<String, Integer> entry : playersScore.entrySet()){
            if(entry.getValue() > 21 || entry.getValue() == 0) {
                playersScore.remove(entry.getKey());
            }
        }
        if(playersScore.containsKey("playerScore1") && playersScore.containsKey("playerScore2")) {
            if(playersScore.get("playerScore1") > playersScore.get("playerScore2")) {
                playerScore = playersScore.get("playerScore1");
            }
            else playerScore = playersScore.get("playerScore2");
        }
        else if(!playersScore.containsKey("playerScore1")) {
            playerScore = playersScore.get("playerScore2");
        }
        else if(!playersScore.containsKey("playerScore2")) {
            playerScore = playersScore.get("playerScore1");
        }
        else {
            System.out.println("Player Burst! Dealer wins! ");
            winner = 1;
        }
        
        if(playersScore.containsKey("dealerScore1") && playersScore.containsKey("dealerScore2")) {
            if(playersScore.get("dealerScore1") > playersScore.get("dealerScore2")) {
                playerScore = playersScore.get("dealerScore1");
            }
            else playerScore = playersScore.get("dealerScore2");
        }
        else if(!playersScore.containsKey("dealerScore1")) {
            playerScore = playersScore.get("dealerScore2");
        }
        else if(!playersScore.containsKey("dealerScore2")) {
            playerScore = playersScore.get("dealerScore1");
        }
        else {
            System.out.println("Dealer Burst! Player wins! ");
            winner = 2;
        }
        
        if(playerScore > dealerScore) {
            System.out.println("Player wins!\nPlayer's score is : " + playerScore + "\nDealer's score is : " + dealerScore + "\n");
            player.printCards();
            player.printSplittedCards();
            dealer.printAllCards();
            dealer.printSplittedCards();
            winner = 2;
        }
        else if(playerScore < dealerScore) {
            System.out.println("Dealer wins!\nPlayer's score is : " + playerScore + "\nDealer's score is : " + dealerScore + "\n");
            player.printCards();
            player.printSplittedCards();
            dealer.printAllCards();
            dealer.printSplittedCards();
            winner = 1;
        }
        else if(playerScore == dealerScore) {
            System.out.println("Dealer wins with a push!\nPlayer's and dealer's score is : " + playerScore + "\n");
            player.printCards();
            player.printSplittedCards();
            dealer.printAllCards();
            dealer.printSplittedCards();
            winner = 1;
        }
        return winner;
    }
    
    public boolean cardAdded(Player player, Dealer dealer, Card newCard) {
        if(player.cardOnHand.isEmpty()) {
            return false;
        }
        else if(!player.cardOnHand.isEmpty()){
            for(Card card:player.cardOnHand) {
                if(card.getCardString().equals(newCard.getCardString())) {
                    return true;
                }
            }
        }
        else if(!player.splittedCardOnHand.isEmpty()) {
            for(Card card1:player.splittedCardOnHand) {
                if(card1.getCardString().equals(newCard.getCardString())) {
                    return true;
                }
            }
        }
        else if (!dealer.cardOnHand.isEmpty()){
            for(Card dCard:dealer.cardOnHand) {
                if(dCard.getCardString().equals(newCard.getCardString())) {
                    return true;
                }
            }
        }
        else if(!dealer.splittedCardOnHand.isEmpty()) {
            for(Card dCard1:dealer.splittedCardOnHand) {
                if(dCard1.getCardString().equals(newCard.getCardString())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        //Ask for if user prefer console or file input
        System.out.println("Please indicate if you prefer "
                + "Console input (C) or file input (F) by "
                + "enter C or F)");
        Scanner scan = new Scanner(System.in);
        String inputMethod = scan.nextLine();

        if(inputMethod.equalsIgnoreCase("C")) {
            new BlackJackGame().ConsoleInputPlay();
        }
        else if(inputMethod.equalsIgnoreCase("F")) {
            new BlackJackGame().FileInputPlay();
        }
        else {
            throw new IllegalArgumentException("The input method is not valid");
        }
    }
}
