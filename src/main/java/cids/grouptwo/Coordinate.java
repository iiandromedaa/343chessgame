package cids.grouptwo;

/**
 * one of us was gonna have to make this class sooner or later honestly
 */
public class Coordinate {

    public final int X;
    public final int Y;

    public Coordinate(int x, int y) {
        X = x;
        Y = y;
    }

    @Override
    public String toString() {
        return "x: " + X + " y: " + Y;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinate that = (Coordinate) obj;
        return X == that.X && Y == that.Y;
    }

}
