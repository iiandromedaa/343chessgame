package cids.grouptwo;

public class Knight extends Piece {
    public Knight(String color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY) {
        // Knight moves in an L-shape: two squares in one direction and one square perpendicular
        int dx = Math.abs(newX - getX());
        int dy = Math.abs(newY - getY());
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }
    
}
