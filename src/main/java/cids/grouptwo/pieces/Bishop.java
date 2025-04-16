package cids.grouptwo.pieces;

import java.util.ArrayList;
import java.util.List;

import cids.grouptwo.Coordinate;

public class Bishop extends Piece {
    
    public Bishop(Color color, int x, int y) {
        super(color, x, y);
    }

    /**
     * Validates moves for the Bishop piece
     * Bishops move diagonally any number of squares
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

        // Bishop can only move diagonally - check that x and y differences are equal
        int deltaX = Math.abs(newX - getX());
        if (deltaX != Math.abs(newY - getY()))
            return false;

        // Determine direction of movement (diagonal): +1 or -1 for each axis
        int dx = (getX() < newX) ? 1 : -1;
        int dy = (getY() < newY) ? 1 : -1;
        
        // Check for pieces blocking the path along the diagonal
        for (int i = 1; i < deltaX; i++) {
            if (board[getY() + i * dy][getX() + i * dx] != null)
                return false;
        }

        // Valid move if destination square is empty
        if (board[newY][newX] == null)
            return true;
        
        // Valid move if destination has opponent's piece
        return (board[newY][newX].getColor() != getColor());
    }

    /**
     * Efficiently gets all possible valid moves for the Bishop
     */
    @Override
    public List<Coordinate> getValidMoves(Piece[][] board) {
        List<Coordinate> validMoves = new ArrayList<>();
        
        // Define the four diagonal directions
        int[][] directions = {
            {1, 1},   // Down-right
            {1, -1},  // Up-right
            {-1, 1},  // Down-left
            {-1, -1}  // Up-left
        };
        
        // Check each direction
        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];
            
            // Move in this direction until we hit a piece or the edge
            for (int i = 1; i < 8; i++) {
                int newX = getX() + i * dx;
                int newY = getY() + i * dy;
                
                // Stop if we're off the board
                if (newX < 0 || newX >= 8 || newY < 0 || newY >= 8) {
                    break;
                }
                
                // Empty square is valid
                if (board[newY][newX] == null) {
                    validMoves.add(new Coordinate(newX, newY));
                }
                // Capture opponent piece and stop
                else if (board[newY][newX].getColor() != getColor()) {
                    validMoves.add(new Coordinate(newX, newY));
                    break;
                }
                // Our piece blocks the path
                else {
                    break;
                }
            }
        }

        System.out.println("Bishop valid moves: " + validMoves);
        
        return validMoves;
    }

    @Override
    public String toString() {
        if (getColor() == Color.WHITE)
            return "♗";
        else
            return "♝";
    }
    
}
