package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HomeScreen extends Application {
    
    private static final Logger logger = LogManager.getLogger(HomeScreen.class);
    
    private Deck deck;
    private Player player = new Player();  
    private Dealer dealer =  new Dealer();  
    private BlackJackGame game = new BlackJackGame();
    
    FlowPane playerCards = new FlowPane(Orientation.HORIZONTAL);
    FlowPane dealerCards = new FlowPane(Orientation.HORIZONTAL);
    
    FlowPane playerSplittedCard = new FlowPane(Orientation.HORIZONTAL);
    FlowPane dealerSplittedCard = new FlowPane(Orientation.HORIZONTAL);
    
    Label dealerTag = new Label("Dealer"); 
    Label playerTag = new Label("Player"); 
    
    Label status = new Label();
    Image imageback = new Image("file:src/main/resources/table.png");
    
    Button drawbtn = new Button();
    Button standbtn = new Button();
    Button newGamebtn = new Button();
    Button splitbtn = new Button();
    Button splitHitbtn = new Button();
    Button splitStandbtn = new Button();
    
    
    boolean busted = false;
    boolean playerTurn = false; 
    boolean gameEnd = false;
    boolean split = false;
    
    public void drawCardPlayer(Player player, FlowPane pane){
        try {
            Card card = deck.getCard();
            ImageView img = new ImageView(card.getCardFace()); 
            pane.getChildren().add(img); 
            player.addCard(card);
            logger.info("Player's new card is {}\n", card.getCardString());
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    public void drawCardSplitPlayer(Player player, FlowPane pane) {
        try {
            Card card = deck.getCard();
            ImageView img = new ImageView(card.getCardFace()); 
            pane.getChildren().add(img); 
            player.splitHandAddCard(card);
            logger.info("Player's new card is {}\n", card.getCardString());
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    public void drawCardSplitDealer(Dealer dealer, FlowPane pane) {
        try {
            Card card = deck.getCard();
            ImageView img = new ImageView(card.getCardFace()); 
            pane.getChildren().add(img); 
            dealer.splitHandAddCard(card);
            logger.info("Dealer's new card is {}\n", card.getCardString());
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    public void drawCardDealer(Dealer dealer, FlowPane pane) {
        try {
            Card card = deck.getCard();
            ImageView img = new ImageView(card.getCardFace());
            pane.getChildren().add(img);
            dealer.addCard(card);
            logger.info("Dealer's new card is {}\n", card.getCardString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void drawCardBack(FlowPane pane) {
        try {
            Card card = deck.getCard();
            ImageView img = new ImageView(card.getCardBack());
            pane.getChildren().add(img);
            dealer.addCard(card);
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }
    
    public void showCards(Dealer dealer, FlowPane pane) {
        if (gameEnd) {
            dealerCards.getChildren().removeAll(dealerCards.getChildren());
            for (Card card : dealer.cardOnHand){
                ImageView img = new ImageView(card.getCardFace());
                pane.getChildren().add(img);
            }
        }
        else {
            status.setText("Game not ended.");
            
        }
    }
    
    public void newGame(){
        logger.info("New game starts.");

        // clear everything 
        player.empty(); 
        dealer.empty(); 

        playerCards.getChildren().removeAll(playerCards.getChildren());  
        dealerCards.getChildren().removeAll(dealerCards.getChildren()); 
        
        busted = false; 
        playerTurn = true; 
        gameEnd = false;
        
        // draw cards for the initial hands, player gets 2, dealer gets 2 shows 1 
        deck = new Deck();
        drawCardPlayer(player, playerCards); 
        drawCardPlayer(player, playerCards);
        drawCardDealer(dealer, dealerCards);
        drawCardBack(dealerCards);
        
        status.setText("Game Start!"); 
    }
    
    @Override
    public void start(Stage primaryStage) {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        grid.setHgap(5.5);
        grid.setVgap(5.5); 
        
        grid.add(dealerTag, 0, 0);
        grid.add(dealerCards, 0, 1, 5, 1); 
        grid.add(dealerSplittedCard, 0, 2, 5, 1);
        
        grid.add(playerSplittedCard, 0, 3, 5, 1);
        grid.add(playerCards, 0, 4, 5, 1); 
        grid.add(playerTag, 0, 5);
        
        grid.add(splitbtn, 0, 6);
        grid.add(drawbtn,1,6);
        grid.add(standbtn,2,6);
        grid.add(newGamebtn, 3, 6); 
        grid.add(status, 0, 8, 6, 1);
        
        grid.add(splitHitbtn, 1, 7);
        grid.add(splitStandbtn, 2, 7);
        grid.setBackground(background);
        
        Scene scene = new Scene(grid, 1600, 900);
        
        primaryStage.setTitle("BlackJack");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        status.setTextFill(Color.web("#FFF")); 
        status.setFont(new Font("Verdana", 24)); 
        
        dealerTag.setTextFill(Color.web("#FFF")); 
        dealerTag.setFont(new Font("Verdana", 24)); 
        
        playerTag.setTextFill(Color.web("#FFF")); 
        playerTag.setFont(new Font("Verdana", 24));
        
        newGame();
        
        int blackJack = game.blackJack(player, dealer);
        if(blackJack == 1) {
            status.setText("Dealer Wins with BlackJack!");
            showCards(dealer, dealerCards);
            playerTurn = false;
            gameEnd = true;
        }
        else if(blackJack == 2) {
            status.setText("Player Wins with BlackJack!");
            showCards(dealer, dealerCards);
            playerTurn = false;
            gameEnd = true;
        }

        else if(blackJack == 0){
            splitbtn.setText("Split");
            splitHitbtn.setText("Hit(S)");
            splitStandbtn.setText("Stand(S)");
            drawbtn.setText("Hit");
            standbtn.setText("Stand");

            
            if(player.splitLegit()) {
                split = true;
                
                splitbtn.setOnAction((e) -> {
                    player.playersSplit();
                    drawCardSplitPlayer(player, playerSplittedCard);
                    splitHitbtn.setOnAction((e1) -> {
                        if(playerTurn && split && game.bust(player, dealer, true) == 0){
                            drawCardSplitPlayer(player, playerCards); 
                        }
                    });
                        //int playerBust = game.bust(player, dealer, false);
                        /*if(playerBust == 1){
                            
                        
                        }*/
                    //});
                });
            }
            else {
               
                drawbtn.setOnAction((e) -> {
                    if(playerTurn == true && game.bust(player, dealer, false) == 0){
                        logger.info("Player Hits.");
                        drawCardPlayer(player, playerCards); 
                    }
                    int playerBust = game.bust(player, dealer, false);
                    if(playerBust == 1){
                        // you busted 
                        busted = true; 
                        playerTurn = false;
                        gameEnd = true;
                        status.setText("Player busted. Dealer Wins!"); 
                        showCards(dealer, dealerCards);
                    }
                });

                standbtn.setOnAction((e) -> {
                    if(playerTurn == true && busted != true && gameEnd == false) {
                        playerTurn = false; 
                        logger.info("Player Stands.");

                        while(game.bust(player, dealer, false) == 0 && dealer.dealerHit()){
                            logger.info("Dealer Hits.");
                            drawCardBack(dealerCards);
                        }
                        int dealerBust = game.bust(player, dealer, false);
                        if(dealerBust == 2) {
                            busted = true;
                            gameEnd = true;
                            status.setText("Dealer Burst! Player Wins!");
                            showCards(dealer, dealerCards);
                        }
                        else if(dealerBust == 0){
                            int winner = 0;
                            try {
                                winner = game.winner(player, dealer);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            } 

                            if(winner == 1) {
                                status.setText("Dealer Win!");
                                gameEnd = true;
                                showCards(dealer, dealerCards);
                            }
                            else if(winner == 2) {
                                status.setText("Player Win!");
                                gameEnd = true;
                                showCards(dealer, dealerCards);
                            }
                            else if(winner == 0) {
                                status.setText("Push!");
                                gameEnd = true;
                                showCards(dealer, dealerCards);
                            }
                        }
                    }
                });
            }
        }

        newGamebtn.setText("New Game");
        newGamebtn.setOnAction((e) -> {
            newGame(); 
        });

    }


    public static void main(String[] args) {
        launch(args);

    }
}
