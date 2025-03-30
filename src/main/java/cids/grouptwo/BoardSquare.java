package cids.grouptwo;

public class BoardSquare extends Piece {
    private boolean isOccupied;
    private Piece piece;

    public BoardSquare(String color, int x, int y) {
        super(color, x, y);
        this.isOccupied = false;
        this.piece = null;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void placePiece(Piece piece) {
        this.piece = piece;
        this.isOccupied = true;
    }

    public void removePiece() {
        this.piece = null;
        this.isOccupied = false;
    }

    public Piece getPiece() {
        return piece;
    }
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

}
