package cids.grouptwo;

public abstract class Piece {

    private int x, y;
    private String color;

    public Piece(String color, int x, int y) {
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

    public String getColor() {
        return color;
    }

    public void piecePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isValidMove(int newX, int newY, Piece[][] board) {
        // Default implementation, can be overridden by subclasses
        return false;
    }

}
