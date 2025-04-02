package cids.grouptwo;

import cids.grouptwo.pieces.Piece;
import java.util.Random;

public class Board {
    
    private boolean[][] obstacles;
    private Random random = new Random();

    private Piece[][] board;
    /* Chess board parameters */
    private final int BOARDPARAMS = 8;

    /**
     * creates empty board
     */
    public Board() {
        board = new Piece[BOARDPARAMS][BOARDPARAMS];
        obstacles = new boolean[BOARDPARAMS][BOARDPARAMS];
    }

    /**
     * creates board with set state from FEN notation
     * @param Fen FEN notation
     */
    public Board(String Fen) {
        board = new Piece[BOARDPARAMS][BOARDPARAMS];
        obstacles = new boolean[BOARDPARAMS][BOARDPARAMS];
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

    /* 10% chance of spawning an obstacle on the board */
    public void attemptObstacleSpawn() {
        if (random.nextInt(100) < 10) {
            int x, y;
            do {
                x = random.nextInt(BOARDPARAMS);
                y = random.nextInt(BOARDPARAMS);
            } while (board[y][x] != null || obstacles[y][x]);

            obstacles[y][x] = true;
            System.out.println("Obstacle spawned at (" + x + ", " + y + ")");
        }
    }

    /* method to clear obstacles (50% chance each turn - can be changed to whatever) */
    public void clearObstacles() {
        for (int y = 0; y < BOARDPARAMS; y++) {
            for (int x = 0; x < BOARDPARAMS; x++) {
                if (obstacles[y][x] && random.nextInt(100) < 50) {
                    obstacles[y][x] = false;
                    
                }
            }
        }
    }

    public boolean isBlocked(int x, int y) {
        return obstacles[y][x];
    }


}