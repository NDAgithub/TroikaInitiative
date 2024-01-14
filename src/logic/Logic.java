package logic;

// imports 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.*;

import java.awt.*;
import gameElements.Token;
import settings.Settings;
import gameElements.Character;

public class Logic {
    private int numEnemies, numHenchmen, numRound = 0, numTurn = 0;
    private ArrayList<Token> tokenList;
    private ArrayList<Character> charList;
    private boolean enemyLeft = true;
    private boolean playerLeft = true;

    // constructor - assigns values to class variables and then starts a new round of initiative
    public Logic(ArrayList<Character> characterList, int numOfEnemies, int numOfHenchmen) {
        charList = characterList;
        numEnemies = numOfEnemies;
        numHenchmen = numOfHenchmen;

        newRound();
    }

    // Sets up a round of initiative
    public void newRound() {
        numRound++;
        numTurn = 0;
        tokenList = new ArrayList<Token>();

        // add players to initiative
        Iterator<Character> charIter = charList.iterator();
        while (charIter.hasNext()) {
            Character c = charIter.next();
            for (int i = 0; i < Settings.TURNS_PER_CHARACTER; i++) {
                tokenList.add(new Token(c.getName(), c.getColor(), true, true, false));
            }
        }

        // add enemies to initiative
        for (int i = 0; i < numEnemies; i++) {
            tokenList.add(new Token("Enemy", Settings.REMOVE_BUTTON_COLOR, false, false, false));
        }

        // add henchmen to initiative
        for (int i = 0; i < numHenchmen; i++) {
            tokenList.add(new Token("Henchman", Color.lightGray, false, true, false));
        }

        // add end of round token
        tokenList.add(new Token("END OF ROUND", Color.BLACK, false, false, true));

        // shuffle the initiative order
        Collections.shuffle(tokenList);
    }

    // returns the next Token in initiative
    public Token nextTurn() {
        // if initiative has been won or lost by the players, return a special end of game Token
        if (isOver()) return new Token("GAME OVER", Color.black, false, false, true);

        numTurn++;
        Token t = tokenList.get(0);
        tokenList.remove(0);
        return t;
    }

    // returns what round of initiative is ongoing
    public int getRound() {
        return numRound;
    }

    // returns what turn of the current round of initiative is ongoing
    public int getTurn() {
        return numTurn;
    }

    // method to remove a player character from initiative from given character's name
    public void removeCharacter(String name, JPanel panel) {
        // if a character's name matches the given name to be removed, remove it from charList and iterate through tokenList to remove instances of it from the rest of the iniative round
        Iterator<Character> charIterator = charList.iterator();
        while (charIterator.hasNext()) {
            Character c = charIterator.next();
            if (c.getName().equals(name)) {
                charList.remove(c);
                charTokenRemove(name);
                validate();
                return;
            }
        }

        // only to be printed if no character is found with the given name to be removed
        JOptionPane.showMessageDialog(panel, "No character found to be removed with the given name ".concat(name));
    }

    // removes character's tokens from the tokenList
    private void charTokenRemove(String name) {
        Iterator<Token> tokenIterator = tokenList.iterator();

        ArrayList<Token> newTokenList = new ArrayList<Token>();
        while (tokenIterator.hasNext()) {
            Token t = tokenIterator.next();
            if (!t.getName().equals(name)) {
                newTokenList.add(t);
            }
        }

        tokenList = newTokenList;
    }

    // method to remove a number of tokens from enemy initiative
    public void removeEnemy(int x) {
        numEnemies -= x;
        enemyTokenRemove(x);
        validate();
    }

    // method to remove a number of tokens from henchmen initiative
    public void removeHenchmen (int x) {
        numHenchmen -= x;
        henchmenTokenRemove(x);
        validate();
    }

    // removes x amount of henchmen tokens
    private void henchmenTokenRemove(int x) {
        int numToRemove = x;
        ArrayList<Token> newTokenList = (ArrayList<Token>) tokenList.clone();
        Collections.reverse(newTokenList);
        ArrayList<Token> newReversedList = new ArrayList<Token>();
        
        // if a token is friendly but is not player controlled, is is a henchman; remove it from initiative and decrement numToRemove by 1
        // if all x henchmen have been removed from the current round of initiative (numToRemove = 0), do not remove any more henchmen
        Iterator<Token> newTokenIterator = newTokenList.iterator();
        while (newTokenIterator.hasNext()) {
            Token t = newTokenIterator.next();
            if (numToRemove > 0) {
                if (!t.isFriendly() || t.isPlayerControlled()) {
                    newReversedList.add(t);
                }
                else {
                    numToRemove--;
                }
            }
            else {
                newReversedList.add(t);
            }
        }

        Collections.reverse(newTokenList);
        tokenList = newTokenList;
    }

    // removes x amount of enemy tokens 
    private void enemyTokenRemove (int x) {
        int numToRemove = x;
        ArrayList<Token> newTokenList = (ArrayList<Token>) tokenList.clone();
        Collections.reverse(newTokenList);
        ArrayList<Token> newReversedList = new ArrayList<Token>();
        
        // if the token is not an enemy, add it to the new list; else, decrement the numToRemove by one
        // if all x enemies have been removed from the current round of initiative (numToRemove = 0), do not remove any more enemies
        Iterator<Token> newTokenIterator = newTokenList.iterator();
        while (newTokenIterator.hasNext()) {
            Token t = newTokenIterator.next();
            if (numToRemove > 0) {
                if (!t.isEnemy()) {
                    newReversedList.add(t);
                }
                else {
                    numToRemove--;
                }
            }
            else {
                newReversedList.add(t);
            }
        }

        Collections.reverse(newReversedList);
        tokenList = newReversedList;
    }

    // adds x henchmen tokens to the next round of initiative
    public void addHenchmen(int x) {
        numHenchmen += x;
    }

    // adds x enemy tokens to the next round of initiative
    public void addEnemy(int x) {
        numEnemies += x;
    }

    // method to check if initiative is still ongoing based on runtime variables
    // returns true if there are still enemies and characters alive in initiative
    // returns false if there are either no enemies or player characters
    private boolean validate() {
        // if all enemies or all players are gone: enemyLeft = false, return false
        if (numEnemies < 1) {
            enemyLeft = false;
            return false;
        }
        // if the all the characters are gone, playerleft = false, return false
        if (charList.size() < 1) {
            playerLeft = false;
            return false;
        }

        // else, return true
        return true;
    }

    // returns true if iniative is over, false if not
    public boolean isOver() {
        return !enemyLeft || !playerLeft;
    }

    // returns true if there are enemies left, false if not
    public boolean enemyLeft() {
        return enemyLeft;
    }

    // returns true if there are players left, false if not
    public boolean playerLeft() {
        return playerLeft;
    }

}
