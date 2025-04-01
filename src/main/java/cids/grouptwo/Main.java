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

        /* variables for black and white player 
         * ngl I want to encapsulate these somewhere else but
         * its not a priority right now for me
         * TODO: betcha cant guess ma dude
        */
        String pBlack = "black";
        String pWhite = "white";

        /* creates the backend board data */
        Board board = new Board();
        board.displayBoard();

        /* creating some pieces, doesnt have to be used and can be modified */
        Piece whiteKing = new King(pWhite, 4, 0);
        Piece blackKing = new King(pBlack, 4, 7);
        Piece whiteBishop = new Bishop(pWhite, 2, 0);
        Piece blackBishop = new Bishop(pBlack, 2, 7);
        Piece whiteKnight = new Knight(pWhite, 1, 0);
        Piece blackKnight = new Knight(pBlack, 1, 7);

        System.out.println("goobed");
    }

    // This is Jack's comment for the jackh branch

}
