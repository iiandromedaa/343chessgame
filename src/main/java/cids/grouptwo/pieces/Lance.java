package cids.grouptwo.pieces;

import java.util.List;

import cids.grouptwo.Coordinate;

public class Lance extends Rook implements Shogi {

	public Lance(Color color, int x, int y) {
		super(color, x, y);
	}

    @Override
    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        // Check board boundaries
        if (newX < 0 || newX >= 8 || newY < 0 || newY >= 8) {
            return false;
        }
        
        // Cannot move to current position
        if (this.getY() == newY && this.getX() == newX) {
            return false;
        }
    
        // Lance can only move vertically
        if (this.getX() != newX) {
            return false;
        }
    
        // Check vertical movement
        else {
            int dy = (getColor() == Color.BLACK) ? 1 : -1;
            for (int i = getY() + dy; i != newY; i += dy) {
                if (i >= 8 || i < 0)
                    return false;
                if (board[i][getX()] != null)
                    return false;
            }
        }
    
        // Valid move if destination square is empty
        if (board[newY][newX] == null)
            return true;
        
        // Valid move if destination has opponent's piece
        return (board[newY][newX].getColor() != getColor());
    }

    @Override
    public List<Coordinate> getValidMoves(Piece[][] board) {
        return super.getValidMovesUnoverridden(board);
    }

    @Override
    public String toString() {
        return "香";
    }
    
}
