package cids.grouptwo.pieces;

public class Pawn extends Piece {

    public Pawn(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        int direction = (getColor() == Piece.Color.WHITE) ? 1 : -1;
        int startingRow = (getColor() == Piece.Color.WHITE) ? 1 : 6;
        
        int dx = newX - getX();
        int dy = newY - getY();
    
        if (dx == 0) {
            if (dy == direction) {
                return board[newY][newX] == null;
            } else if (dy == (2 * direction) && getY() == startingRow) {
                return board[newY][newX] == null && board[getY() + direction][newX] == null;
            }
        } else if (Math.abs(dx) == 1 && dy == direction) {
            return board[newY][newX] != null && !board[newY][newX].getColor().equals(getColor());
        }
    
        return false;
    }

    @Override
    public String toString() {
        if (getColor() == Color.WHITE)
            return "♙";
        else
            return "♟";
    }

}