package fileHandling;

// imports
import gameElements.Character;
import settings.Settings;

import java.util.ArrayList;
import java.util.Scanner;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class FileAccessor {
    private String filepath = Settings.LOADING_FILEPATH;
    private File file;
    private Scanner scanner;
    private FileWriter fileWriter;
    private ArrayList<Character> charList = new ArrayList<Character>();
    private int enemyInit, henchmenInit;
    
    // constructor, runs loadFile() so long as filepath is valid
    public FileAccessor() {
        try {
            file = new File(filepath).getCanonicalFile();
        }
        catch (IOException e) {
            System.out.println("file issue: filepath IO exception");
            e.printStackTrace();
        }
        
        loadFile();
    }

    // stores information of enemy and henchmen initiative + player Characters in class variables to be retrieved by getter methods
    public void loadFile() {
        try {
            scanner = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            System.out.println("scanner issue: File not found");
            e.printStackTrace();
        }

        int counter = 0;
        while (scanner.hasNextLine()) {
            if (counter == 0) {
                enemyInit = Integer.parseInt(scanner.nextLine());
                counter++;
            }
            else if (counter == 1) {
                henchmenInit = Integer.parseInt(scanner.nextLine());
                counter++;
            }
            else {
                String charLine = scanner.nextLine();
                String[] charSplit = charLine.split(" ");
                charList.add(new Character(charSplit[0], Color.decode(charSplit[1])));
            }
        }
    }

    // saves the given initiative data (Characters, number of enemies and henchmen) to a file
    public void saveFile(ArrayList<Character> characterList, int numEnemies, int numHenchmen) {
        try {
            fileWriter = new FileWriter(file, false);

            String toWrite = new String();

            // adding enemy initiative to file
            toWrite = toWrite.concat(Integer.toString(numEnemies) + "\n");
            // adding henchmen initiative to file
            toWrite = toWrite.concat(Integer.toString(numHenchmen) + "\n");
            // adding Characters to file
            charList = characterList;
            Iterator<Character> charIter = characterList.iterator();
            while (charIter.hasNext()) {
                Character c = charIter.next();
                toWrite = toWrite.concat(c.getName() + " " + Integer.toString(c.getColor().getRGB()) + "\n");
            }

            fileWriter.write(toWrite);
            fileWriter.close();
        }
        catch (IOException e) {
            System.out.println("fileWriter IO exception");
            e.printStackTrace();
        }
    }

    // returns the stored ArrayList of the Character objects
    public ArrayList<Character> getCharacters() {
        return charList;
    }

    // returns the stored number of enemy tokens in initiative
    public int getEnemyInit() {
        return enemyInit;
    }

    // returns the stored number of henchmen tokens in initiative
    public int getHenchmenInit() {
        return henchmenInit;
    }
}
