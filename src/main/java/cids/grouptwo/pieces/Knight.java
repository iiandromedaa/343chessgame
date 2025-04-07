package cids.grouptwo.pieces;

public class Knight extends Piece {

    public Knight(Color color, int x, int y) {
        super(color, x, y);
    }

    /**
     * Validates moves for the Knight piece
     * Knights move in an L-shape: two squares in one direction and one square perpendicular
     */
    @Override
    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        // Check board boundaries
        if (newX < 0 || newX >= 8 || newY < 0 || newY >= 8) {
            return false;
        }
        
        // Calculate absolute distance from current position
        int dx = Math.abs(newX - getX());
        int dy = Math.abs(newY - getY());

        // Check if move matches knight's L-shape pattern (2+1)
        if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) {
            // Valid if destination is empty
            if (board[newY][newX] == null)
                return true;
            // Or if destination has opponent's piece
            return board[newY][newX].getColor() != getColor();
        }
        return false;
    }

    @Override
    public String toString() {
        if (getColor() == Color.WHITE)
            return "♘";
        else
            return "♞";
    }
    
}
