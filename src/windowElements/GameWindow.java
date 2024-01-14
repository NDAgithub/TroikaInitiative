package windowElements;

// imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import windowElements.SetupWindow;
import logic.Logic;
import settings.Settings;

// GameWindow is the window that contains the project, and it is filled by either SetupWindow or InitWindow
public class GameWindow extends Frame {
    // variables
    private InitWindow initWindow;
    private SetupWindow setupWindow;
    private boolean changeToInit = true;
    private Logic logic;
    
    // constructor + setting settings
    // applies SetupWindow panel to GameWindow frame on startup 
    public GameWindow() {
        super("Troika Initiative Tracker");
        setSize(Settings.WINDOW_SIZE_X, Settings.WINDOW_SIZE_Y);
        this.setResizable(false);

        applySetupPanel();
        
        //setupWindow.refresh();
        addWindowListener(new ExitListener());
    
        setVisible(true);
    }

    // ExitListener
    class ExitListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    // function to change the panel on screen between the SetupWindow and InitWindow
    public void changePanel() {
        // when changing to InitWindow, create a new Logic class with the user settings from SetupWindow and apply the new InitWindow with that Logic class to GameWindow
        if (changeToInit) {
            logic = new Logic(setupWindow.getCharacters(), setupWindow.getEnemies(), setupWindow.getHenchmen());
            remove(setupWindow);
            applyInitPanel(logic);
        }
        // changing to SetupWindow 
        else {
            remove(initWindow);
            applySetupPanel();
        }
        changeToInit = !changeToInit;
        revalidate();
    }

    // function to create a new SetupWindow and apply it to the GameWindow frame
    // a listener is added so that when the START button is pressed inside SetupWindow, GameWindow changes the panel to InitWindow
    private void applySetupPanel() {
        setupWindow = new SetupWindow();

        setupWindow.addContainerListener(new ContainerListener() {

            @Override
            public void componentAdded(ContainerEvent e) {
                if (((JLabel)e.getChild()).getText().equals("START")) {
                    
                    changePanel();
                }
            }

            @Override
            public void componentRemoved(ContainerEvent e) {

            }
            
        });
        
        add(setupWindow);
    }

    // function to create a new InitWindow and apply it to the GameWindow frame
    // a listener is added so that when the RETURN button is pressed inside InitWindow, GameWindow changes the panel to SetupWindow
    private void applyInitPanel(Logic logic) {
        initWindow = new InitWindow(logic);

        // adding the listener so that when the user tries to return from initWindow to the setupwindow it will be heard
        initWindow.addContainerListener(new ContainerListener() {

            @Override
            public void componentAdded(ContainerEvent e) {
                if (((JLabel)e.getChild()).getText().equals("RETURN")) {
                    changeToInit = false;
                    changePanel();
                }
            }

            @Override
            public void componentRemoved(ContainerEvent e) {

            }
            
        });

        add(initWindow);
    }


}
