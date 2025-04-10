package cids.grouptwo.pieces;

public class Pawn extends Piece {

    private boolean hasMoved = false;
    private boolean movedTwoSquares = false;

    public Pawn(Color color, int x, int y) {
        super(color, x, y);
    }

    /**
     * Checks if a move is valid for a pawn
     * Includes standard moves, captures, en passant and first-move double step
     */
    @Override
    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        // Direction depends on pawn color: white moves up (-1), black moves down (+1)
        int direction = (getColor() == Piece.Color.WHITE) ? -1 : 1;
        // Starting row for initial double move: row 1 for white, row 6 for black
        int startingRow = (getColor() == Piece.Color.WHITE) ? 6 : 1;
        
        int dx = newX - getX();
        int dy = newY - getY();
    
        // Check bounds
        if (newX < 0 || newX >= 8 || newY < 0 || newY >= 8) {
            return false;
        }
    
        // Straight forward move (non-capturing)
        if (dx == 0) {
            // Single square forward
            if (dy == direction) {
                return board[newY][newX] == null;
            } 
            // Double square forward from starting position
            else if (dy == (2 * direction) && getY() == startingRow) {
                // Check that both squares are empty
                return board[newY][newX] == null && board[getY() + direction][newX] == null;
            }
        } 
        // Diagonal capturing move
        else if (Math.abs(dx) == 1 && dy == direction) {
            // Standard diagonal capture
            if (board[newY][newX] != null && board[newY][newX].getColor() != getColor()) {
                return true;
            }
            
            // En passant capture
            if (board[newY][newX] == null) {
                // Check if there's a pawn in the adjacent square that just moved two squares
                Piece adjacentPiece = board[getY()][newX];
                if (adjacentPiece instanceof Pawn && 
                    adjacentPiece.getColor() != getColor() && 
                    ((Pawn)adjacentPiece).hasMovedTwoSquaresLastTurn()) {
                    return true;
                }
            }
        }
    
        return false;
    }

    /**
     * Checks if this pawn has moved two squares on its previous turn
     * @return true if the pawn moved two squares in its last turn
     */
    public boolean hasMovedTwoSquaresLastTurn() {
        return movedTwoSquares;
    }

    /**
     * Updates position and records movement history
     * @param x new x position
     * @param y new y position
     */
    @Override
    public void piecePosition(int x, int y) {
        // Calculate if this move is a two-square move
        movedTwoSquares = Math.abs(y - getY()) == 2;
        
        // Call the parent method to actually update the position
        super.piecePosition(x, y);
        
        // Mark that the pawn has moved
        hasMoved = true;
    }

    /**
     * Gets the promotion options when a pawn reaches the opposite end of the board
     * @return array of possible promotion piece types
     */
    public Class<? extends Piece>[] getPromotionOptions() {
        @SuppressWarnings("unchecked")
        Class<? extends Piece>[] options = (Class<? extends Piece>[]) new Class<?>[] {
            Queen.class, Rook.class, Bishop.class, Knight.class
        };
        return options;
    }

    /**
     * Checks if the pawn can be promoted
     * @return true if pawn is at the end of the board
     */
    public boolean canPromote() {
        return (getColor() == Color.WHITE && getY() == 0) || 
               (getColor() == Color.BLACK && getY() == 7);
    }

    @Override
    public String toString() {
        if (getColor() == Color.WHITE)
            return "♙";
        else if (getColor() == Color.BLACK)
            return "♟";
        else
            return "P";
    }
}