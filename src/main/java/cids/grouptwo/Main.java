package cids.grouptwo;

import java.io.IOException;
import java.util.prefs.Preferences;

import cids.grouptwo.gdx.GdxChessGame;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * just for neatness' sake, use the main method just to bootstrap
 * other things, like the libgdx
 */
public class Main {

    public static void main(String[] args) {
        ChessGame game = new ChessGame();
        // game.getBoard().defaultBoard(game.getPieceSet());
        // game.estoyLoopin();
        launchGdx(game);
    }

    private static void launchGdx(ChessGame game) {
        int w, h;
        boolean fullscreen;
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        Graphics.DisplayMode dm = Lwjgl3ApplicationConfiguration.getDisplayMode();
        Preferences prefs = Preferences.userNodeForPackage(Main.class);

        try {
            w = Integer.parseInt(prefs.get("width", "0"));
            h = Integer.parseInt(prefs.get("height", "0"));
            fullscreen = Boolean.parseBoolean(prefs.get("fullscreen", "false"));
            if (w == 0 || h == 0) {
                w = dm.width / 2;
                h = dm.height / 2;
                fullscreen = false;
            }
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
        new Lwjgl3Application(new GdxChessGame(game, h), config);
        
    }

    /**
     * Clears the console screen
     */
    public static void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                Runtime.getRuntime().exec("clear");
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            System.out.println("Clear failed :( " + e);
        }
    }
}
