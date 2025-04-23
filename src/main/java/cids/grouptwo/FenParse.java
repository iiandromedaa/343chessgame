package cids.grouptwo;

import java.util.List;
import java.util.Map;

import cids.grouptwo.exceptions.FenParseException;
import cids.grouptwo.pieces.*;
import cids.grouptwo.pieces.Piece.Color;

/**
 * This is a utility class meant to parse FEN strings (which is a type of chess notation
 * representing board states) and then return a valid 2d piece array which we can do whatever we
 * want with :]
 */
public final class FenParse {
    
    public static Piece[][] parse(String fen, Map<Piece, Piece> pieceSet) throws FenParseException {
        if (fen == null)
            throw new FenParseException();
        String[] ranks = fen.split("/");
        if (ranks.length != 8)
            throw new FenParseException();
        Piece[][] temp = new Piece[8][8];
        
        int rankCursor = 0;
        int fileCursor = 0;

        for (int i = 0; i < ranks.length; i++) {
            fileCursor = 0;
            for (int j = 0; j < ranks[i].length(); j++) {
                // obviously break if we're gonna out of bounds
                if (fileCursor > 7)
                    break;
                // end once we hit a space, thats metadata that we dont care about
                if (ranks[i].charAt(j) == ' ')
                    break;
                // check for numbers, these will be empty spaces
                try {
                    fileCursor += Integer.parseInt(Character.toString(ranks[i].charAt(j)));
                } catch (NumberFormatException e) {
                    // big stupid switch case incoming
                    switch (ranks[i].charAt(j)) {
                        case 'r':
                            temp[rankCursor][fileCursor] = new Rook(Color.BLACK, 
                                fileCursor, rankCursor);
                            break;
                        case 'R':
                            temp[rankCursor][fileCursor] = new Rook(Color.WHITE, 
                                fileCursor, rankCursor);
                            break;
                        case 'n':
                            temp[rankCursor][fileCursor] = new Knight(Color.BLACK, 
                                fileCursor, rankCursor);
                            break;
                        case 'N':
                            temp[rankCursor][fileCursor] = new Knight(Color.WHITE, 
                                fileCursor, rankCursor);
                            break;
                        case 'b':
                            temp[rankCursor][fileCursor] = new Bishop(Color.BLACK, 
                                fileCursor, rankCursor);
                            break;
                        case 'B':
                            temp[rankCursor][fileCursor] = new Bishop(Color.WHITE, 
                                fileCursor, rankCursor);
                            break;
                        case 'q':
                            temp[rankCursor][fileCursor] = new Queen(Color.BLACK, 
                                fileCursor, rankCursor);
                            break;
                        case 'Q':
                            temp[rankCursor][fileCursor] = new Queen(Color.WHITE, 
                                fileCursor, rankCursor);
                            break;
                        case 'k':
                            temp[rankCursor][fileCursor] = new King(Color.BLACK, 
                                fileCursor, rankCursor);
                            break;
                        case 'K':
                            temp[rankCursor][fileCursor] = new King(Color.WHITE, 
                                fileCursor, rankCursor);
                            break;
                        case 'p':
                            temp[rankCursor][fileCursor] = new Pawn(Color.BLACK, 
                                fileCursor, rankCursor);
                            break;
                        case 'P':
                            temp[rankCursor][fileCursor] = new Pawn(Color.WHITE, 
                                fileCursor, rankCursor);
                            break;
                    }
                    fileCursor++;
                }
            }
            rankCursor++;
        }
        return temp;
    }
    // TODO do the piece set piece replacement shit and also make it apply to black too
    // a working method, athena deliver unto me!!
}
