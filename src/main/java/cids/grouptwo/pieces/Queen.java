package cids.grouptwo.pieces;

public class Queen extends Piece {

    public Queen(Color color, int x, int y) {
       super(color, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        Rook rookMove = new Rook(this.getColor(), this.getX(), this.getY());
        Bishop bishopMove = new Bishop(this.getColor(), this.getX(), this.getY());

        return rookMove.isValidMove(newX, newY, board) || bishopMove.isValidMove(newX, newY, board);
    }

    @Override
    public String toString() {
        if (getColor() == Color.WHITE)
            return "♕";
        else
            return "♛";
    }
}