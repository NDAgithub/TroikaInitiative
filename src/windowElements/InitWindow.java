package windowElements;

// imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import settings.Settings;
import gameElements.*;
import logic.Logic;

// the InitWindow provides the actual function of the program, allowing the user to pick the next initiative token, as well as removing tokens from initiative
public class InitWindow extends JPanel implements ActionListener {
    private JPanel topPanel, bottomPanel, mainPanel, sidePanel, currentPanel;
    private JButton nextTurnButton, returnButton, enemyRemoveButton, enemyAddButton, charRemoveButton, henchmenRemoveButton, henchmenAddButton;
    private final JLabel returnLabel = new JLabel("RETURN"), 
                         enemyLabel = new JLabel("Enter number of enemy init. to be removed"), 
                         enemyAddLabel = new JLabel("Enter number of enemy init. to be added"),
                         charLabel = new JLabel("Enter dead character name"),
                         henchmenLabel = new JLabel("Enter number of henchmen init to be removed"),
                         henchmenAddLabel = new JLabel("Enter number of henchmen init to be added");
    private JLabel playerUpdateText = new JLabel();
    private JTextField enemyRemoveText, enemyAddText, charRemoveText, henchmenRemoveText, henchmenAddText;
    private boolean firstTurn = true;
    private Logic logic;

