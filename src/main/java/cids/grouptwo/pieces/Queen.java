package cids.grouptwo.pieces;

public class Queen extends Piece {

    public Queen(Color color, int x, int y) {
       super(color, x, y);
    }

    /**
     * Validates moves for the Queen piece
     * Queens can move horizontally, vertically or diagonally any number of squares
     */
    @Override
    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        // Check board boundaries
        if (newX < 0 || newX >= 8 || newY < 0 || newY >= 8) {
            return false;
        }
        
        // Cannot move to current position
        if (newX == getX() && newY == getY()) 
            return false;
            
        // Queen combines movement of Rook and Bishop
        // Create temporary pieces to reuse their movement validation
        Rook rookMove = new Rook(this.getColor(), this.getX(), this.getY());
        Bishop bishopMove = new Bishop(this.getColor(), this.getX(), this.getY());

        // Valid if move follows either rook or bishop movement pattern
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