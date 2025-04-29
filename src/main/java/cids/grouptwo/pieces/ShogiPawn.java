package cids.grouptwo.pieces;

import java.util.List;

import cids.grouptwo.Coordinate;

public class ShogiPawn extends Pawn implements Shogi {

	public ShogiPawn(Color color, int x, int y) {
        super(color, x, y);
	}

    @Override
    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        if (getColor() == Color.WHITE)
            return newX == getX() && newY == getY() - 1 && 
                (board[newY][newX] == null || board[newY][newX].getColor() != getColor());
        else
            return newX == getX() && newY == getY() + 1 && 
                (board[newY][newX] == null || board[newY][newX].getColor() != getColor());
    }

    @Override
    public List<Coordinate> getValidMoves(Piece[][] board) {
        return super.getValidMovesUnoverridden(board);
    }

    @Override
    public String toString() {
        return "歩";
    }

}
