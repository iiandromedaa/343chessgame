package cids.grouptwo.pieces;

public class Rook extends Piece {

    public Rook(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        if (this.getY() == newY && this.getX() == newX) {
            return false;
        }
    
        if (this.getY() != newY && this.getX() != newX) {
            return false;
        }
    
        if (this.getY() == newY) {
            // horizontal
            int dx = (getX() < newX) ? 1 : -1;
            for (int i = getX() + dx; i != newX; i += dx) {
                if (board[getY()][i] != null)
                    return false;
            }
        } else {
            int dy = (getY() < newY) ? 1 : -1;
            for (int i = getY() + dy; i != newY; i += dy) {
                if (board[i][getX()] != null)
                    return false;
            }
        }
    
        // valid move if board empty
        if (board[newY][newX] == null)
            return true;
        
        // if square isnt empty and the piece there is a different colour, return true
        return (board[newY][newX].getColor() != getColor());
    }

    @Override
    public String toString() {
        if (getColor() == Color.WHITE)
            return "♖";
        else
            return "♜";
    }

}