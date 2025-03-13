package cids.grouptwo;
public class Board {

    private int[][] board;
    /* Chess board parameters */
    private int boardParams = 8;

    /* Figured translating these numbers to sprites would be easier than "black" and "white" 
     * Feel free to adjust to what suits you best, Danielle.
    */
    /* board colors */
    private int black = -1;
    private int white = -2;

    /* Constructor for a basic chess board */
    public Board() {
        board = new int[boardParams][boardParams];
        
        /* loops through and alternates the board colors */
        for (int i = 0; i < boardParams; i++)
            for (int j = 0; j < boardParams; j++)
                {
                    if(i % 2 == 0)
                    {
                        if(j % 2 == 0)
                            board[i][j] = white;
                        else
                            board[i][j] = black;
                    }
                    else
                    {
                        if(j % 2 == 0)
                            board[i][j] = black;
                        else
                            board[i][j] = white;
                    }
                };
    }

    /* Displays the chess board */
    public void displayBoard() {
        for (int i = 0; i < boardParams; i++) {
            for (int j = 0; j < boardParams; j++) {
                System.out.print("[" + board[i][j] + "]");
            }
            System.out.println();
        }
    }
}