package cids.grouptwo;

import java.util.prefs.Preferences;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import cids.grouptwo.gdx.ChessGame;

/**
 * just for neatness' sake, use the main method just to bootstrap
 * other things, like the libgdx
 */
public class Main {
    
    public static void main(String[] args) {
        launchGdx();
    }

    private static void launchGdx() {
        int w, h;
        boolean fullscreen;
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        Graphics.DisplayMode dm = Lwjgl3ApplicationConfiguration.getDisplayMode();
        Preferences prefs = Preferences.userNodeForPackage(Main.class);

        try {
            w = Integer.parseInt(prefs.get("width", "1280"));
            h = Integer.parseInt(prefs.get("height", "720"));
            fullscreen = Boolean.parseBoolean(prefs.get("fullscreen", "false"));
        } catch (NullPointerException | SecurityException | IllegalStateException e) {
            // if settings not found :(
            w = dm.width / 2;
            h = dm.height / 2;
            fullscreen = false;
        }

        config.setResizable(false);
        config.setWindowedMode(w, h);
        
        if (fullscreen)
            config.setFullscreenMode(dm);

        System.out.println("launching our awesome game on " + System.getProperty("os.name") + "!!");
		new Lwjgl3Application(new ChessGame(w, h), config);
    }

}
