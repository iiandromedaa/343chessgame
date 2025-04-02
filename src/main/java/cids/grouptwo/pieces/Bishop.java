package cids.grouptwo.pieces;

public class Bishop extends Piece {
    
    public Bishop(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        // do not return its current square
        if (newX == getX() && newY == getY())
            return false;

        int delta = Math.abs(newX - getX());
        // get the absolutes, compare to check if its diagonal
        if (delta != Math.abs(newY - getY()))
            return false;

        int dx = (getX() < newX) ? 1 : -1;
        int dy = (getY() < newY) ? 1 : -1;
        
        for (int i = 1; i < delta; i++) {
            if (board[getY() + i * dy][getX() + i * dx] != null)
                return false;
        }

        // valid move if board empty
        if (board[newY][newX] == null)
            return true;
        
        // if square isnt empty and the piece there is a different colour, return true
        return (board[newY][newX].getColor() != getColor());
    }

    // public boolean diagonalObstruction(int newX, int newY, Piece[][] board) {

    // }

    @Override
    public String toString() {
        if (getColor() == Color.WHITE)
            return "♗";
        else
            return "♝";
    }
    
}
