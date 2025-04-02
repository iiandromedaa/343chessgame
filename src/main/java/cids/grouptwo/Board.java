package cids.grouptwo;

import cids.grouptwo.pieces.Piece;

public class Board {

    private Piece[][] board;
    /* Chess board parameters */
    private final int BOARDPARAMS = 8;

    /**
     * creates empty board
     */
    public Board() {
        board = new Piece[BOARDPARAMS][BOARDPARAMS];
    }

    /**
     * creates board with set state from FEN notation
     * @param Fen FEN notation
     */
    public Board(String Fen) {
        board = new Piece[BOARDPARAMS][BOARDPARAMS];
        try {
			setBoard(FenParse.parse(Fen));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * displays chess board, only for debug purposes, shows only from
     * black's perspective
     */
    public void displayBoard() {
        Main.clear();
        for (int y = 0; y < BOARDPARAMS; y++) {
            for (int x = 0; x < BOARDPARAMS; x++) {
                if (board[y][x] == null)
                    System.out.print("[ ]");
                else
                    System.out.print("[" + board[y][x].toString() + "]");
            }
            System.out.println();
        }
    }

    /**
     * manually sets piece on board, overwriting what was in that space before<p>
     * use with caution
     * @param piece
     */
    public void setPiece(Piece piece) {
        board[piece.getY()][piece.getX()] = piece;
    }

    private void setBoard(Piece[][] board) throws Exception {
        if (board.length != this.board.length)
            throw new Exception();
        this.board = board;
    }

}