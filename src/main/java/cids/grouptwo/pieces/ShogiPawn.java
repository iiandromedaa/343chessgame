package cids.grouptwo.pieces;

public class ShogiPawn extends Pawn implements Shogi {

	public ShogiPawn(Color color, int x, int y) {
        super(color, x, y);
	}

    @Override
    public ShogiPawn copyPiece(){
        ShogiPawn tempPiece = new ShogiPawn(this.getColor(), this.getX(), this.getY());
        return tempPiece;
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
    public String toString() {
        return "歩";
    }

}
