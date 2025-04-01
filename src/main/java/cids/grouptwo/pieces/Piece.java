package cids.grouptwo.pieces;

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
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
    public List<Coordinate> getValidMoves() {
        return null;
    }

    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        // Default implementation, can be overridden by subclasses
        return false;
    }

    public enum Color {

        WHITE, BLACK;

    }

}
