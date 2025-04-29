package cids.grouptwo;

import java.util.HashMap;
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
    
    public static Piece[][] parse(String fen, Map<Piece, Piece> playerPieceSet) throws FenParseException {
        if (fen == null)
            throw new FenParseException();
        String[] ranks = fen.split("/");
        if (ranks.length != 8)
            throw new FenParseException();
        Piece[][] temp = new Piece[8][8];

        // make a shallow copy of the player pieces map, youll see why later
        Map<Piece, Piece> playerSetCopy = new HashMap<>(playerPieceSet);
        
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
                    // big stupid switch incoming
                    switch (ranks[i].charAt(j)) {
                        case 'r':
                            temp[rankCursor][fileCursor] = new Rook(Color.BLACK, 
                                fileCursor, rankCursor);
                            break;
                        case 'R':
                            replaceHandler(temp, playerSetCopy, 
                                Rook.class, rankCursor, fileCursor);
                            break;
                        case 'n':
                            temp[rankCursor][fileCursor] = new Knight(Color.BLACK, 
                                fileCursor, rankCursor);
                            break;
                        case 'N':
                            replaceHandler(temp, playerSetCopy, 
                                Knight.class, rankCursor, fileCursor);
                            break;
                        case 'b':
                            temp[rankCursor][fileCursor] = new Bishop(Color.BLACK, 
                                fileCursor, rankCursor);
                            break;
                        case 'B':
                            replaceHandler(temp, playerSetCopy, 
                                Bishop.class, rankCursor, fileCursor);
                            break;
                        case 'q':
                            temp[rankCursor][fileCursor] = new Queen(Color.BLACK, 
                                fileCursor, rankCursor);
                            break;
                        case 'Q':
                            replaceHandler(temp, playerSetCopy, 
                                Queen.class, rankCursor, fileCursor);
                            break;
                        case 'k':
                            temp[rankCursor][fileCursor] = new King(Color.BLACK, 
                                fileCursor, rankCursor);
                            break;
                        case 'K':
                            replaceHandler(temp, playerSetCopy, 
                                King.class, rankCursor, fileCursor);
                            break;
                        case 'p':
                            temp[rankCursor][fileCursor] = new Pawn(Color.BLACK, 
                                fileCursor, rankCursor);
                            break;
                        case 'P':
                            replaceHandler(temp, playerSetCopy, 
                                Pawn.class, rankCursor, fileCursor);
                            break;
                    }
                    fileCursor++;
                }
            }
            rankCursor++;
        }
        return temp;
    }

    /**
     * lol i cannot believe java let me get away with this shit<p>
     * i only did this to make the ugly switch case in the prase method
     * slightly more readable, but in doing this i've made this code 100x harder to
     * maintain, thats ok though its worth it for the Aesthetic
     */
    private static <P extends Piece> void replaceHandler(Piece[][] temp, 
        Map<Piece, Piece> playerSetCopy, Class<P> type, int rankCursor, int fileCursor) {
        for (Piece p : playerSetCopy.keySet()) {
            if (type.isInstance(p)) {
                temp[rankCursor][fileCursor] = playerSetCopy.get(p);
                playerSetCopy.get(p).piecePosition(fileCursor, rankCursor);
                playerSetCopy.remove(p);
                return;
            }
        }
    }

}
