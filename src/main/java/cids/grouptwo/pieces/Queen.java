package cids.grouptwo.pieces;

class Queen extends Piece {

    public Queen(Color color, int x, int y) {
       super(color, x, y);
   }

   @Override
   public boolean isValidMove(int targetX, int targetY, Piece[][] board) {
       Rook rookMove = new Rook(this.getColor(), this.getX(), this.getY());
       Bishop bishopMove = new Bishop(this.getColor(), this.getX(), this.getY());

       return rookMove.isValidMove(targetX, targetY, board) || bishopMove.isValidMove(targetX, targetY, board);
   }

   @Override
   public String toString() {
       if (getColor() == Color.WHITE)
            return "♕";
        else
            return "♛";
   }
}