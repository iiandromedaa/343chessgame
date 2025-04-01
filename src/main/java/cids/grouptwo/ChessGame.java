package cids.grouptwo;

import java.util.List;

import cids.grouptwo.roguestuff.Modifier;

/**
 * the core gameplay loop and gamestate is stored here
 */
public class ChessGame {

    /**
     * -1: game over<p>
     * 0: white<p>
     * 1: black
     */
    private int turn;
    private Board board;
    // temporary, we dont know how we wanna store modifiers yet
    private List<Modifier> modifiers;

    public ChessGame() {
        // white first move of course
        turn = 0;
        board = new Board();
    }

    public int getTurn() {
        return turn;
    }

    /**
     * called after player move
     */
    public void step() {
        if (turn == 0) {
            // player turn is over, have the bot do stuff
            turn = 1;
            // HypotheticalChessBot.playSickAssMove();
            step();
        } else if (turn == 1) {
            // if we are stepping on black's turn, then black is finished 
            // and now white is to play
            turn = 0;
        } else {
            // it's not either black's or white's turn, the game must be over
            return;
        }
    }

    /**
     * ends game, call at the end of a round, this value locks the game state but
     * can be reset by other methods
     */
    public void kill() {
        turn = -1;
    }

    /**
     * resets game state, use at beginning of new round
     */
    public void reset() {
        // set turn to white's
        turn = 0;
        // TODO choose random FEN from our cool list of chess midgames
        // TODO set board state to that FEN
    }
    
}
