package cids.grouptwo;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cids.grouptwo.exceptions.BoardException;
import cids.grouptwo.exceptions.FenParseException;
import cids.grouptwo.pieces.*;
import static cids.grouptwo.pieces.Piece.Color.BLACK;
import static cids.grouptwo.pieces.Piece.Color.DEBUG;
import static cids.grouptwo.pieces.Piece.Color.WHITE;

public class Board {

    private boolean[][] obstacles;
    private Random random = new Random();
    private List<BoardListener> listeners;

    private Piece[][] board;

    private final int BOARDPARAMS = 8;

    /**
     * creates standard chess board
     */
    public Board(Map<Piece, Piece> pieceSet) {
        board = new Piece[BOARDPARAMS][BOARDPARAMS];
        obstacles = new boolean[BOARDPARAMS][BOARDPARAMS];
        defaultBoard(pieceSet);
        listeners = new ArrayList<>();
    }

    /**
     * creates board with set state from FEN notation
     * <p>call using null as parameter for empty board
     * @param fen FEN notation
     */
    public Board(Map<Piece, Piece> pieceSet, String fen) {
        board = new Piece[BOARDPARAMS][BOARDPARAMS];
        obstacles = new boolean[BOARDPARAMS][BOARDPARAMS];
        if (fen == null)
            return;
        try {
            if (pieceSet.isEmpty())
                setBoard(FenParse.parse(fen, defaultBoard(pieceSet)));
            else
                setBoard(FenParse.parse(fen, pieceSet));
		} catch (BoardException | FenParseException e) {
			e.printStackTrace();
		}
        listeners = new ArrayList<>();
    }

    /**
     * author:Adam
     * @param other
     */
    public Board(Board other){
        this.board =  new Piece[BOARDPARAMS][BOARDPARAMS];
        for(int i = 0; i < BOARDPARAMS; i++){
            for(int j = 0; j < BOARDPARAMS; j++){
                if(other.getPieceFromXY(i,j) != null){
                    Piece piece = other.getPieceFromXY(i,j).copyPiece();
                    this.setPiece(piece);
                }
            }
        }
    }


