package cids.grouptwo.pieces;

public class Bishop extends Piece {
    
    public Bishop(Color color, int x, int y) {
        super(color, x, y);
    }

    /**
     * Validates moves for the Bishop piece
     * Bishops move diagonally any number of squares
     */
    @Override
    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        // Check board boundaries
        if (newX < 0 || newX >= 8 || newY < 0 || newY >= 8) {
            return false;
        }
        
        // Cannot move to current position
        if (newX == getX() && newY == getY())
            return false;

        // Bishop can only move diagonally - check that x and y differences are equal
        int deltaX = Math.abs(newX - getX());
        if (deltaX != Math.abs(newY - getY()))
            return false;

        // Determine direction of movement (diagonal): +1 or -1 for each axis
        int dx = (getX() < newX) ? 1 : -1;
        int dy = (getY() < newY) ? 1 : -1;
        
        // Check for pieces blocking the path along the diagonal
        for (int i = 1; i < deltaX; i++) {
            if (board[getY() + i * dy][getX() + i * dx] != null)
                return false;
        }

        // Valid move if destination square is empty
        if (board[newY][newX] == null)
            return true;
        
        // Valid move if destination has opponent's piece
        return (board[newY][newX].getColor() != getColor());
    }

    @Override
    public String toString() {
        if (getColor() == Color.WHITE)
            return "♗";
        else
            return "♝";
    }
    
}
