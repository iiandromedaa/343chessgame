package cids.grouptwo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import cids.grouptwo.pieces.*;
import cids.grouptwo.roguestuff.Modifier;

/**
 * The core gameplay loop and gamestate is stored here.
 * Manages the chess game state, player turns, and game logic.
 */
public class ChessGame {

    /**
     * Game state indicator:<p>
     * </p><p>-1: game over
     * </p><p>0: white's turn
     * </p><p>1: black's turn
     */
    private int turn;
    private Board board;
    // temporary, we dont know how we wanna store modifiers yet
    private List<Modifier> modifiers;
    /**
     * map to store piece transformations (promotion / swapping a piece for another as a modifier)
     * when the FEN parser is setting up a game state, it will refer to this map to place pieces
     * where they would have been in the original FEN game state, even if the modified piece
     * did not exist in the original notation
     * <p>PS, this only needs to store white pieces, until we figure out if we want the AI to get
     * piece swaps too
     */
    private Map<Piece, Piece> pieceSet;

    /**
     * Constructor initializes a new chess game with white to move first
     * and an empty board
     */
    public ChessGame() {
        // white first move of course
        turn = 0;
        pieceSet = new HashMap<>();
    }

    public void newBoard() {
        board = new Board(pieceSet);
    }

    public void newBoard(String fen) {
        board = new Board(pieceSet, fen);
    }

    public Map<Piece, Piece> getPieceSet() {
        return pieceSet;
    }

