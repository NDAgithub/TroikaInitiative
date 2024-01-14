package settings;

// imports
import java.awt.Color;
import java.awt.Font;
import java.io.File;

// provides set variables which are used across the program
public class Settings  {
    // window and panel sizes
    public static final int WINDOW_SIZE_X = 1080; 
    public static final int WINDOW_SIZE_Y =620;
    public static final int TOP_PANEL_HEIGHT = 40;
    public static final int BOTTOM_PANEL_HEIGHT = 75;
    public static final int CHAR_PANEL_WIDTH = 280;
    public static final int CHAR_PANEL_HEIGHT = 150;
    public static final int SIDE_PANEL_WIDTH = 150;

    // button settings
    public static final int ADD_BUTTON_WIDTH = 250;
    public static final int ADD_BUTTON_HEIGHT = 80;
    public static final int START_BUTTON_WIDTH = 250;
    public static final int START_BUTTON_HEIGHT = 80;
    public static final int DELETE_BUTTON_WIDTH = 85;
    public static final int DELETE_BUTTON_HEIGHT = 40;

    public static final Color REMOVE_BUTTON_COLOR = new Color(247, 60, 60);
    public static final Color ADD_BUTTON_COLOR = new Color(60, 247, 60);
    
    // Character name settings
    public static final int CHAR_NAME_TEXTBOX_WIDTH = 40;
    public static final int CHAR_NAME_TEXTBOX_HEIGHT = 15;
    public static final int DISPLAY_CHAR_NAME_WIDTH = 300;
    public static final int DISPLAY_CHAR_NAME_HEIGHT = 150;
    
    // number of tokens added to initiative per character - the Troika manual specifies 2 but it can be adjusted here
    public static final int TURNS_PER_CHARACTER = 2;

    // save file path
    public static final String LOADING_FILEPATH = "src" + File.separator + "fileHandling" + File.separator + "lastSetup.txt";

    // fonts
    public static final Font ROUND_FONT = new Font("SansSerif", Font.BOLD, 48);
    public static final Font CHAR_FONT = new Font("SansSerif", Font.BOLD, 40);
    public static final Font PC_FONT = new Font("SansSerif", Font.BOLD, 24);
    public static final Font SIDE_PANEL_FONT = new Font("SansSerif", Font.BOLD, 16);
};
