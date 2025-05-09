package cids.grouptwo;

public interface KillListener {

    /**
     * event for the end of the game
     * @param turn which player won, 0 white, 1 black
     */
    public void killNotify(int turn);

}
