package com.freedsuniverse.sidecraft;

import java.io.File;

public class Settings {
    //Modify-able settings
    public static int BLOCK_SIZE = 32;
    public static boolean DEBUG = false;
    public static boolean SOUND = true;
    
    //controls
    //public static Key INVENTORY_TOGGLE = Keys.I;
    
    //Directions
    public final static int LEFT = -1;
    public final static int RIGHT = 1;
    public final static int UP = 1;
    public final static int DOWN = -1;
    
    //Misc.
    public final static String VERSION = "Indev 0.2";
    
    //Files
    public final static String SAVE_DIR = System.getenv("USERPROFILE")+ File.separator + "AppData" + File.separator + "Roaming" + File.separator + ".sidecraft" + File.separator;
    public final static String TEXTURE_KEYWORD = "texture/";
    public final static String MAIN_DIR = "/";
    public final static String PLAYER_DIR = "/player/";
    public final static String ENTITY_DIR = "/entity/";
    public final static String MATERIAL_DIR = "/material/";
    public static final String MENU_BACKGROUND = "/misc/menu/background.png";
    public static final String MENU_BACKGROUND_TILE = "/misc/menu/backgroundTile.png";
    public static final String PAUSED_BACKGROUND = "/misc/pause/backgroundTile.png";
    private static String DEFAULT_DIR = "-1";
    
    public static String defaultDirectory(){
        if(DEFAULT_DIR.equals("-1")) {
            String OS = System.getProperty("os.name").toUpperCase();
            if (OS.contains("WIN"))
                DEFAULT_DIR = System.getenv("APPDATA") + File.separator + ".sidecraft" + File.separator;
            else if (OS.contains("MAC"))
                DEFAULT_DIR = System.getProperty("user.home") + "/Library/Application " + "Support" + File.separator + ".sidecraft" + File.separator;
            else if (OS.contains("NUX"))
                DEFAULT_DIR = System.getProperty("user.home") + File.separator + ".sidecraft" + File.separator;
            else
                DEFAULT_DIR =  System.getProperty("user.dir") + File.separator + ".sidecraft" + File.separator;
        }
        return DEFAULT_DIR;
    }
}
