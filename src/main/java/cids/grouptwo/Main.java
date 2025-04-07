package cids.grouptwo;

import java.io.IOException;
import java.util.Scanner;
import java.util.prefs.Preferences;

import cids.grouptwo.pieces.*;
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
        game.getBoard().defaultBoard();
        // runGameLoop(game);
        launchGdx();
    }

    private static void launchGdx() {
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
        new Lwjgl3Application(new cids.grouptwo.gdx.ChessGame(h), config);
        
    }

    /**
     * Runs the main chess game loop
     *
     * @param game the chess game to run
     */
    private static void runGameLoop(ChessGame game) {
        Scanner scanner = new Scanner(System.in);

        while (game.getTurn() != -1) { // Game loop continues until the game is over
            // Display current board state
            game.getBoard().displayBoard();

            System.out.println((game.getTurn() == 0 ? "White" : "Black") + "'s turn. Enter your move (e.g., 'e2 e4'): ");
            String input = scanner.nextLine();

            if (isQuitCommand(input)) {
                System.out.println("Exiting the game...");
                game.kill();
                break;
            }

            try {
                String[] parts = input.split(" ");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid input format. Use 'e2 e4'.");
                }

                Coordinate from = parseCoordinate(parts[0]);
                Coordinate to = parseCoordinate(parts[1]);

                // Get the piece at the starting position
                Piece piece = game.getBoard().getPieceFromXY(from.X, from.Y);

                // Validate that the piece exists and belongs to the current player
                if (piece == null || piece.getColor() != (game.getTurn() == 0 ? Piece.Color.WHITE : Piece.Color.BLACK)) {
                    throw new IllegalArgumentException("Invalid piece selection.");
                }

                // Validate that the move is legal for this piece
                if (!piece.isValidMove(to.X, to.Y, game.getBoard().getBoard())) {
                    throw new IllegalArgumentException("Invalid move for the selected piece.");
                }

                // Handle special moves
                handleSpecialMoves(game.getBoard(), piece, from, to);

                // Clear the old position properly
                game.getBoard().clearPosition(from);

                // Update the piece's internal position
                piece.piecePosition(to);

                // Set the piece at its new position
                game.getBoard().setPiece(piece);

                // Handle pawn promotion if needed
                handlePawnPromotion(game.getBoard(), piece, scanner);

                // Show success message before clearing the board
                System.out.println("Move successful: " + parts[0] + " to " + parts[1]);
                System.out.println("Press Enter to continue...");
                scanner.nextLine();

                // Check for game-ending conditions
                if (isCheckmate(game)) {
                    game.getBoard().displayBoard();
                    System.out.println("Checkmate! " + (game.getTurn() == 0 ? "White" : "Black") + " wins!");
                    game.kill();
                    break;
                }

                // Check for check
                if (isCheck(game)) {
                    System.out.println("Check!");
                }

                // Advance the game state
                game.step();

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Press Enter to try again...");
                scanner.nextLine();
            }
        }

        System.out.println("Game over!");
        scanner.close();
    }

    /**
     * Checks if the input is a command to exit the game
     * 
     * @param input the user input to check
     * @return true if the input is a quit command, false otherwise
     */
    private static boolean isQuitCommand(String input) {
        input = input.trim().toLowerCase();
        return input.equals("quit") || input.equals("exit");
    }

    /**
     * Handles special chess moves like castling and en passant
     */
    private static void handleSpecialMoves(Board board, Piece piece, Coordinate from, Coordinate to) {
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
            } // Queenside castling (left)
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
     */
    private static void handlePawnPromotion(Board board, Piece piece, Scanner scanner) {
        if (piece instanceof Pawn && ((Pawn) piece).canPromote()) {
            System.out.println("Pawn promotion! Enter your choice (Q=Queen, R=Rook, B=Bishop, N=Knight): ");
            String choice = scanner.nextLine().toUpperCase();

            Piece promotedPiece = null;

            switch (choice) {
                case "Q":
                    promotedPiece = new Queen(piece.getColor(), piece.getX(), piece.getY());
                    break;
                case "R":
                    promotedPiece = new Rook(piece.getColor(), piece.getX(), piece.getY());
                    break;
                case "B":
                    promotedPiece = new Bishop(piece.getColor(), piece.getX(), piece.getY());
                    break;
                case "N":
                    promotedPiece = new Knight(piece.getColor(), piece.getX(), piece.getY());
                    break;
                default:
                    promotedPiece = new Queen(piece.getColor(), piece.getX(), piece.getY()); // Default to Queen
                    break;
            }

            if (promotedPiece != null) {
                board.setPiece(promotedPiece); // Replace the pawn with the promoted piece
            }
        }
    }

    /**
     * Checks if the current player's king is in check
     */
    private static boolean isCheck(ChessGame game) {
        // Find the current player's king
        Piece.Color currentColor = (game.getTurn() == 0) ? Piece.Color.WHITE : Piece.Color.BLACK;
        King king = findKing(game.getBoard(), currentColor);

        if (king == null) {
            return false; // Should not happen in a valid chess game
        }

        // Check if any opponent piece can capture the king
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece piece = game.getBoard().getPieceFromXY(x, y);
                if (piece != null && piece.getColor() != currentColor) {
                    if (piece.isValidMove(king.getX(), king.getY(), game.getBoard().getBoard())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Finds the king of the specified color on the board
     */
    private static King findKing(Board board, Piece.Color color) {
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
     */
    private static boolean isCheckmate(ChessGame game) {
        // If not in check, cannot be in checkmate
        if (!isCheck(game)) {
            return false;
        }

        // Find all pieces of the current player
        Piece.Color currentColor = (game.getTurn() == 0) ? Piece.Color.WHITE : Piece.Color.BLACK;
        Board board = game.getBoard();

        // Try all possible moves for all pieces
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece piece = board.getPieceFromXY(x, y);
                if (piece != null && piece.getColor() == currentColor) {
                    // Get all valid moves for this piece
                    for (Coordinate move : piece.getValidMoves(board.getBoard())) {
                        // Save the current state
                        Piece capturedPiece = board.getPieceFromXY(move.X, move.Y);
                        int oldX = piece.getX();
                        int oldY = piece.getY();

                        // Make the move temporarily
                        board.clearPosition(oldX, oldY);
                        piece.piecePosition(move.X, move.Y);
                        board.setPiece(piece);

                        // Check if still in check
                        boolean stillInCheck = isCheck(game);

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
     * Parses algebraic chess notation (e.g., "e4") to board coordinates
     */
    private static Coordinate parseCoordinate(String input) {
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
