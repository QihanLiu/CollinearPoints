import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lines = new ArrayList<>();

    public BruteCollinearPoints(Point[] rawpoints) { // finds all line segments containing 4 points
        Point[] points;
        points = checksort(rawpoints);
        double slope12, slope23, slope34;
        int p1, p2, p3, p4;
        for (p1 = 0; p1 < points.length; p1++) {
            for (p2 = p1 + 1; p2 < points.length; p2++) {
                slope12 = points[p1].slopeTo(points[p2]);
                for (p3 = p2 + 1; p3 < points.length; p3++) {
                    slope23 = points[p2].slopeTo(points[p3]);
                    if (slope12 != slope23)
                        continue; // check if the first 3 point in a line
                    for (p4 = p3 + 1; p4 < points.length; p4++) {
                        slope34 = points[p3].slopeTo(points[p4]);
                        if (slope23 != slope34)
                            continue; // check if the 4th point in the same line
                        lines.add(new LineSegment(points[p1], points[p4]));
                    }
                }
            }
        }
    }

    public int numberOfSegments() { // the number of line segments
        return lines.size();
    }

    public LineSegment[] segments() { // the line segments
        return lines.toArray(new LineSegment[lines.size()]);
    }

    private Point[] checksort(Point[] rawpoints) { // check the input points (null or duplicate) and sort it
        if (rawpoints == null) {
            throw new IllegalArgumentException("The array \"Points\" is null.");
        }
        for (Point point : rawpoints) {
            if (point == null) {
                throw new IllegalArgumentException("The array \"Points\" contains null elements.");
            }
        }

        Point[] points = rawpoints.clone();
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("The array \"Points\" contains duplicate elements.");
            }
        }
        return points;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}