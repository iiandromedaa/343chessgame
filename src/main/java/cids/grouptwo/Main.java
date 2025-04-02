package cids.grouptwo;

import cids.grouptwo.pieces.*;

import static cids.grouptwo.pieces.Piece.Color.*;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        /* creates the backend board data */
        Board board = new Board();
        
        /* creating some pieces, doesnt have to be used and can be modified */
        board.setPiece(new Pawn(WHITE, 2, 0));

        board.setPiece(new Pawn(BLACK, 4, 6));
        board.setPiece(new Pawn(WHITE, 5, 5));
        board.setPiece(new Pawn(WHITE, 3, 5));
        board.setPiece(new Pawn(WHITE, 4, 5));

        board.setPiece(new Pawn(WHITE, 1, 3));

        board.setPiece(new Pawn(WHITE, 5, 2));

        board.setPiece(new Bishop(WHITE, 2, 4));
        // board.setPiece(new Rook(WHITE, 4, 4));

        board.displayBoard();
        new Scanner(System.in).nextLine();
        board.displayMoves(board.getPieceFromXY(4, 6).getValidMoves(board.getBoard()));
        // System.out.println(board.getPieceFromXY(4, 6).isValidMove(4, 4, board.getBoard()));
        // System.out.println(board.getPieceFromXY(1, 6).getValidMoves(board.getBoard()));
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
