package cids.grouptwo;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {

    public static void main(String[] args) {
        // uncomment this to launch the new libgdx window, it just draws a circle to the
        // center of the screen
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        new Lwjgl3Application(new Chessgame(), config);

        System.out.println("hi world hello world hii!!!!!");

        Board board = new Board();
        board.displayBoard();

        // /* JackH ~ creates 3 piece types for the board of each color */
        // Piece bKing = new Piece(0, 3, "black", "king"); // black king
        // Piece bKnightL = new Piece(0, 1, "black", "knight"); // black knight left
        // Piece bKnightR = new Piece(0, 6, "black", "knight"); // black knight right
        // Piece bBishopL = new Piece(0, 2, "black", "bishop"); // black bishop left
        // Piece bBishopR = new Piece(0, 5, "black", "bishop"); // black bishop right
        // Piece wKing = new Piece(7, 3, "white", "king"); // white king
        // Piece wKnightL = new Piece(7, 1, "white", "knight"); // white knight left
        // Piece wKnightR = new Piece(7, 6, "white", "knight"); // white knight right
        // Piece wBishopL = new Piece(7, 2, "white", "bishop"); // white bishop left
        // Piece wBishopR = new Piece(7, 5, "white", "bishop"); // white bishop right
    }

    // This is Jack's comment for the jackh branch

}
