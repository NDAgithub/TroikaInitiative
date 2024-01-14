package gameElements;

// imports
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import settings.Settings;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

// the Character class stores the name and color chosen for the Character, as well as the JPanel which appears in SetupWindow to alter Character name and color
public class Character {
    private String name;
    private Color color;
    private boolean delete;
    private JPanel panel;
    private JTextArea nameText;
    private JButton colButton, delButton;
    private ActionListener actionListener;

    // constructor
    public Character(String charName, Color charColor) {
        name = charName;
        color = charColor;
        delete = false;

        panel = new JPanel();
        panel.setBackground(color);
        panel.setMinimumSize(new Dimension(Settings.CHAR_PANEL_WIDTH, Settings.CHAR_PANEL_HEIGHT));

        nameText = new JTextArea(charName, 1, 10);

        // whenever a char is added or removed from the Character's name, update the name of the Character
        // this avoids the need for a button or dialog to confirm name changes, since the name updates with every char change
        nameText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setName(nameText.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                setName(nameText.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                //doesn't seem to do anything
            }
            
        });
        panel.add(nameText);

        // adding an action listener to the class so that the buttons on the JPanel work
        actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPress(e);
            }    
        };

        // creating/adding buttons to panel
        colButton = new JButton("Color");
        colButton.setBackground(Color.WHITE);
        colButton.setMaximumSize(new Dimension(Settings.DELETE_BUTTON_WIDTH, Settings.DELETE_BUTTON_HEIGHT));
        colButton.addActionListener(actionListener);
        panel.add(colButton);

        delButton = new JButton("Delete");
        delButton.setBackground(Color.RED);
        delButton.setMaximumSize(new Dimension(Settings.DELETE_BUTTON_WIDTH, Settings.DELETE_BUTTON_HEIGHT));
        delButton.addActionListener(actionListener);
        panel.add(delButton);
    }

    // function to handle button press events
    private void buttonPress(ActionEvent e) {
        String ac = e.getActionCommand();
        // delete button functionality
        if (ac.equals("Delete")) {
            panel.setBackground(Color.RED);
            delete();
        }
        // color button functionality
        else if (ac.equals("Color")){
            color = JColorChooser.showDialog(null, "Choose player color", Color.WHITE);
            panel.setBackground(color);
        }
    }

    // sets the Character's delete value to true - used only by buttonPress() to mark the Character as ready to be removed
    private void delete() {
        delete = true;
    }

    // returns the name of the Character
    public String getName() {
        return name;
    }

    // returns the color of the Character
    public Color getColor() {
        return color;
    }

    // sets the name of the Character
    public void setName(String newName) {
        name = newName;
    }

    // sets the color of the Character
    public void setColor(Color newColor) {
        color = newColor;
        panel.setBackground(newColor);
    }

    // returns whether the Character should be deleted or not
    public boolean getDelete() {
        return delete;
    }

    // returns the JPanel for the Character to be displayed in the SetupWindow
    public JPanel getPanel() {
        return panel;
    }
}
