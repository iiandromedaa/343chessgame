package cids.grouptwo;

public class King extends Piece {
    
    public King(String color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        // King moves one square in any direction
        return Math.abs(newX - getX()) <= 1 && Math.abs(newY - getY()) <= 1;
    }
    
}
