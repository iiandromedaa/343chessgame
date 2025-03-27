package cids.grouptwo;

public class Turn {
    //tracks current turn
    private String turn = "white";
    //stores which turn it isn't
    private String nTurn = "black";
    
    //swaps the turns
    public void turnChange(){
        String temp = turn;
        turn = nTurn;
        nTurn = temp;
    }

    //sets turn to 2 to end the game
    public void gameEnd(){
        turn = "end";
    }

    //method incase we need to reset the turn order
    public void resetTurn(){
        turn = "white";
        nTurn = "black";
    }

    //used to get the current turn
    public String getTurn(){
        return turn;
    }

}
