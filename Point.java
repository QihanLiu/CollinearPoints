import edu.princeton.cs.algs4.StdDraw;
import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    public Point(int x, int y) { // constructs the point (x, y)
        this.x = x;
        this.y = y;
    }

    public void draw() { // draws this point
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) { // draws the line segment from this point to that point
        if (that == null)
            throw new IllegalArgumentException("Null input of drawing.");
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() { // string representation
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that) { // compare two points by y-coordinates, breaking ties by x-coordinates
        if (that == null)
            throw new NullPointerException("Null input of compare.");

        if (this.x == that.x && this.y == that.y) { // same
            return 0;
        }
        if (this.y < that.y || (this.y == that.y && this.x < that.x)) { // less
            return -1;
        }
        return 1; // bigger
    }

    public double slopeTo(Point that) { // the slope between this point and that point
        double diffx = that.x - this.x;
        double diffy = that.y - this.y;

        if (diffx == 0 && diffy == 0) { // same point
            return Double.NEGATIVE_INFINITY;
        }
        if (diffx == 0) { // virtical line
            return Double.POSITIVE_INFINITY;
        }
        if (diffy == 0) { // horizontal line
            return 0.0;
        }
        return diffy / diffx;
    }

    public Comparator<Point> slopeOrder() { // compare two points by slopes they make with this point
        return new Slopecomparator();
    }

    private class Slopecomparator implements Comparator<Point> {
        public int compare(Point arg0, Point arg1) {
            double slope0 = slopeTo(arg0);
            double slope1 = slopeTo(arg1);
            return Double.compare(slope0, slope1);
        }
    }

    public static void main(String[] args) {
    }
}
