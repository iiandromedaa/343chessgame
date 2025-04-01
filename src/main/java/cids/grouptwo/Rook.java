package cids.grouptwo;

class Rook extends Piece {
    public Rook(String color, int x, int y) {
        super(color, x, y);
    }

   @Override
    public boolean isValidMove(int targetY, int targetX, Piece[][] board) {
        if (this.getY() == targetY && this.getX() == targetX) {
            return false;
        }
    
        if (this.getY() != targetY && this.getX() != targetX) {
            return false;
        }
    
        if (this.getY() == targetY) {
            int start = Math.min(this.getX(), targetX) + 1;
            int end = Math.max(this.getX(), targetX);
            for (int i = start; i < end; i++) {
                if (board[targetY][i] != null) {
                    return false;
                }
            }
        } else {
            int start = Math.min(this.getY(), targetY) + 1;
            int end = Math.max(this.getY(), targetY);
            for (int i = start; i < end; i++) {
                if (board[i][targetX] != null) {
                    return false;
                }
            }
        }
    
        return true;
    }
}