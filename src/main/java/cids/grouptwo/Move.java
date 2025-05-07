package cids.grouptwo;

import java.util.Objects;

import cids.grouptwo.pieces.Piece;

public class Move {

    public final Coordinate from;
    public final Coordinate to;
    public final Piece piece;

    Move(Coordinate from, Coordinate to, Piece piece) {
        this.from = from;
        this.to = to;
        this.piece = piece;
    }

    /**
     * compares 2 boards and returns the move difference between them
     * useful for the gui
     * @param oldBoard
     * @param newBoard
     * @return move difference 
     */
    public static Move findDiff(Board oldBoard, Board newBoard) {
        Coordinate from = null;
        Coordinate to = null;
        
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece oldPiece = oldBoard.getPieceFromXY(col, row);
                Piece newPiece = newBoard.getPieceFromXY(col, row);

                if(!Objects.equals(oldPiece, newPiece)) {
                    if (oldPiece != null && newPiece == null) {
                        from = new Coordinate(row, col);
                    } else if ((oldPiece == null && newPiece != null) || 
                        oldPiece != null && newPiece != null && !oldPiece.equals(newPiece)) {
                            to = new Coordinate(row, col);
                        }
                }
            }
        }

        if (from != null && to != null) {
            return new Move(from, to, newBoard.getPieceFromCoordinate(to));
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return from + " | " + to + " | " + piece;
    }
    
}
