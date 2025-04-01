package cids.grouptwo;

public class Bishop extends Piece {
    
    public Bishop(String color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        /* Bishop moves diagonally, so the absolute difference in x and y must be equal */ 
        return Math.abs(newX - getX()) == Math.abs(newY - getY());
    }
    
}
