package cids.grouptwo;

import java.util.List;

import cids.grouptwo.exceptions.BoardException;
import cids.grouptwo.exceptions.FenParseException;
import cids.grouptwo.pieces.Pawn;
import cids.grouptwo.pieces.Piece;

import static cids.grouptwo.pieces.Piece.Color.*;

public class Board {

    private Piece[][] board;

    private final int BOARDPARAMS = 8;

    /**
     * creates standard chess board
     */
    public Board() {
        board = new Piece[BOARDPARAMS][BOARDPARAMS];
        
    }

    /**
     * creates board with set state from FEN notation
     * <p>call using null as parameter for empty board
     * @param fen FEN notation
     */
    public Board(String fen) {
        board = new Piece[BOARDPARAMS][BOARDPARAMS];
        if (fen == null)
            return;
        try {
			setBoard(FenParse.parse(fen));
		} catch (BoardException | FenParseException e) {
			e.printStackTrace();
		}
    }

    /**
     * displays chess board, only for debug purposes, shows only from
     * black's perspective
     */
    public void displayBoard() {
        Main.clear();
        for (int y = 0; y < BOARDPARAMS; y++) {
            for (int x = 0; x < BOARDPARAMS; x++) {
                if (board[y][x] == null)
                    System.out.print("[ ]");
                else
                    System.out.print("[" + board[y][x].toString() + "]");
            }
            System.out.println();
        }
    }

    public void displayMoves(List<Coordinate> coordinates) {
        coordinates.forEach(coordinate -> 
            board[coordinate.Y][coordinate.X] = new Pawn(DEBUG, coordinate.X, coordinate.Y) {
            @Override
            public String toString() {
                return "*";
            }
        });
        displayBoard();
        for (int y = 0; y < BOARDPARAMS; y++) {
            for (int x = 0; x < BOARDPARAMS; x++) {
                if (board[y][x] != null && (board[y][x].getColor() == DEBUG))
                    board[y][x] = null;
            }
        }
        
    }

    public Piece[][] getBoard() {
        return board;
    }

    /**
     * CAN RETURN NULL, BE CAREFUL NOT TO CAUSE RUNTIME EXCEPTIONS!!!!!!!
     * @param x
     * @param y
     * @return piece at xy position, or null
     */
    public Piece getPieceFromXY(int x, int y) {
        return getPieceFromCoordinate(new Coordinate(x, y));
    }

    /**
     * CAN RETURN NULL, BE CAREFUL NOT TO CAUSE RUNTIME EXCEPTIONS!!!!!!!
     * @param coordinate
     * @return piece at coordinate position, or null
     */
    public Piece getPieceFromCoordinate(Coordinate coordinate) {
        return board[coordinate.Y][coordinate.X];
    }

    /**
     * manually sets piece on board, overwriting what was in that space before<p>
     * use with caution
     * @param piece
     */
    public void setPiece(Piece piece) {
        board[piece.getY()][piece.getX()] = piece;
    }

    private void setBoard(Piece[][] board) throws BoardException {
        if (board.length != this.board.length)
            throw new BoardException();
        this.board = board;
    }

}