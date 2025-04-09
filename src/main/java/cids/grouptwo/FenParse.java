package cids.grouptwo;

import java.util.Map;

import cids.grouptwo.exceptions.FenParseException;
import cids.grouptwo.pieces.Piece;

/**
 * This is a utility class meant to parse FEN strings (which is a type of chess notation
 * representing board states) and then return a valid 2d piece array which we can do whatever we
 * want with :]
 */
public final class FenParse {
    
    public static Piece[][] parse(String fen, Map<Piece, Piece> pieceSet) throws FenParseException {
        if (fen == null)
            return null;

        return null;
    }

}