    // constructor
    public InitWindow(Logic l) {
        // sets the Logic unit for the InitWindow to the one passed in by GameWindow using the settings the user entered in the SetupWindow
        logic = l;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // JPanel setup - InitWindow uses a topPanel (comprised of a main and side panel) and a bottomPanel 
        // mainPanel displays the information of the current Token drawn in initiative
        // sidePanel allows the player to remove Character, enemy and henchmen tokens from initiative, as well as adding enemy and henchmen tokens
        // bottomPanel allows the player to return to SetupWindow or pick the next Token for initiative
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setMaximumSize(new Dimension(Settings.WINDOW_SIZE_X, Settings.WINDOW_SIZE_Y - Settings.BOTTOM_PANEL_HEIGHT));
        topPanel.setBackground(Color.lightGray);
        add(topPanel);

        // mainPanel
        mainPanel = new JPanel();
        mainPanel.setMaximumSize(new Dimension(Settings.WINDOW_SIZE_X - Settings.SIDE_PANEL_WIDTH, Settings.WINDOW_SIZE_Y - Settings.BOTTOM_PANEL_HEIGHT));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        topPanel.add(mainPanel, BorderLayout.CENTER);

        // sidePanel
        sidePanel = new JPanel();
        sidePanel.setMinimumSize(new Dimension(Settings.SIDE_PANEL_WIDTH, Settings.WINDOW_SIZE_Y - Settings.BOTTOM_PANEL_HEIGHT));
        sidePanel.setBackground(Color.GRAY);
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));
        topPanel.add(sidePanel, BorderLayout.EAST);

        enemyLabel.setFont(Settings.SIDE_PANEL_FONT);
        enemyLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        henchmenLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        henchmenLabel.setFont(Settings.SIDE_PANEL_FONT);
        charLabel.setFont(Settings.SIDE_PANEL_FONT);
        charLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        enemyAddLabel.setFont(Settings.SIDE_PANEL_FONT);
        enemyAddLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        henchmenAddLabel.setFont(Settings.SIDE_PANEL_FONT);
        henchmenAddLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        sidePanel.add(enemyLabel);
        enemyRemoveText = new JTextField("0", 15);
        enemyRemoveText.setMaximumSize(new Dimension(Settings.SIDE_PANEL_WIDTH, 40));
        sidePanel.add(enemyRemoveText);
        enemyRemoveButton = new JButton("Remove enemy init.");
        enemyRemoveButton.addActionListener(this);
        enemyRemoveButton.setSize(new Dimension(Settings.SIDE_PANEL_WIDTH, Settings.ADD_BUTTON_HEIGHT));
        enemyRemoveButton.setBackground(Settings.REMOVE_BUTTON_COLOR);
        enemyRemoveButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        sidePanel.add(enemyRemoveButton);

        sidePanel.add(enemyAddLabel);
        enemyAddText = new JTextField("0", 15);
        enemyAddText.setMaximumSize(new Dimension(Settings.SIDE_PANEL_WIDTH, 40));
        sidePanel.add(enemyAddText);
        enemyAddButton = new JButton("Add enemy init.");
        enemyAddButton.addActionListener(this);
        enemyAddButton.setSize(new Dimension(Settings.SIDE_PANEL_WIDTH, Settings.ADD_BUTTON_HEIGHT));
        enemyAddButton.setBackground(Settings.ADD_BUTTON_COLOR);
        enemyAddButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        sidePanel.add(enemyAddButton);

        sidePanel.add(henchmenLabel);
        henchmenRemoveText = new JTextField("0", 15);
        henchmenRemoveText.setMaximumSize(new Dimension(Settings.SIDE_PANEL_WIDTH, 40));
        sidePanel.add(henchmenRemoveText);
        henchmenRemoveButton = new JButton("Remove henchmen init.");
        henchmenRemoveButton.addActionListener(this);
        henchmenRemoveButton.setSize(new Dimension(Settings.SIDE_PANEL_WIDTH, Settings.ADD_BUTTON_HEIGHT));
        henchmenRemoveButton.setBackground(Settings.REMOVE_BUTTON_COLOR);
        henchmenRemoveButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        sidePanel.add(henchmenRemoveButton);

        sidePanel.add(henchmenAddLabel);
        henchmenAddText = new JTextField("0", 15);
        henchmenAddText.setMaximumSize(new Dimension(Settings.SIDE_PANEL_WIDTH, 40));
        sidePanel.add(henchmenAddText);
        henchmenAddButton = new JButton("Add henchmen init.");
        henchmenAddButton.addActionListener(this);
        henchmenAddButton.setSize(new Dimension(Settings.SIDE_PANEL_WIDTH, Settings.ADD_BUTTON_HEIGHT));
        henchmenAddButton.setBackground(Settings.ADD_BUTTON_COLOR);
        henchmenAddButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        sidePanel.add(henchmenAddButton);

        sidePanel.add(charLabel);
        charRemoveText = new JTextField("Dead Character", 15);
        charRemoveText.setMaximumSize(new Dimension(Settings.SIDE_PANEL_WIDTH, 40));
        sidePanel.add(charRemoveText);
        charRemoveButton = new JButton("Remove character");
        charRemoveButton.addActionListener(this);
        charRemoveButton.setSize(new Dimension(Settings.SIDE_PANEL_WIDTH, Settings.ADD_BUTTON_HEIGHT));
        charRemoveButton.setBackground(Settings.REMOVE_BUTTON_COLOR);
        charRemoveButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        sidePanel.add(charRemoveButton);

        // bottomPanel 
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setMaximumSize(new Dimension(Settings.WINDOW_SIZE_X, Settings.BOTTOM_PANEL_HEIGHT));
        add(bottomPanel);

        nextTurnButton = new JButton("Next turn");
        nextTurnButton.addActionListener(this);
        bottomPanel.add(nextTurnButton, BorderLayout.EAST);

        playerUpdateText.setHorizontalAlignment(JLabel.CENTER);
        bottomPanel.add(playerUpdateText, BorderLayout.CENTER);

        returnButton = new JButton("Return to setup");
        returnButton.addActionListener(this);
        bottomPanel.add(returnButton, BorderLayout.WEST);

        // run the nextTurn() function once setup of InitWindow is complete to start initiative
        nextTurn();

        setVisible(true);
        repaint();
    }

    // button press handler
    @Override
    public void actionPerformed(ActionEvent e) {
        String ac = e.getActionCommand();

        // return to setup button adds a label which the GameWindow is listening for to trigger the change to SetupWindow
        if (ac.equals("Return to setup")) {
            add(returnLabel);
            return;
        }
        // next turn button runs the nextTurn() function
        else if (ac.equals("Next turn")) {
            nextTurn();
            return;
        }
        // remove character button calls logic.removeCharacter() with the name typed into the Character removal textfield as an argument 
        else if (ac.equals("Remove character")) {
            String removedCharacter = charRemoveText.getText();
            logic.removeCharacter(removedCharacter, mainPanel);
            playerUpdateText.setText(removedCharacter + " has been removed from initiative.");
        }
        // remove enemy init. button calls logic.removeEnemy() with the number typed into the enemy removal textfield as an argument
        else if (ac.equals("Remove enemy init.")) {
            int enemyInitRemoval = Integer.parseInt(enemyRemoveText.getText());
            logic.removeEnemy(enemyInitRemoval);
            if (enemyInitRemoval == 1) {playerUpdateText.setText("1 enemy token removed from initiative.");}
            else {playerUpdateText.setText(enemyInitRemoval + " enemy tokens removed from initiative.");}
        }
        // remove henchmen init. button calls logic.removeHenchmen() with the number typed into the henchmen removal textfield as an argument
        else if (ac.equals("Remove henchmen init.")) {
            int henchmanInitRemoval = Integer.parseInt(henchmenRemoveText.getText());
            logic.removeHenchmen(Integer.parseInt(henchmenRemoveText.getText()));
            if (henchmanInitRemoval == 1) {playerUpdateText.setText("1 henchman token removed from initiative.");}
            else {playerUpdateText.setText(henchmanInitRemoval + " henchman tokens removed from initiative.");}
        }
        // add enemy init. to next round
        else if (ac.equals("Add enemy init.")) {
            int enemyInitAdd = Integer.parseInt(enemyAddText.getText());
            logic.addEnemy(Integer.parseInt(enemyAddText.getText()));
            if (enemyInitAdd == 1) {playerUpdateText.setText("1 enemy token added to next round.");}
            else {playerUpdateText.setText(enemyInitAdd + " enemy tokens added to next round");}
        }
        // add henchmen init. to next round
        else if (ac.equals("Add henchmen init.")) {
            int henchmenInitAdd = Integer.parseInt(henchmenAddText.getText());
            logic.addHenchmen(Integer.parseInt(henchmenAddText.getText()));
            if (henchmenInitAdd == 1) {playerUpdateText.setText("1 henchman token added to next round.");}
            else {playerUpdateText.setText(henchmenInitAdd + " henchman tokens added to next round");}
        }

        repaint();

        // if any of the changes made by the button press end initiative, automatically start the next turn so that the end of game screen will be displayed
        if (logic.isOver()) {
                nextTurn();
            }
    }

    // function to move to the next turn of initiative by simulating drawing another Token from the bag of Tokens
    public void nextTurn() {
        // set playerUpdateText to be blank
        playerUpdateText.setText(" ");

        // if first turn, don't remove currentPanel
        if (firstTurn) {
            firstTurn = false;
        }
        else {
            mainPanel.remove(currentPanel);
        }

        SimpleAttributeSet centerAttribute = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAttribute, StyleConstants.ALIGN_CENTER);

        // if initiative is over: display screen that says so, remove the action listener from the next turn button, and return
        if (logic.isOver()) {
            JPanel gameOverPanel = new JPanel();
            gameOverPanel.setLayout(new BoxLayout(gameOverPanel, BoxLayout.PAGE_AXIS));
            gameOverPanel.setBackground(Color.black);

            JTextPane endText = new JTextPane();
            endText.setText("INITIATIVE OVER");
            endText.setFont(Settings.ROUND_FONT);
            StyledDocument documentStyle = endText.getStyledDocument();
            documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);

            endText.setMaximumSize(new Dimension(Settings.DISPLAY_CHAR_NAME_WIDTH, Settings.DISPLAY_CHAR_NAME_HEIGHT));
            endText.setAlignmentX(CENTER_ALIGNMENT);
            gameOverPanel.add(endText);

            JTextPane winText = new JTextPane();
            winText.setMaximumSize(new Dimension(Settings.DISPLAY_CHAR_NAME_WIDTH, Settings.DISPLAY_CHAR_NAME_HEIGHT));
            winText.setAlignmentX(CENTER_ALIGNMENT);
            winText.setFont(Settings.PC_FONT);
            documentStyle = winText.getStyledDocument();
            documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);
            

            if (logic.playerLeft()) {
                winText.setText("Players won");
                mainPanel.setBackground(new Color(50, 128, 50));
                gameOverPanel.setBackground(new Color(50, 128, 50));
            }
            else {
                winText.setText("Players lost");
                mainPanel.setBackground(new Color(128, 50, 50));
                gameOverPanel.setBackground(new Color(128, 50, 50));
            }
            gameOverPanel.add(winText);

            mainPanel.add(gameOverPanel);
            nextTurnButton.setEnabled(false);
            nextTurnButton.setEnabled(false);
            enemyAddButton.setEnabled(false);
            enemyRemoveButton.setEnabled(false);
            henchmenAddButton.setEnabled(false);
            henchmenRemoveButton.setEnabled(false);
            charRemoveButton.setEnabled(false);
            revalidate();
            repaint();
            return;
        }

        // else, get the next Token in initiative
        Token t = logic.nextTurn();

        // create a panel called currentPanel to display the information of the Token on the mainPanel
        mainPanel.setBackground(t.getColor());
        
        currentPanel = new JPanel();
        currentPanel.setLayout(new BoxLayout(currentPanel, BoxLayout.PAGE_AXIS));
        currentPanel.setAlignmentX(CENTER_ALIGNMENT);
        currentPanel.setMinimumSize(new Dimension(Settings.WINDOW_SIZE_X - Settings.SIDE_PANEL_WIDTH, Settings.WINDOW_SIZE_Y - Settings.BOTTOM_PANEL_HEIGHT));
        currentPanel.setBackground(t.getColor());

        JTextPane roundText = new JTextPane();
        roundText.setText("ROUND " + logic.getRound() + " TURN " + logic.getTurn());
        roundText.setMaximumSize(new Dimension(Settings.DISPLAY_CHAR_NAME_WIDTH + 40, Settings.DISPLAY_CHAR_NAME_HEIGHT + 20));
        roundText.setAlignmentX(CENTER_ALIGNMENT);
        roundText.setFont(Settings.ROUND_FONT);
        StyledDocument documentStyle = roundText.getStyledDocument();
        documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);
        currentPanel.add(roundText);

        JTextPane nameText = new JTextPane();
        nameText.setText("CURRENT INITIATIVE: " + System.lineSeparator() + t.getName());
        nameText.setBackground(Color.white);
        nameText.setMaximumSize(new Dimension(Settings.DISPLAY_CHAR_NAME_WIDTH + 20, Settings.DISPLAY_CHAR_NAME_HEIGHT + 10));
        nameText.setAlignmentX(CENTER_ALIGNMENT);
        nameText.setFont(Settings.CHAR_FONT);
        nameText.setForeground(Color.BLACK);
        documentStyle = nameText.getStyledDocument();
        documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);
        currentPanel.add(nameText);

        JTextPane pcText = new JTextPane();
        pcText.setText("isPlayerControlled:" + t.isPlayerControlled());
        pcText.setMaximumSize(new Dimension(Settings.DISPLAY_CHAR_NAME_WIDTH - 60, Settings.DISPLAY_CHAR_NAME_HEIGHT -20));
        if (!t.isPlayerControlled()) {
            pcText.setBackground(Settings.REMOVE_BUTTON_COLOR);
        }
        else {
            pcText.setBackground(Color.WHITE);
        }
        pcText.setAlignmentX(CENTER_ALIGNMENT);
        documentStyle = pcText.getStyledDocument();
        documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);
        currentPanel.add(pcText);

        JTextPane friendText = new JTextPane();
        friendText.setText("isFriendly:" + t.isFriendly());
        friendText.setMaximumSize(new Dimension(Settings.DISPLAY_CHAR_NAME_WIDTH - 60, Settings.DISPLAY_CHAR_NAME_HEIGHT -20));
        if (!t.isFriendly()) {
            friendText.setBackground(Settings.REMOVE_BUTTON_COLOR);
        }
        else {
            friendText.setBackground(Color.WHITE);
        }
        friendText.setAlignmentX(CENTER_ALIGNMENT);
        documentStyle = friendText.getStyledDocument();
        documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);
        currentPanel.add(friendText);

        // if the End of Round Token is drawn, start a new round of initiative
        if (t.isEndOfRound()) {
            logic.newRound();
            nameText.setBackground(Color.white);
            pcText.setBackground(Color.black);
            friendText.setBackground(Color.black);
        }

        mainPanel.add(currentPanel);
        revalidate();
        repaint();
    }
}
