package cids.grouptwo;

class Pawn extends Piece {
    public Pawn(String color, int x, int y) {
        super(color, x, y);
    }

   @Override
    public boolean isValidMove(int targetX, int targetY, Piece[][] board) {
        int direction = (this.getColor().equals("White")) ? -1 : 1;
        int startingRow = (this.getColor().equals("White")) ? 6 : 1;
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
}