package cids.grouptwo;

import cids.grouptwo.pieces.Piece;

public interface BoardListener {

    /**
     * represents a board square being updated
     * if from and to are the same, the piece was promoted or otherwise swapped
     * @param board the board, duh
     * @param move the move storing the origin, destination, and piece involved
     */
    public void boardUpdate(Board board, Move move);

    public void boardSet(Board board);

}
