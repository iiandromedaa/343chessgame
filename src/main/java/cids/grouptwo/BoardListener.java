package cids.grouptwo;

import cids.grouptwo.pieces.Piece;

public interface BoardListener {

    /**
     * represents a board square being updated
     * if from and to are the same, the piece was promoted or otherwise swapped
     * @param board the board, duh
     * @param from the origin of the piece move
     * @param to the destination of the piece move
     * @param piece the piece involved in the update
     */
    public void boardUpdate(Board board, Coordinate from, Coordinate to, Piece piece);

}
