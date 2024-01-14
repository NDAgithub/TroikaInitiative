package gameElements;

// imports
import java.awt.*;

// in Troika, Characters, enemies and henchmen all have initiative Tokens which are pulled randomly from a bag
// Tokens store basic information about whose turn it is 
// the End of Round token signifies the start of a new round of initiative
public class Token {
    // variables
    private String tokenName;
    private Color tokenColor;
    private boolean isPlayerControlled;
    private boolean isFriendly;
    private boolean isEndOfRound = false;

    // constructor
    public Token(String name, Color color, boolean player, boolean friendly, boolean endRound){
        tokenName = name;
        tokenColor = color;
        isPlayerControlled = player;
        isFriendly = friendly;
        if (endRound) {isEndOfRound=true;}
    }

    // getter methods
    public String getName() {
        return tokenName;
    }

    public Color getColor() {
        return tokenColor;
    }

    public boolean isPlayerControlled() {
        return isPlayerControlled;
    }

    public boolean isEndOfRound() {
        return isEndOfRound;
    }

    public boolean isEnemy() {
        if (tokenName.equals("Enemy")) return true;
        else return false;
    }

    public boolean isFriendly() {
        return isFriendly;
    }
}


