package cids.grouptwo.pieces;

import java.util.ArrayList;
import java.util.List;

import cids.grouptwo.Coordinate;

public class Knight extends Piece {

    public Knight(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public String returnName(){
        return "Knight";
    }

    @Override
    public Knight copyPiece(){
        Knight tempPiece = new Knight(this.getColor(), this.getX(), this.getY());
        return tempPiece;
    }

    /**
     * Validates moves for the Knight piece
     * Knights move in an L-shape: two squares in one direction and one square perpendicular
     */
    @Override
    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        // Check board boundaries
        if (newX < 0 || newX >= 8 || newY < 0 || newY >= 8) {
            return false;
        }
        
        // Calculate absolute distance from current position
        int dx = Math.abs(newX - getX());
        int dy = Math.abs(newY - getY());

        // Check if move matches knight's L-shape pattern (2+1)
        if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) {
            // Valid if destination is empty
            if (board[newY][newX] == null)
                return true;
            // Or if destination has opponent's piece
            return board[newY][newX].getColor() != getColor();
        }
        return false;
    }

    /**
     * Efficiently gets all possible valid moves for the Knight
     */
    @Override
    public List<Coordinate> getValidMoves(Piece[][] board) {
        List<Coordinate> validMoves = new ArrayList<>();
        
        // All possible L-shaped knight moves
        int[][] knightMoves = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };
        
        // Check each possible knight move
        for (int[] move : knightMoves) {
            int newX = getX() + move[0];
            int newY = getY() + move[1];
            
            // Check if the position is on the board
            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                // Valid if empty or enemy piece
                if (board[newY][newX] == null || board[newY][newX].getColor() != getColor()) {
                    validMoves.add(new Coordinate(newX, newY));
                }
            }
        }

        //System.out.println("Knight valid moves: " + validMoves);
        
        return validMoves;
    }

    @Override
    public String toString() {
        if (getColor() == Color.WHITE)
            return "♘";
        else
            return "♞";
    }
    
}
