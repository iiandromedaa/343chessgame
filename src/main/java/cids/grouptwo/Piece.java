package cids.grouptwo;

public abstract class Piece {
    private int x, y;
    private String color;

    public Piece(String color, int x, int y) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColor() {
        return color;
    }

    public void piecePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // // /* JackH ~ AI generated move validation, need to review
    // // public boolean isValidMove(int newX, int newY
    //        switch (type.toLowerCase()
    //            case "kin
    //                // King can move one square in any direct
    //                return Math.abs(newX - x) <= 1 && Math.abs(newY - y) <=

    //            case "knigh
    //                // Knight moves in an L-sh
    //                int dx = Math.abs(newX - 
    //                int dy = Math.abs(newY - 
    //                return (dx == 2 && dy == 1) || (dx == 1 && dy == 

    //            case "bisho
    //                // Bishop moves diagona
    //                return Math.abs(newX - x) == Math.abs(newY - 

    //            defau
    //                // Invalid piece t
    //                return fal
         
    // // }

}
