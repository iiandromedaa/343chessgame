package cids.grouptwo.pieces;

public class King extends Piece {

    private boolean hasMoved = false;

    public King(Color color, int x, int y) {
        super(color, x, y);
    }

    /**
     * Validates if a move is legal for the king
     * Includes standard king moves and castling
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

        // Standard king move: one square in any direction
        if (Math.abs(newX - getX()) <= 1 && Math.abs(newY - getY()) <= 1) {
            // Cannot move to a square occupied by a friendly piece
            return board[newY][newX] == null || board[newY][newX].getColor() != getColor();
        }
        
        // Check for castling
        if (!hasMoved && newY == getY() && Math.abs(newX - getX()) == 2) {
            // Kingside castling (right)
            if (newX > getX()) {
                return canCastle(board, true);
            }
            // Queenside castling (left) 
            else {
                return canCastle(board, false);
            }
        }

        return false;
    }

    /**
     * Checks if castling is possible
     * @param board the current board state
     * @param kingSide true for kingside castling, false for queenside
     * @return true if castling is legal
     */
    private boolean canCastle(Piece[][] board, boolean kingSide) {
        int rookX = kingSide ? 7 : 0;
        
        // Check if rook is in position and hasn't moved
        Piece rook = board[getY()][rookX];
        if (!(rook instanceof Rook) || ((Rook)rook).hasMoved() || rook.getColor() != getColor()) {
            return false;
        }
        
        // Check if squares between king and rook are empty
        int step = kingSide ? 1 : -1;
        for (int x = getX() + step; x != rookX; x += step) {
            if (board[getY()][x] != null) {
                return false;
            }
        }
        
        // TODO: Check if king is in check or would pass through check
        // Full implementation would verify the king is not in check and doesn't
        // move through or into check when castling
        
        return true;
    }

    /**
     * Updates the king's position and records that it has moved
     */
    @Override
    public void piecePosition(int x, int y) {
        // Check if this is a castling move
        if (!hasMoved && Math.abs(x - getX()) == 2 && y == getY()) {
            // This was a castling move, we'll need to move the rook too
            // This would be handled in the game logic
        }
        
        super.piecePosition(x, y);
        hasMoved = true;
    }
    
    /**
     * Whether the king has moved (used for castling eligibility)
     * @return true if the king has moved
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    @Override
    public String toString() {
        if (getColor() == Color.WHITE)
            return "♔";
        else
            return "♚";
    }
    
}
