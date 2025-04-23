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

    /**
     * handles coordinate comparison, more readable than manually comparing both x and y
     * every single time we want to check if 2 coordinates are the "Same" (not literally
     * the same reference but equal X and Y values)
     * @param obj object to be compared (automatically false if not coordinate)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinate) {
            return (((Coordinate)obj).X == X && ((Coordinate)obj).Y == Y);
        } else
            return false;
    }

}
