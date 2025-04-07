package cids.grouptwo.pieces;

public class Rook extends Piece {

    private boolean hasMoved = false;

    public Rook(Color color, int x, int y) {
        super(color, x, y);
    }

    /**
     * Checks if a move is valid for a rook
     * Rooks move horizontally or vertically any number of squares
     */
    @Override
    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        // Check board boundaries
        if (newX < 0 || newX >= 8 || newY < 0 || newY >= 8) {
            return false;
        }
        
        // Cannot move to current position
        if (this.getY() == newY && this.getX() == newX) {
            return false;
        }
    
        // Rook can only move horizontally or vertically
        if (this.getY() != newY && this.getX() != newX) {
            return false;
        }
    
        // Check horizontal movement
        if (this.getY() == newY) {
            int dx = (getX() < newX) ? 1 : -1;
            for (int i = getX() + dx; i != newX; i += dx) {
                if (board[getY()][i] != null)
                    return false;
            }
        } 
        // Check vertical movement
        else {
            int dy = (getY() < newY) ? 1 : -1;
            for (int i = getY() + dy; i != newY; i += dy) {
                if (board[i][getX()] != null)
                    return false;
            }
        }
    
        // Valid move if destination square is empty
        if (board[newY][newX] == null)
            return true;
        
        // Valid move if destination has opponent's piece
        return (board[newY][newX].getColor() != getColor());
    }

    /**
     * Updates the rook's position and records that it has moved
     */
    @Override
    public void piecePosition(int x, int y) {
        super.piecePosition(x, y);
        hasMoved = true;
    }
    
    /**
     * Determines if the rook has moved (used for castling eligibility)
     * @return true if the rook has moved
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    @Override
    public String toString() {
        if (getColor() == Color.WHITE)
            return "♖";
        else
            return "♜";
    }

}