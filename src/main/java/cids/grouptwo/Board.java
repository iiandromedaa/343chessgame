package cids.grouptwo;

public class Board {

    private Piece[][] board;
    /* Chess board parameters */
    private int boardParams = 8;

    /*
     * Modified the code from integers to be objects of type Piece
     * theres an "BoardSquare" piece type now that represents an empty square
     */
    private Piece white = new BoardSquare("white", 0, 0);
    private Piece black = new BoardSquare("black", 0, 0);

    /* Constructor for a basic chess board */
    public Board() {
        board = new Piece[boardParams][boardParams];

        /* loops through and alternates the board colors */
        for (int i = 0; i < boardParams; i++)
            for (int j = 0; j < boardParams; j++) {
                if (i % 2 == 0) {
                    if (j % 2 == 0)
                        board[i][j] = white;
                    else
                        board[i][j] = black;
                } else {
                    if (j % 2 == 0)
                        board[i][j] = black;
                    else
                        board[i][j] = white;
                }
            }
        ;
    }

    /* Displays the chess board */
    public void displayBoard() {
        for (int i = 0; i < boardParams; i++) {
            for (int j = 0; j < boardParams; j++) {
                if (board[i][j] == white) {
                    System.out.print("[" + "W" + "]");
                } else if (board[i][j] == black) {
                    System.out.print("[" + "B" + "]");
                } else {
                    System.out.print("[ ]"); /* crude error checker */
                }
                System.out.println();
            }
        }
    }

}