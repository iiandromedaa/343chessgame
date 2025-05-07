package cids.grouptwo;

import java.util.Random;

import cids.grouptwo.pieces.Piece;


public class ZorbHash { 
    private static long[][][] ZobristTable;
    private static final Random rand = new Random();
    private static final long turnRandom = rand.nextLong();


    /**
     * Defualt initialise for a basic chess game
     */
    public static void initialise() {
      initialise(6);
    }

    /**
     * This initializes the array to create a Zobrist Hashing, with the input being the number of types
     * of pieces currently used
     * @param numbPiece
     */
      public static void initialise(int numbPiece) {
          ZobristTable = new long[8][8][2*numbPiece];
          for (int i = 0; i < 8; i++) {
              for (int j = 0; j < 8; j++) {
                  for (int k = 0; k < 2*numbPiece; k++) {
                    ZobristTable[i][j][k] = rand.nextLong();
                  }
              }
          }
      }

      private static int indexOf(Piece piece) {
          Integer baseIndex = piece.returnNumber();
          int colorOffset = piece.getColor().toString().equals("BLACK") ? 1 : 0;
          return baseIndex * 2 + colorOffset;
          }

      public static long computeHash(Board board, Boolean turn) {
          long h = 0;
          for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
              if (board.getPieceFromXY(i, j) != null) {
                int piece = indexOf(board.getPieceFromXY(i, j));
                h ^= ZobristTable[i][j][piece];
              }
            }
          }
          if(turn)
              h ^= turnRandom;
          return h;
        }
        public static long updateHash(long currentHash, Board board, int x1, int y1, int x2, int y2) {
            Piece movedPiece = board.getPieceFromXY(x1, y1);
            Piece capturedPiece = board.getPieceFromXY(x2, y2);

            if (movedPiece != null) {
                int pieceAtStart = indexOf(movedPiece);
                currentHash ^= ZobristTable[x1][y1][pieceAtStart];
            }

            if (capturedPiece != null) {
                int capturedPieceIndex = indexOf(capturedPiece);
                currentHash ^= ZobristTable[x2][y2][capturedPieceIndex];
            }

            if (movedPiece != null) {
                int pieceAtEnd = indexOf(movedPiece);
                currentHash ^= ZobristTable[x2][y2][pieceAtEnd];
            }
            currentHash ^= turnRandom;
        
            return currentHash;
        }
    }
