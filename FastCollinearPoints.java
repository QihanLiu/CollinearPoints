import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lines = new ArrayList<>();

    public FastCollinearPoints(Point[] rawpoints) { // finds all line segments containing 4 or more points
        Point[] points;
        points = checksort(rawpoints);
        Point[] pointsort;

        int i, first, last;
        for (i = 0; i < points.length; i++) {
            pointsort = points.clone();
            Arrays.sort(pointsort, pointsort[i].slopeOrder()); // now the current point is the 1st element;
            for (first = 1, last = 2; last < pointsort.length; last++) { // now the pointsort is sorted by slope (it is
                                                                         // still points).

                while (pointsort[0].slopeTo(pointsort[first]) == pointsort[0].slopeTo(pointsort[last])) {
                    last++;
                    if (last == pointsort.length)
                        break; // if last moves outside of the array
                }
                if (last - first >= 3 && pointsort[0].compareTo(pointsort[first]) <= 0) { // the current point is the
                                                                                          // 1st one (1st time to
                                                                                          // record)
                    lines.add(new LineSegment(pointsort[0], pointsort[last - 1])); // find the other 3 with same slope
                }
                first = last;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
