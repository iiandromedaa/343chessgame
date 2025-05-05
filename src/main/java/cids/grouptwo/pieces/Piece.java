package cids.grouptwo.pieces;

import java.util.ArrayList;
import java.util.List;

import cids.grouptwo.Coordinate;

public abstract class Piece {

    private int x, y;
    private Color color;

    public Piece(Color color, int x, int y) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /**
     * this method is used for the Ai to determine which piece is which
     * @return name of piece
     */
    public String returnName(){
        return null;
    }

    public Piece(Color color, Coordinate coordinate) {
        this(color, coordinate.X, coordinate.Y);
    }

    /**
     * This is a method to copy a Piece
     * @return
     */
    public abstract Piece copyPiece();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinate getPosition() {
        return new Coordinate(x, y);
    }

    public Color getColor() {
        return color;
    }

    /**
     * Gives the value of the piece
     * @return piece value
     */
    public int getValue(){
        return 0;
    }

    /**
     * Gives how valuable a space is to a piece
     * @param x
     * @param y
     * @return space evaluation
     */
    public double getValueOfSpace(int x, int y){
        return 0;
    }

    /**
     * Returns an identifying number, easier to sort through
     * @return Piece ID number
     */
    public int returnNumber(){
        return -1;
    }

    public void piecePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * alternate position method if you prefer the coordinate class
     * @param coordinate coordinate to set the piece position to
     */
    public void piecePosition(Coordinate coordinate) {
        this.x = coordinate.X;
        this.y = coordinate.Y;
    }

    /**
     * do not override this method in the piece classes, i mean, you can, but it wont make a difference
     * the calls to isValidMove will be overriden in each subclass
     * <p>internally, calling isValidMove() is a truncation of this.isValidMove(), so in the context
     * of the subclass, it calls the overriden isValidMove(), so ya, dont worry about this
     * @return list of coordinates for all valid moves
     */
    public List<Coordinate> getValidMoves(Piece[][] board) {
        List<Coordinate> retList = new ArrayList<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (isValidMove(col, row, board))
                    retList.add(new Coordinate(col, row));
            }
        }
        return retList;
    }

    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        // Default implementation, can be overridden by subclasses
        return false;
    }

    public enum Color {

        WHITE, BLACK, DEBUG;

    }

}
