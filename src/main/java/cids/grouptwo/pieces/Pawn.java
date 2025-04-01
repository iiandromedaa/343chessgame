package cids.grouptwo.pieces;

public class Pawn extends Piece {

    public Pawn(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isValidMove(int targetX, int targetY, Piece[][] board) {
        int direction = (this.getColor() == Piece.Color.BLACK) ? -1 : 1;
        int startingRow = (this.getColor() == Piece.Color.WHITE) ? 6 : 1;
        int moveDistance = targetX - this.getX();
        int colDistance = targetY - this.getY();
    
        if (colDistance == 0) {
            if (moveDistance == direction) {
                return board[targetX][targetY] == null;
            } else if (moveDistance == 2 * direction && this.getX() == startingRow) {
                return board[targetX][targetY] == null && board[this.getX() + direction][targetY] == null;
            }
        } else if (Math.abs(colDistance) == 1 && moveDistance == direction) {
            return board[targetX][targetY] != null && !board[targetX][targetY].getColor().equals(this.getColor());
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