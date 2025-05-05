package cids.grouptwo.pieces;

import java.util.ArrayList;
import java.util.List;

import cids.grouptwo.Coordinate;

public class King extends Piece {

    private final double[][] kingEvalWhite = {
        { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        { -2.0, -3.0, -3.0, -4.0, -4.0, -3.0, -3.0, -2.0},
        { -1.0, -2.0, -2.0, -2.0, -2.0, -2.0, -2.0, -1.0},
        {  2.0,  2.0,  0.0,  0.0,  0.0,  0.0,  2.0,  2.0 },
        {  2.0,  3.0,  1.0,  0.0,  0.0,  1.0,  3.0,  2.0 }
    };
    
    private final double[][] kingEvalBlack = {
        {  2.0,  3.0,  1.0,  0.0,  0.0,  1.0,  3.0,  2.0 },
        {  2.0,  2.0,  0.0,  0.0,  0.0,  0.0,  2.0,  2.0 },
        { -1.0, -2.0, -2.0, -2.0, -2.0, -2.0, -2.0, -1.0},
        { -2.0, -3.0, -3.0, -4.0, -4.0, -3.0, -3.0, -2.0},
        { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
        { -3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0}
    };

    private boolean hasMoved = false;

    public King(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public String returnName(){
        return "King";
    }

    @Override
    public int returnNumber(){
        return 5;
    }

    @Override
    public int getValue(){
        return 900;
    }

    @Override
    public double getValueOfSpace(int x, int y){
        if(getColor() == Color.WHITE)
            return kingEvalWhite[x][y];
        else 
            return kingEvalBlack[x][y];
    }

    @Override
    public King copyPiece(){
        King tempPiece = new King(this.getColor(), this.getX(), this.getY());
        return tempPiece;
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

    /**
     * Efficiently gets all possible valid moves for the King
     * including standard moves and castling
     */
    @Override
    public List<Coordinate> getValidMoves(Piece[][] board) {
        List<Coordinate> validMoves = new ArrayList<>();
        
        // Check all eight adjacent squares (standard king moves)
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                // Skip the current position
                if (dx == 0 && dy == 0) continue;
                
                int newX = getX() + dx;
                int newY = getY() + dy;
                
                // Check if the position is on the board
                if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                    // Valid if empty or enemy piece
                    if (board[newY][newX] == null || board[newY][newX].getColor() != getColor()) {
                        validMoves.add(new Coordinate(newX, newY));
                    }
                }
            }
        }
        
        // Check for castling moves
        /**
         * if (!hasMoved) {
            // Kingside castling
            if (canCastle(board, true)) {
                validMoves.add(new Coordinate(getX() + 2, getY()));
            }
            // Queenside castling
            if (canCastle(board, false)) {
                validMoves.add(new Coordinate(getX() - 2, getY()));
            }
        }
         */
       

        //System.out.println("King valid moves: " + validMoves);
        
        return validMoves;
    }

    @Override
    public String toString() {
        if (getColor() == Color.WHITE)
            return "k";
        else
            return "K";
    }
    
}
