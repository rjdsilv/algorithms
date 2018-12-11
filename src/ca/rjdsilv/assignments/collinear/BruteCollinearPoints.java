package ca.rjdsilv.assignments.collinear;/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ResizingArrayBag;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private final Point[] points;
    private int count = 0;
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        if (null == points)        throw new IllegalArgumentException("Array can't be null!");

        this.points = points.clone();
        if (hasNullPoint(this.points))  throw new IllegalArgumentException("Array can't have null points.");
        if (hasDuplicates(this.points)) throw new IllegalArgumentException("Array can't have duplicated points.");

        Arrays.sort(this.points, this.points[0].slopeOrder());
        Arrays.sort(this.points);
        segments = segments();
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
        System.out.println();
    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        if (null == segments) {
            if (points.length < 4) {
                return new LineSegment[0];
            }

            final ResizingArrayBag<LineSegment> bp = new ResizingArrayBag<>();
            final int len = points.length;

            // Find the segments.
            for (int p = 0; p < len - 3; p++) {
                for (int q = p + 1; q < len - 2; q++) {
                    final double spq = points[p].slopeTo(points[q]);

                    for (int r = q + 1; r < len - 1; r++) {
                        final double spr = points[p].slopeTo(points[r]);

                        if (spq == spr) {
                            for (int s = r + 1; s < len; s++) {
                                final double sps = points[p].slopeTo(points[s]);
                                if (spr == sps) {
                                    count++;
                                    bp.add(new LineSegment(points[p], points[s]));
                                }
                            }
                        }
                    }
                }
            }

            // Create the array.
            final LineSegment[] lineSegments = new LineSegment[bp.size()];
            int i = 0;
            for (LineSegment ls : bp) {
                lineSegments[i++] = ls;
            }
            return lineSegments;
        }

        // Return the segments clone.
        return segments.clone();
    }

    private boolean hasNullPoint(Point[] pts) {
        for (Point p : pts) {
            if (null == p) return true;
        }

        return false;
    }

    private boolean hasDuplicates(Point[] pts) {
        if (pts.length <= 1) return false;

        Arrays.sort(pts);
        for (int i = 1; i < pts.length; i++) {
            if (pts[i - 1].compareTo(pts[i]) == 0) return true;
        }

        return false;
    }
}
