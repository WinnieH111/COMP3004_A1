package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
        
        if(player.splitLegit()) {
            System.out.println("You have received 2 same card. Do you want to Split? Press D to split");
            Scanner scan = new Scanner(System.in);
            String action = scan.nextLine();
            if(action.equalsIgnoreCase("D")) {
                player.playersSplit();
                player.addCard(deck.getCard());
                player.printCards();
                System.out.println("Please indicate if you would like to Hit on this hand. H to hit, S to stand");
                action = scan.nextLine();
                while(action.equalsIgnoreCase("H") && bust(player, dealer, true) == 0) {
                    player.addCard(deck.getCard());
                }
                if(action.equalsIgnoreCase("S") 
                        || bust(player, dealer, true)!=0) {
                    player.splitHandAddCard(deck.getCard());
                }
                while(action.equals("H") && player.getSplitScore(false)<=21) {
                    player.splitHandAddCard(deck.getCard());
                }
                if(action.equalsIgnoreCase("S")||player.getSplitScore(false)>21){
                    if (dealer.dealerHit()) {
                        if(dealer.splitLegit()) {
                            dealer.playersSplit();
                            dealer.addCard(deck.getCard());
                            while(dealer.dealerHit()) {
                                dealer.addCard(deck.getCard());
                            }
                            dealer.splitHandAddCard(deck.getCard());
                            while(dealer.dealerSplitHit()) {
                                dealer.splitHandAddCard(deck.getCard());
                            }
                        }
                        else {
                            while(dealer.dealerHit()) {
                                dealer.addCard(deck.getCard());
                                if(bust(player, dealer, false)!=0) {
                                    System.exit(0);
                                }
                            }
                        }
                    }
                    
                }
                splitWinner(player, dealer);
            }
        }
            
        //if no BlackJack, and no split necessary, ask for player's action
        System.out.println("Please indicate your action. Enter H for hit, Enter S for stand.");
        Scanner scan = new Scanner(System.in);
        String inputAction = scan.nextLine();
        while(!inputAction.equalsIgnoreCase("S")) {
            if(inputAction.equalsIgnoreCase("H")) {
                player.addCard(deck.getCard());
                player.printCards();
                if(bust(player, dealer, false)==0) {
                    System.out.println("Please indicate your action. Enter H for hit, Enter S for stand.");
                    inputAction = scan.nextLine();
                }
                else {
                    System.exit(0);
                }   
            }
            else {
                System.out.println("Please check your input and try again.");
                inputAction = scan.nextLine();
            }
        }
        if(inputAction.equalsIgnoreCase("S")) {
            if(dealer.dealerHit()) {
                if(!dealer.splitLegit()) {
                    while(dealer.dealerHit()) {
                        dealer.addCard(deck.getCard());
                        if(bust(player, dealer, false)!=0) {
                            System.exit(0);
                        }
                    }
                    winner(player, dealer);
                    System.exit(0);
                }
                else if(dealer.splitLegit()) {
                    dealer.playersSplit();
                    dealer.addCard(deck.getCard());
                    while(dealer.dealerHit()) {
                        dealer.addCard(deck.getCard());
                    }
                    dealer.splitHandAddCard(deck.getCard());
                    while(dealer.dealerSplitHit() && bust(player, dealer, false) == 0) {
                        dealer.splitHandAddCard(deck.getCard());
                    }
                }
                splitWinner(player, dealer);
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
        
        List<Card> fileInputCards = new ArrayList<Card>();
        for(String cardString:cardsAndInstruction) {
            Card newCard = new Card(cardString.substring(0, 1), cardString.substring(1));
            fileInputCards.add(newCard);
        }
        return readCardsAndInstructions(fileInputCards);
    }

    public int readCardsAndInstructions(List<Card> readContent) throws Exception {
        int winner = 0;
        Player player = new Player();
        Dealer dealer = new Dealer();
        for(int i = 0; i <4; i++) {
            if(!cardAdded(player, dealer, readContent.get(0))) {
                if(i<2) {
                    player.addCard(readContent.remove(0));
                }
                else {
                    dealer.addCard(readContent.remove(0));
                }
            }
            else {
                throw new Exception("The card has been used.");
            }
        }

        player.printCards();
        dealer.printCards();

        //Test if any player blackjacked
        winner = blackJack(player, dealer);
        if(winner != 0) return winner;
        else if(readContent.size() == 0 && winner == 0) {
            throw new Exception("No enough Card Information to continue the game. Game terminated...");
        }

        //read Instruction
        String action = readContent.remove(0).validAction();
        if(action.equalsIgnoreCase("H")){
            if(!cardAdded(player, dealer, readContent.get(0))) {
                player.addCard(readContent.remove(0));
                if(bust(player, dealer, false)!=0) {
                    return bust(player, dealer, false);
                }
            }
            while(readContent.get(0).validAction().equalsIgnoreCase("H")) {
                readContent.remove(0);
                if(!cardAdded(player, dealer, readContent.get(0))) {
                    player.addCard(readContent.remove(0));
                    if(bust(player, dealer, false) != 0) {
                        return bust(player, dealer, false);
                    }
                }
            }
            if(readContent.get(0).validAction().equalsIgnoreCase("S")) {
                readContent.remove(0);
                if(dealer.splitLegit()) {
                    return dealerSplitHandler(player, dealer, readContent);
                }
                else {
                    while(dealer.dealerHit()) {
                        if(!cardAdded(player, dealer, readContent.get(0))) {
                            dealer.addCard(readContent.remove(0));
                            if(bust(player, dealer, false)!=0) {
                                return bust(player, dealer, false);
                            }
                        }
                    }
                    return winner(player, dealer);
                }
            }
            return winner;
        }

        else if(action.equalsIgnoreCase("D")) {
            if(!player.splitLegit()) {
                throw new Exception ("Player can not split. Please check your file input and try again. Game terminate...");
            }
            else {
                player.playersSplit();
                if(!cardAdded(player, dealer, readContent.get(0))) {
                    player.addCard(readContent.remove(0));
                }

                while(bust(player, dealer, true) == 0 
                        && readContent.get(0).validAction().equalsIgnoreCase("H")) {
                    readContent.remove(0);
                    if(!cardAdded(player, dealer, readContent.get(0))){
                        player.addCard(readContent.remove(0));
                        
                    }
                }
                if(bust(player, dealer, true)!=0) {
                    //continue with split hand and dealer hands 
                    return playerSplitHand(player, dealer, readContent);
                }
                else if(readContent.remove(0).validAction().equalsIgnoreCase("S")) {
                    //Continue with split hand 
                    return playerSplitHand(player, dealer, readContent);
                }

            }
        }

        else if(action.equalsIgnoreCase("S")) {
            if(dealer.dealerHit() && dealer.splitLegit()) {
                return dealerSplitHandler(player, dealer, readContent);
            }
            else if(dealer.dealerHit() && !dealer.splitLegit()) {
                while(dealer.dealerHit()) {
                    dealer.addCard(readContent.remove(0));
                    if(bust(player, dealer, false) != 0) {
                        return bust(player, dealer, false);
                    }
                }
                return winner(player, dealer);
            }
            else return winner(player, dealer);
        }

        else  {
            throw new Exception("Player's action instruction is required. "
                    + "Please check your input file and try again. Game terminated...");
        }
        return winner;
    }

    public int playerSplitHand(Player player, Dealer dealer, List<Card> readContent) throws Exception {
        if(!cardAdded(player, dealer, readContent.get(0))) {
            player.splitHandAddCard(readContent.remove(0));
        }
        while (player.getSplitScore(false)<=21 
                && readContent.get(0).validAction().equalsIgnoreCase("H")) {
            readContent.remove(0);
            if(!cardAdded(player, dealer, readContent.get(0))) {
                player.splitHandAddCard(readContent.remove(0));
            }
        }
        if(player.getSplitScore(false)>21 && player.getScore(false)>21) {
            System.out.print("Player bust, dealer wins.\nDealer's score is " + dealer.getScore(false) + "\nPlayer's score is " + player.getScore(false) + "\n" + "\nPlayer's Split hand score is " + player.getSplitScore(false));
            player.printCards();
            player.printSplittedCards();
            dealer.printAllCards();
            return 1;
            }
        
        else if( readContent.get(0).validAction().equalsIgnoreCase("S")) {
            readContent.remove(0);
            return dealerSplitHandler(player, dealer, readContent);
        }
     
        
            return dealerSplitHandler(player, dealer, readContent);
        
    }
    
    public int dealerSplitHandler(Player player, Dealer dealer, List<Card> readContent) throws Exception {
        if(dealer.splitLegit()) {
            dealer.playersSplit();

            while(dealer.dealerHit() && bust(player, dealer, true) == 0) {
                if(!cardAdded(player, dealer, readContent.get(0))){
                    dealer.addCard(readContent.remove(0));
                }
            }
            while(dealer.dealerSplitHit() && dealer.getSplitScore(false)<=21) {
                if(!cardAdded(player, dealer, readContent.get(0))){
                    dealer.splitHandAddCard(readContent.remove(0));
                }
            }
        }
        else {
            while(dealer.dealerHit()) {
                if(!cardAdded(player, dealer, readContent.get(0))) {
                    dealer.addCard(readContent.remove(0));
                }
            }
        }
        return splitWinner(player, dealer);
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

    public int bust(Player player, Dealer dealer, boolean splittedPlay) {
        int endOfGame = 0;
        int playerScore = player.getScore(false);
        int dealerScore = dealer.getScore(false);
        if(!splittedPlay) {
            if (playerScore>21 && dealerScore < 21) {
                System.out.print("Player bust, dealer wins.\nDealer's score is " + dealerScore + "\nPlayer's score is " + playerScore + "\n");
                player.printCards();
                dealer.printAllCards();
                endOfGame = 1;
            }
            else if (playerScore < 21 && dealerScore > 21) {
                System.out.println("Dealer bust, Player wins.\nPlayer's score is " + playerScore + "\nDealer's score is " + dealerScore + "\n");
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
        for(Iterator<Map.Entry<String,Integer>>it=playersScore.entrySet().iterator();it.hasNext();){
            Map.Entry<String, Integer> entry = it.next();
            if (entry.getValue() >21 || entry.getValue() == 0) {
                 it.remove();
            }
        }
        if(playersScore.containsKey("playerScore1") && playersScore.containsKey("playerScore2")) {
            if(playersScore.get("playerScore1") > playersScore.get("playerScore2")) {
                playerScore = playersScore.get("playerScore1");
            }
            else playerScore = playersScore.get("playerScore2");
        }
        else if(!playersScore.containsKey("playerScore1") && !playersScore.containsKey("PlayerScore2")) {
            System.out.println("Player bust! Dealer wins! ");
            winner = 1;
            
        }
            
        else if(!playersScore.containsKey("playerScore2")) {
            playerScore = playersScore.get("playerScore1");
        }
        else if (!playersScore.containsKey("playerScore1")){
            playerScore = playersScore.get("playerScore2");
        }
        
        if(playersScore.containsKey("dealerScore1") && playersScore.containsKey("dealerScore2")) {
            if(playersScore.get("dealerScore1") > playersScore.get("dealerScore2")) {
                dealerScore = playersScore.get("dealerScore1");
            }
            else dealerScore = playersScore.get("dealerScore2");
        }
        else if(!playersScore.containsKey("dealerScore1") && !playersScore.containsKey("dealerScore2")){
            System.out.println("Dealer bust! Player wins! ");
            winner = 2;
        }
        else if(!playersScore.containsKey("dealerScore1")) {
            dealerScore = playersScore.get("dealerScore2");
        }
        else if(!playersScore.containsKey("dealerScore2")) {
            dealerScore = playersScore.get("dealerScore1");
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
    
    public boolean cardAdded(Player player, Dealer dealer, Card newCard) throws Exception {
        boolean cardAddedResult = cardAddedCheck(player, dealer, newCard);
        if(cardAddedResult) {
            throw new Exception ("The new card can not be added because it is used. "
                    + "Please check your input file. Game terminated...");
        }
        return cardAddedResult;
    }
    
    public boolean cardAddedCheck(Player player, Dealer dealer, Card newCard) {
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
