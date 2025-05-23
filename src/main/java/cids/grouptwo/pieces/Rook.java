package cids.grouptwo.pieces;

import java.util.ArrayList;
import java.util.List;

import cids.grouptwo.Coordinate;

public class Rook extends Piece {

    private final double[][] rookEvalWhite = {
        {  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0},
        {  0.5,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        {  0.0,   0.0, 0.0,  0.5,  0.5,  0.0,  0.0,  0.0}
    };
    
    private final double[][] rookEvalBlack = {
        {  0.0,   0.0, 0.0,  0.5,  0.5,  0.0,  0.0,  0.0},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
        {  0.5,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  0.5},
        {  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0}
        
    };

    private boolean hasMoved = false;

    public Rook(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public String returnName(){
        return "Rook";
    }

    @Override
    public Rook copyPiece(){
        Rook tempPiece = new Rook(this.getColor(), this.getX(), this.getY());
        return tempPiece;
    }

    @Override
    public int returnNumber(){
        return 3;
    }

    @Override
    public int getValue(){
        return 50;
    }


    @Override
    public double getValueOfSpace(int x, int y){
        if(getColor() == Color.WHITE)
            return rookEvalWhite[x][y];
        else 
            return rookEvalBlack[x][y];
    }

    /**
     * Checks if a move is valid for a rook
     * Rooks move horizontally or vertically any number of squares
     */
    @Override
    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        // Check board boundaries
        if (newX < 0 || newX >= 8 || newY < 0 || newY >= 8) {
            return false;
        }
        
        // Cannot move to current position
        if (this.getY() == newY && this.getX() == newX) {
            return false;
        }
    
        // Rook can only move horizontally or vertically
        if (this.getY() != newY && this.getX() != newX) {
            return false;
        }
    
        // Check horizontal movement
        if (this.getY() == newY) {
            int dx = (getX() < newX) ? 1 : -1;
            for (int i = getX() + dx; i != newX; i += dx) {
                if (board[getY()][i] != null)
                    return false;
            }
        } 
        // Check vertical movement
        else {
            int dy = (getY() < newY) ? 1 : -1;
            for (int i = getY() + dy; i != newY; i += dy) {
                if (board[i][getX()] != null)
                    return false;
            }
        }
    
        // Valid move if destination square is empty
        if (board[newY][newX] == null)
            return true;
        
        // Valid move if destination has opponent's piece
        return (board[newY][newX].getColor() != getColor());
    }

    /**
     * Efficiently gets all possible valid moves for the Rook
     */
    @Override
    public List<Coordinate> getValidMoves(Piece[][] board) {
        List<Coordinate> validMoves = new ArrayList<>();
        
        // Define the four orthogonal directions
        int[][] directions = {
            {0, 1},   // Down
            {0, -1},  // Up
            {1, 0},   // Right
            {-1, 0}   // Left
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
        
        return validMoves;
    }

    /**
     * Updates the rook's position and records that it has moved
     */
    @Override
    public void piecePosition(int x, int y) {
        super.piecePosition(x, y);
        hasMoved = true;
    }
    
    /**
     * Determines if the rook has moved (used for castling eligibility)
     * @return true if the rook has moved
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    @Override
    public String toString() {
        if (getColor() == Color.WHITE)
            return "r";
        else
            return "R";
    }

}