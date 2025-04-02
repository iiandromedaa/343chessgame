package cids.grouptwo.pieces;

public class Knight extends Piece {

    public Knight(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        // Knight moves in an L-shape: two squares in one direction and one square perpendicular
        int dx = Math.abs(newX - getX());
        int dy = Math.abs(newY - getY());

        if (((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) && (board[dy][dx]).getColor() != getColor())
            return true;
        else
            return false;

        // return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }

    @Override
    public String toString() {
        if (getColor() == Color.WHITE)
            return "♘";
        else
            return "♞";
    }
    
}