    /**
     * Displays chess board with proper chess notation for coordinates
     * Shows the full board with coordinate labels (a-h, 1-8)
     * Black pieces are at the top (rows 0-1), white pieces at the bottom (rows 6-7)
     */
    public void displayBoard() {
        //Main.clear();
        
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

    /**
     * Returns the board as a piece array
     * Author:Adam
     */
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

    /**
     * bundles together the method calls required to make a move
     * @param piece piece
     * @param coordinate coordinate to move it to
     */
    public void move(Piece piece, Coordinate coordinate) {
        clearPosition(piece.getPosition());
        notifyListeners(new Move(piece.getPosition(), coordinate, piece));
        piece.piecePosition(coordinate);
        setPiece(piece);
    }

    public void move(Move move) {
        System.out.println(move);
        Piece piece = getPieceFromCoordinate(move.to);
        if (piece.getPosition() != null)
            clearPosition(move.to);
        notifyListeners(move);
        // move.piece.piecePosition(move.to);
        setPiece(move.piece);
    }

    public void setBoard(Piece[][] board) throws BoardException {
        if (board.length != this.board.length)
            throw new BoardException();
        this.board = board;
    }

    /* 10% chance of spawning an obstacle on the board */
    public void attemptObstacleSpawn() {
        if (random.nextInt(100) < 10) {
            int x, y;
            do {
                x = random.nextInt(BOARDPARAMS);
                y = random.nextInt(BOARDPARAMS);
            } while (board[y][x] != null || obstacles[y][x]);

            obstacles[y][x] = true;
            System.out.println("Obstacle spawned at (" + x + ", " + y + ")");
        }
    }

    /* method to clear obstacles (50% chance each turn - can be changed to whatever) */
    public void clearObstacles() {
        for (int y = 0; y < BOARDPARAMS; y++) {
            for (int x = 0; x < BOARDPARAMS; x++) {
                if (obstacles[y][x] && random.nextInt(100) < 50) {
                    obstacles[y][x] = false;
                    
                }
            }
        }
    }

    public boolean isBlocked(int x, int y) {
        return obstacles[y][x];
    }

    public void addListener(BoardListener boardListener) {
        listeners.add(boardListener);
    }

    public void notifyListeners(Move move) {
        for (BoardListener boardListener : listeners) {
            boardListener.boardUpdate(this, move);
        }
    }

    /**
     * This method can be used to easily make a move on the board
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void makeMove(int startX, int startY, int endX, int endY) {
        Piece piece = getPieceFromXY(startX, startY);
        if (piece == null) {
            throw new IllegalStateException("No piece at start: (" + startX + ", " + startY + ")");
        }
        clearPosition(startX, startY); 
        if (piece.returnNumber() == 0 && Math.abs(startX - endX) == 1 && Math.abs(startY - endY) == 1) {
            if (getPieceFromXY(endX, endY) == null) {
                clearPosition(endX, startY); 
            }
        }
        piece.piecePosition(endX, endY);
        setPiece(piece);
        if((piece.returnNumber() == 5) && (Math.abs(startX-endX) == 2)){
            if(endX == 6){
                Piece rook = getPieceFromXY(7, startY);
                clearPosition(7, startY);
                rook.piecePosition(5, startY);
                setPiece(rook);
            }
            else{
                Piece rook = getPieceFromXY(0, startY);
                clearPosition(0, startY);
                rook.piecePosition(3, startY);
                setPiece(rook);
            }
        }
    }

    /**
     * This method can be used to easily undo a move on the board
     * @param from
     * @param to
     */
    public void undoMove(int startX, int startY, int endX, int endY, Piece capturedPiece) {
        Piece piece = getPieceFromXY(endX, endY);
        if (piece == null) {
            throw new IllegalStateException("No piece at destination to undo move");
        }
        clearPosition(endX, endY);
        piece.piecePosition(startX, startY);
        setPiece(piece);
        if((piece.returnNumber() == 5) && (Math.abs(startX-endX) == 2)){
            if(endX == 6){
                Piece rook = getPieceFromXY(5, startY);
                clearPosition(5, startY);
                rook.piecePosition(7, startY);
                setPiece(rook);
            }
            else{
                Piece rook = getPieceFromXY(3, startY);
                clearPosition(3, startY);
                rook.piecePosition(0, startY);
                setPiece(rook);
            }
        }

        if (capturedPiece != null) {
            setPiece(capturedPiece);
        }
    }

    /**
     * Sets up the chess board with an initial configuration
     *
     * @param board the chess board to set up
     */
    private Map<Piece, Piece> defaultBoard(Map<Piece, Piece> pieceSet) {
        // Add pieces in their initial positions

        // White pieces
        setPiece(new Rook(WHITE, 0, 7));
        setPiece(new Knight(WHITE, 1, 7));
        setPiece(new Bishop(WHITE, 2, 7));
        setPiece(new Queen(WHITE, 3, 7));
        setPiece(new King(WHITE, 4, 7));
        setPiece(new Bishop(WHITE, 5, 7));
        setPiece(new Knight(WHITE, 6, 7));
        setPiece(new Rook(WHITE, 7, 7));

        // White pawns
        for (int i = 0; i < 8; i++) {
            setPiece(new Pawn(WHITE, i, 6));
        }

        if (pieceSet.isEmpty()) {
            pieceSet.put(getPieceFromXY(0, 7), getPieceFromXY(0, 7));
            pieceSet.put(getPieceFromXY(1, 7), getPieceFromXY(1, 7));
            pieceSet.put(getPieceFromXY(2, 7), getPieceFromXY(2, 7));
            pieceSet.put(getPieceFromXY(3, 7), getPieceFromXY(3, 7));
            pieceSet.put(getPieceFromXY(4, 7), getPieceFromXY(4, 7));
            pieceSet.put(getPieceFromXY(5, 7), getPieceFromXY(5, 7));
            pieceSet.put(getPieceFromXY(6, 7), getPieceFromXY(6, 7));
            pieceSet.put(getPieceFromXY(7, 7), getPieceFromXY(7, 7));
            for (int i = 0; i < board.length; i++) {
                pieceSet.put(getPieceFromXY(i, 6), getPieceFromXY(i, 6));
            }
        }

        // Black pieces
        setPiece(new Rook(BLACK, 0, 0));
        setPiece(new Knight(BLACK, 1, 0));
        setPiece(new Bishop(BLACK, 2, 0));
        setPiece(new Queen(BLACK, 3, 0));
        setPiece(new King(BLACK, 4, 0));
        setPiece(new Bishop(BLACK, 5, 0));
        setPiece(new Knight(BLACK, 6, 0));
        setPiece(new Rook(BLACK, 7, 0));

        // Black pawns
        for (int i = 0; i < 8; i++) {
            setPiece(new Pawn(BLACK, i, 1));
        }

        return pieceSet;
    }

}