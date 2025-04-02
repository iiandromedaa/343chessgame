package cids.grouptwo.pieces;

public class King extends Piece {

    public King(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        // King moves one square in any direction
        return Math.abs(newX - getX()) <= 1 && Math.abs(newY - getY()) <= 1;
    }

    @Override
    public String toString() {
        if (getColor() == Color.WHITE)
            return "♔";
        else
            return "♚";
    }
    
}