    /**
     * Gets the current chess board
     * @return the game board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Main gameplay loop that handles player input and game state transitions
     */
    public void estoyLoopin() {
        Scanner scanner = new Scanner(System.in);

        while (turn != -1) { // Game loop continues until the game is over
            displayGameState();

            System.out.println((turn == 0 ? "White" : "Black") + "'s turn. Enter your move (e.g., 'e2 e4'): ");
            String input = scanner.nextLine();

            try {
                String[] parts = input.split(" ");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid input format. Use 'e2 e4'.");
                }

                Coordinate from = parseCoordinate(parts[0]);
                Coordinate to = parseCoordinate(parts[1]);

                // Get the piece at the starting position
                Piece piece = board.getPieceFromXY(from.X, from.Y);
                
                // Validate that the piece exists and belongs to the current player
                if (piece == null || piece.getColor() != (turn == 0 ? Piece.Color.WHITE : Piece.Color.BLACK)) {
                    throw new IllegalArgumentException("Invalid piece selection.");
                }

                // Validate that the move is legal for this piece
                if (!piece.isValidMove(to.X, to.Y, board.getBoard())) {
                    throw new IllegalArgumentException("Invalid move for the selected piece.");
                }

                // Handle special moves
                handleSpecialMoves(piece, from, to);

                // Clear the old position properly
                board.clearPosition(from);
                
                // Update the piece's infernal position
                piece.piecePosition(to);
                
                // Set the piece at its new position
                board.setPiece(piece);
                
                // Handle pawn promotion if needed
                handlePawnPromotion(piece, scanner);

                // Check for game-ending conditions
                if (isCheckmate()) {
                    displayGameState();
                    System.out.println("Checkmate! " + (turn == 0 ? "White" : "Black") + " wins!");
                    kill();
                    break;
                }

                // Advance the game state
                step();

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("Game over!");
        scanner.close();
    }

    /**
     * Handles special chess moves like castling and en passant
     * @param piece the piece being moved
     * @param from starting coordinate
     * @param to destination coordinate
     */
    private void handleSpecialMoves(Piece piece, Coordinate from, Coordinate to) {
        // Handle castling
        if (piece instanceof King && Math.abs(from.X - to.X) == 2) {
            // Kingside castling (right)
            if (to.X > from.X) {
                Piece rook = board.getPieceFromXY(7, from.Y);
                if (rook instanceof Rook) {
                    board.clearPosition(7, from.Y);
                    rook.piecePosition(from.X + 1, from.Y);
                    board.setPiece(rook);
                }
            } 
            // Queenside castling (left)
            else {
                Piece rook = board.getPieceFromXY(0, from.Y);
                if (rook instanceof Rook) {
                    board.clearPosition(0, from.Y);
                    rook.piecePosition(from.X - 1, from.Y);
                    board.setPiece(rook);
                }
            }
        }
        
        // Handle en passant capture
        if (piece instanceof Pawn && from.X != to.X && board.getPieceFromXY(to.X, to.Y) == null) {
            // If moving diagonally to an empty square, it must be en passant
            board.clearPosition(to.X, from.Y); // Remove the captured pawn
        }
    }

    /**
     * Handles pawn promotion when a pawn reaches the opposite end of the board
     * @param piece the piece that might need promotion
     * @param scanner scanner for user input
     */
    private void handlePawnPromotion(Piece piece, Scanner scanner) {
        if (piece instanceof Pawn && ((Pawn)piece).canPromote()) {
            System.out.println("Pawn promotion! Enter your choice (Q=Queen, R=Rook, B=Bishop, N=Knight): ");
            String choice = scanner.nextLine().toUpperCase();
            
            switch (choice) {
                case "Q":
                    swapPiece(piece, new Queen(piece.getColor(), piece.getX(), piece.getY()));
                    break;
                case "R":
                    swapPiece(piece, new Rook(piece.getColor(), piece.getX(), piece.getY()));
                    break;
                case "B":
                    swapPiece(piece, new Bishop(piece.getColor(), piece.getX(), piece.getY()));
                    break;
                case "N":
                    swapPiece(piece, new Knight(piece.getColor(), piece.getX(), piece.getY()));
                    break;
                default:
                    swapPiece(piece, new Queen(piece.getColor(), piece.getX(), piece.getY())); // Default to Queen
                    break;
            }
        }
    }

    public void swapPiece(Piece from, Piece to) {
        pieceSet.put(from, to);
        board.setPiece(to);
    }

    /**
     * Checks if the current player's king is in check
     * @return true if the current player's king is in check
     */
    private boolean isCheck() {
        // Find the current player's king
        Piece.Color currentColor = (turn == 0) ? Piece.Color.WHITE : Piece.Color.BLACK;
        King king = findKing(currentColor);
        
        if (king == null) {
            return false; // Should not happen in a valid chess game
        }
        
        // Check if any opponent piece can capture the king
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece piece = board.getPieceFromXY(x, y);
                if (piece != null && piece.getColor() != currentColor) {
                    if (piece.isValidMove(king.getX(), king.getY(), board.getBoard())) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }

    /**
     * Finds the king of the specified color on the board
     * @param color the color of the king to find
     * @return the King piece or null if not found
     */
    private King findKing(Piece.Color color) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece piece = board.getPieceFromXY(x, y);
                if (piece instanceof King && piece.getColor() == color) {
                    return (King) piece;
                }
            }
        }
        return null;
    }

    /**
     * Checks if the current player is in checkmate
     * @return true if the current player is in checkmate
     */
    private boolean isCheckmate() {
        // If not in check, cannot be in checkmate
        if (!isCheck()) {
            return false;
        }
        
        // Find all pieces of the current player
        Piece.Color currentColor = (turn == 0) ? Piece.Color.WHITE : Piece.Color.BLACK;
        
        // Try all possible moves for all pieces
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece piece = board.getPieceFromXY(x, y);
                if (piece != null && piece.getColor() == currentColor) {
                    // Get all valid moves for this piece
                    List<Coordinate> validMoves = piece.getValidMoves(board.getBoard());
                    
                    // Try each move to see if it gets out of check
                    for (Coordinate move : validMoves) {
                        // Save the current state
                        Piece capturedPiece = board.getPieceFromXY(move.X, move.Y);
                        int oldX = piece.getX();
                        int oldY = piece.getY();
                        
                        // Make the move temporarily
                        board.clearPosition(oldX, oldY);
                        piece.piecePosition(move.X, move.Y);
                        board.setPiece(piece);
                        
                        // Check if still in check
                        boolean stillInCheck = isCheck();
                        
                        // Restore the original position
                        board.clearPosition(move.X, move.Y);
                        piece.piecePosition(oldX, oldY);
                        board.setPiece(piece);
                        if (capturedPiece != null) {
                            board.setPiece(capturedPiece);
                        }
                        
                        // If any move gets out of check, not checkmate
                        if (!stillInCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        
        // If no move gets out of check, it's checkmate
        return true;
    }

    /**
     * Display the current state of the chess board
     */
    private void displayGameState() {
        board.displayBoard();
    }

    /**
     * Parses algebraic chess notation (e.g., "e4") to board coordinates
     * @param input string in algebraic notation
     * @return Coordinate object representing the board position
     */
    private Coordinate parseCoordinate(String input) {
        if (input.length() != 2) {
            throw new IllegalArgumentException("Invalid coordinate format.");
        }

        int x = input.charAt(0) - 'a'; // Convert 'a'-'h' to 0-7
        int y = 8 - (input.charAt(1) - '0'); // Convert '1'-'8' to 7-0

        if (x < 0 || x >= 8 || y < 0 || y >= 8) {
            throw new IllegalArgumentException("Coordinate out of bounds.");
        }

        return new Coordinate(x, y);
    }

    /**
     * Get the current turn state
     * @return turn value (-1: game over, 0: white, 1: black)
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Advances the game state after a player has moved
     * Switches turns between white and black
     */
    public void step() {
        if (turn == 0) {
            // Change from white's turn to black's turn
            turn = 1;
            // Bot implementation would go here
            // HypotheticalChessBot.playSickAssMove();
            // Don't automatically step again! That skips black's turn
        } else if (turn == 1) {
            // Change from black's turn to white's turn
            turn = 0;
        } else {
            // It's not either black's or white's turn, the game must be over
            return;
        }
    }

    /**
     * Ends the game by setting turn to -1
     */
    public void kill() {
        turn = -1;
    }

    /**
     * Resets the game state to start a new game
     */
    public void reset() {
        // Set turn to white's
        turn = 0;
        // TODO choose random FEN from our cool list of chess midgames
        // TODO set board state to that FEN
    }

}
