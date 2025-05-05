package cids.grouptwo.pieces;

import java.util.ArrayList;
import java.util.List;

import cids.grouptwo.Coordinate;

public class Queen extends Piece {

    private final double[][] evalQueen = {
        { -2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0},
        { -1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0},
        { -1.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -1.0},
        { -0.5,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5},
        {  0.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5},
        { -1.0,  0.5,  0.5,  0.5,  0.5,  0.5,  0.0, -1.0},
        { -1.0,  0.0,  0.5,  0.0,  0.0,  0.0,  0.0, -1.0},
        { -2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0}
    };

    public Queen(Color color, int x, int y) {
       super(color, x, y);
    }

    @Override
    public String returnName(){
        return "Queen";
    }
    
    @Override
    public Queen copyPiece(){
        Queen tempPiece = new Queen(this.getColor(), this.getX(), this.getY());
        return tempPiece;
    }

    @Override
    public int returnNumber(){
        return 4;
    }

    @Override
    public int getValue(){
        return 90;
    }


    @Override
    public double getValueOfSpace(int x, int y){
        return evalQueen[x][y];
    }

    /**
     * Validates moves for the Queen piece
     * Queens can move horizontally, vertically or diagonally any number of squares
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
            
        // Queen combines movement of Rook and Bishop
        // Create temporary pieces to reuse their movement validation
        Rook rookMove = new Rook(this.getColor(), this.getX(), this.getY());
        Bishop bishopMove = new Bishop(this.getColor(), this.getX(), this.getY());

        // Valid if move follows either rook or bishop movement pattern
        return rookMove.isValidMove(newX, newY, board) || bishopMove.isValidMove(newX, newY, board);
    }

     /**
     * Efficiently gets all possible valid moves for the Queen
     */
    @Override
    public List<Coordinate> getValidMoves(Piece[][] board) {
        List<Coordinate> validMoves = new ArrayList<>();
        
        // Define all eight directions (horizontal, vertical, and diagonal)
        int[][] directions = {
            {0, 1}, {0, -1}, {1, 0}, {-1, 0},  // Rook-like moves
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1} // Bishop-like moves
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

        //System.out.println("Queen valid moves: " + validMoves);
        
        return validMoves;
    }

    @Override
    public String toString() {
        if (getColor() == Color.WHITE)
            return "q";
        else
            return "Q";
    }
}