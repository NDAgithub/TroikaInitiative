package windowElements;

// imports
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

import settings.Settings;
import gameElements.Character;
import fileHandling.FileAccessor;

// SetupWindow is the screen that displays when the program is first launched which allows the user to add player Characters and change the number of both enemy and henchmen tokens
public class SetupWindow extends JPanel implements ActionListener {
    // variables
    private JPanel topPanel, midPanel, bottomPanel;
    private JButton addButton, startButton;
    private JTextField enemyText, henchmenText;
    private ArrayList<Character> characterList;
    private JLabel startLabel = new JLabel("START");
    private FileAccessor fileAccessor;

    // constructor
    public SetupWindow() {
        fileAccessor = new FileAccessor();

        // JPanel setup - SetupWindow uses a topPanel, a midPanel and a bottomPanel
        // topPanel contains the button to add new player Characters and change both enemy and henchmen initiative token numbers
        // midPanel contains the area where player Characters are configured
        // bottomPanel contains the button to move out of SetupWindow into InitWindow, starting initiative
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // topPanel
        topPanel = new JPanel();
        topPanel.setMaximumSize(new Dimension(Settings.WINDOW_SIZE_X, Settings.TOP_PANEL_HEIGHT));
        topPanel.setBackground(Color.lightGray);
        add(topPanel);

        // midPanel
        midPanel = new JPanel();
        midPanel.setMaximumSize(new Dimension(Settings.WINDOW_SIZE_X, Settings.WINDOW_SIZE_Y - 2*Settings.BOTTOM_PANEL_HEIGHT));
        midPanel.setBackground(Color.lightGray);
        add(midPanel);

        // bottomPanel
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setMaximumSize(new Dimension(Settings.WINDOW_SIZE_X, Settings.BOTTOM_PANEL_HEIGHT));
        bottomPanel.setBackground(Color.lightGray);
        add(bottomPanel);

        addButton = new JButton("ADD CHARACTER");
        addButton.addActionListener(this);
        addButton.setBackground(Color.GRAY);
        addButton.setMaximumSize(new Dimension(Settings.ADD_BUTTON_WIDTH, Settings.ADD_BUTTON_HEIGHT));
        topPanel.add(addButton);

        startButton = new JButton("START INITIATIVE");
        startButton.setBackground(Color.GREEN);
        startButton.setMaximumSize(new Dimension(Settings.START_BUTTON_WIDTH, Settings.START_BUTTON_HEIGHT));
        startButton.addActionListener(this);
        bottomPanel.add(startButton, BorderLayout.LINE_END);

        enemyText = new JTextField("2", 5);
        henchmenText = new JTextField("0", 5);

        topPanel.add(new JLabel("Enemy Initiative"));
        topPanel.add(enemyText);
        topPanel.add(new JLabel("Henchman Initiative"));
        topPanel.add(henchmenText);

        // the mouseListener calls the refresh function when the cursor leaves the Delete button on a character to remove it from the midPanel
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                refresh();
            }

            @Override
            public void mouseExited(MouseEvent e) {}

            
        });

        setVisible(true);

        characterList = new ArrayList<Character>();

        // the last values which were used when initiative was started (enemy/henchmen init and Character info) are loaded from the save file into the SetupWindow
        loadLastValues();

        revalidate();
        repaint();
    }

    // button handlers
    @Override
    public void actionPerformed(ActionEvent e) {
        String ac = e.getActionCommand();
        // adding a Character
        if (ac.equals("ADD CHARACTER")) {
            addCharacter();
            revalidate();
        }
        // starting initiative - adds a label which GameWindow is listening for to change the window to InitWindow
        // saves the current game settings (enemy/henchmen init and Character data) to the save file
        else if (ac.equals("START INITIATIVE")){
            if (!characterList.isEmpty() && Integer.parseInt(enemyText.getText()) > 0) {
                fileAccessor.saveFile(characterList, Integer.parseInt(enemyText.getText()), Integer.parseInt(henchmenText.getText()));
                add(startLabel);
            }
            else {
                JOptionPane.showMessageDialog(topPanel, "Needs a character and enemies to start");
            }
        }
    }

    // sets variable data based on the last time the program was run
    private void loadLastValues() {
        enemyText.setText(Integer.toString(fileAccessor.getEnemyInit()));
        henchmenText.setText(Integer.toString(fileAccessor.getHenchmenInit()));
        characterList = fileAccessor.getCharacters();

        // adds the Character data from the save file to the screen 
        Iterator<Character> charIter = characterList.iterator();
        while (charIter.hasNext()) {
            addCharToScreen(charIter.next());
        }
    }

    // function called to create a new Character
    private void addCharacter() {
        Character newChar = new Character("Default", Color.GRAY);
        addCharToScreen(newChar);
        characterList.add(newChar);
    }

    // function called to add a Character's JPanel to the midPanel
    private void addCharToScreen(Character newChar) {
        midPanel.add(newChar.getPanel());
    }

    // called on mouse movement in the GameWindow to remove deleted players 
    public void refresh() {

        Iterator<Character> charIterate = characterList.iterator();
        // if character is supposed to be deleted, remove its panel from the SetupWindow
        while (charIterate.hasNext()){
            Character character = charIterate.next();
            if (character.getDelete()) {
                midPanel.remove(character.getPanel());
            }
        }
        characterList.removeIf(character -> (character.getDelete()));
        
        repaint();
    }

    // returns ArrayList of player Characters found in midPanel
    public ArrayList<Character> getCharacters() {
        return characterList;
    }

    // returns number of henchmen Tokens specified in the henchmen init textfield 
    public int getHenchmen() {
        return Integer.parseInt(henchmenText.getText());
    }

    // returns number of enemy Tokens specified in the enemy init textfield
    public int getEnemies() {
        return Integer.parseInt(enemyText.getText());
    }
}
