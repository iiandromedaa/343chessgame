package cids.grouptwo.pieces;

import java.util.List;

import cids.grouptwo.Coordinate;

/**
 * elephant
 */
public class Alfil extends Bishop {

	public Alfil(Color color, int x, int y) {
		super(color, x, y);
	}

    @Override
    public Alfil copyPiece(){
        Alfil tempPiece = new Alfil(this.getColor(), this.getX(), this.getY());
        return tempPiece;
    }

    @Override
    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        // two square diagonally
        if (Math.abs(newX - getX()) != 2 || Math.abs(newY - getY()) != 2)
            return false;
        return Math.abs(newX - getX()) == Math.abs(newY - getY()) &&
            (board[newY][newX] == null || board[newY][newX].getColor() != getColor());
    }

    @Override
    public List<Coordinate> getValidMoves(Piece[][] board) {
        return super.getValidMovesUnoverridden(board);
    }

    @Override
    public String toString() {
        return "🐘";
    }
    
}