package cids.grouptwo.pieces;

public class Ferz extends Bishop {

	public Ferz(Color color, int x, int y) {
		super(color, x, y);
	}

    @Override
    public Ferz copyPiece(){
        Ferz tempPiece = new Ferz(this.getColor(), this.getX(), this.getY());
        return tempPiece;
    }

    @Override
    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        // one square diagonally
        if (Math.abs(newX - getX()) != 1 || Math.abs(newY - getY()) != 1)
            return false;
        return Math.abs(newX - getX()) == Math.abs(newY - getY()) &&
            (board[newY][newX] == null || board[newY][newX].getColor() != getColor());
    }

    @Override
    public String toString() {
        return "F";
    }
    
}
