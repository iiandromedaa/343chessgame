package cids.grouptwo;

import java.util.List;

import cids.grouptwo.exceptions.BoardException;
import cids.grouptwo.exceptions.FenParseException;
import cids.grouptwo.pieces.Pawn;
import cids.grouptwo.pieces.Piece;
import static cids.grouptwo.pieces.Piece.Color.DEBUG;

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
     * Displays chess board with proper chess notation for coordinates
     * Shows the full board with coordinate labels (a-h, 1-8)
     * Black pieces are at the top (rows 0-1), white pieces at the bottom (rows 6-7)
     */
    public void displayBoard() {
        Main.clear();
        
        // Print the header with file (column) coordinates
        System.out.println("    BLACK SIDE");
        System.out.print("  ");
        for (char file = 'a'; file <= 'h'; file++) {
            System.out.print(" " + file + " ");
        }
        System.out.println();
        
        // Print each rank with its pieces and rank number
        for (int y = 0; y < BOARDPARAMS; y++) {
            // Print rank number (8 to 1, top to bottom)
            System.out.print((8 - y) + " ");
            
            // Print pieces in this rank
            for (int x = 0; x < BOARDPARAMS; x++) {
                if (board[y][x] == null)
                    System.out.print("[ ]");
                else
                    System.out.print("[" + board[y][x].toString() + "]");
            }
            
            // Print rank number again at the end of the row
            System.out.println(" " + (8 - y));
        }
        
        // Print the footer with file coordinates again
        System.out.print("  ");
        for (char file = 'a'; file <= 'h'; file++) {
            System.out.print(" " + file + " ");
        }
        System.out.println();
        System.out.println("    WHITE SIDE");
    }

    public void displayMoves(List<Coordinate> coordinates) {
        // Mark all valid move coordinates with a debug pawn represented as "*"
        coordinates.forEach(coordinate -> 
            board[coordinate.Y][coordinate.X] = new Pawn(DEBUG, coordinate.X, coordinate.Y) {
            @Override
            public String toString() {
                return "*";
            }
        });
        
        // Display the board with the valid move markers
        displayBoard();
        
        // Clean up the board by removing all debug markers
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

    /**
     * Clears a specific position on the board
     * @param x x-coordinate to clear
     * @param y y-coordinate to clear
     */
    public void clearPosition(int x, int y) {
        if (x >= 0 && x < BOARDPARAMS && y >= 0 && y < BOARDPARAMS) {
            board[y][x] = null;
        }
    }

    /**
     * Clears a specific position on the board
     * @param coordinate coordinate to clear
     */
    public void clearPosition(Coordinate coordinate) {
        clearPosition(coordinate.X, coordinate.Y);
    }

    private void setBoard(Piece[][] board) throws BoardException {
        if (board.length != this.board.length)
            throw new BoardException();
        this.board = board;
    }

}