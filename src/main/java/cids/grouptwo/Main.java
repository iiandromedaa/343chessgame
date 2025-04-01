package cids.grouptwo;

import cids.grouptwo.pieces.Bishop;
import cids.grouptwo.pieces.King;
import cids.grouptwo.pieces.Knight;
import static cids.grouptwo.pieces.Piece.Color.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        /* creates the backend board data */
        Board board = new Board();
        
        /* creating some pieces, doesnt have to be used and can be modified */
        board.setPiece(new King(WHITE, 4, 0));
        board.setPiece(new King(BLACK, 4, 7));
        board.setPiece(new Bishop(WHITE, 2, 0));
        board.setPiece(new Bishop(BLACK, 2, 7));
        board.setPiece(new Knight(WHITE, 1, 0));
        board.setPiece(new Knight(BLACK, 1, 7));

        board.displayBoard();
    }

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
