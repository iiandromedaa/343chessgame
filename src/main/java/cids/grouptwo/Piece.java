package cids.grouptwo;
public class Piece {

    private int x, y;
    private String color, type;
    
    public Piece(int x, int y, String color, String type) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setType(String type) {
        this.type = type;
    }

    /* JackH ~ AI generated move validation, need to review */
    public boolean isValidMove(int newX, int newY) {
        switch (type.toLowerCase()) {
            case "king":
                // King can move one square in any direction
                return Math.abs(newX - x) <= 1 && Math.abs(newY - y) <= 1;

            case "knight":
                // Knight moves in an L-shape
                int dx = Math.abs(newX - x);
                int dy = Math.abs(newY - y);
                return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);

            case "bishop":
                // Bishop moves diagonally
                return Math.abs(newX - x) == Math.abs(newY - y);

            default:
                // Invalid piece type
                return false;
        }
    }

    @Override
    public String toString() {
        return color + " " + type + " at (" + x + ", " + y + ")";
    }
}
