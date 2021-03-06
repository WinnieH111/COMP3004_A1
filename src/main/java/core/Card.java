package core;

import java.util.regex.Pattern;
import javafx.scene.image.Image;

public class Card {
    
    private String suit = null;
    private String rank = null;
    
    Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }
    
    public String getSuit(){
        return suit;
    }
    
    public String getRank() {
        return rank;
    }
    
    public String getCardString() {
        return suit+rank;
    }
    
    public int getValue() {
        int value = 0;
        if(rank.equals("J")
                ||rank.equals("Q")
                ||rank.equals("K")) {
            value = 10;
        }
        else if(rank.equals("A")) {
            value = 1000;
        }
        else if (isIntRank(rank)) {
            value = Integer.parseInt(rank);
        }
        else {
            throw new IllegalArgumentException("Please check your input file and try again");
        }
        return value;
    }
    
    public static boolean isIntRank(String rank) {
        Pattern pattern = Pattern.compile("[0-9]|10");
        return pattern.matcher(rank).matches();
    }
    
    public boolean isValidCard() {
        Pattern pattern = Pattern.compile("([DCHS])([2-9]|1[0]|[AJQK])$");
        return pattern.matcher(getCardString()).matches();
    }
    
    
    public String validAction() throws Exception {
        boolean actionPattern = false;
        Pattern pattern = Pattern.compile("[DSH]");
        actionPattern = pattern.matcher(getCardString()).matches();
        if(actionPattern) {
            return suit;
        }
        else {
            throw new Exception("Not a legal action instruction. Game terminated...");
        }
    }
    
    //FOR GUI
    public Image getCardFace() {
        return new Image(cardFilePath());
    } 
    
    public String cardFilePath () {
        if(getRank().equals("10")) {
            return "file:src/main/resources/cards/" + "t" + getSuit().toLowerCase() + ".gif";
        }
        return "file:src/main/resources/cards/" + getRank().toLowerCase() + getSuit().toLowerCase() + ".gif";
    }
    
    public Image getCardBack() {
        return new Image("file:src/main/resources/cards/b.gif");
    }
}
