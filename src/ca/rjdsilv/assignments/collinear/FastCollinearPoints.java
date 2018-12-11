package ca.rjdsilv.assignments.collinear;

import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ResizingArrayBag;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private int count = 0;
    private final Point[] points;
    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        if (null == points)        throw new IllegalArgumentException("Array can't be null!");

        this.points = points.clone();
        if (hasNullPoint(this.points))  throw new IllegalArgumentException("Array can't have null points.");
        if (hasDuplicates(this.points)) throw new IllegalArgumentException("Array can't have duplicated points.");
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        if (null != segments) {
            return segments.clone();
        }

        if (points.length < 4) {
            return new LineSegment[0];
        }

        final ResizingArrayBag<LineSegment> bp = new ResizingArrayBag<>();
        final int len = points.length;
        final BST<Double, ResizingArrayBag<Point>> slopeBst = new BST<>();

        Arrays.sort(points, points[0].slopeOrder());
        Arrays.sort(points);

        for (int p = 0; p < len; p++) {
            // Copy array so we do not lose the reference points.
            final Point[] copy = new Point[len - p];
            System.arraycopy(points, p, copy, 0, len - p);
            Arrays.sort(copy, copy[0].slopeOrder());

            int counter = 1;
            double refSlope = copy[0].slopeTo(copy[0]);
            ResizingArrayBag<Point> pos = new ResizingArrayBag<>();
            pos.add(copy[0]);

            for (int q = 1; q < copy.length; q++) {
                double currSlope = copy[0].slopeTo(copy[q]);
                if (refSlope == currSlope) {
                    counter++;
                    pos.add(copy[q]);
                }
                else {
                    final double spq = copy[0].slopeTo(copy[q - 1]);
                    addLineSegment(bp, slopeBst, copy, counter, pos, q, spq);
                    refSlope = currSlope;
                    counter = 1;
                    pos = new ResizingArrayBag<>();
                }
            }

            final int q = copy.length;
            final double spq = copy[0].slopeTo(copy[q - 1]);
            addLineSegment(bp, slopeBst, copy, counter, pos, q, spq);
        }

        final LineSegment[] lineSegments = new LineSegment[bp.size()];
        int i = 0;
        for (LineSegment ls : bp) {
            lineSegments[i++] = ls;
        }
        return lineSegments;
    }

    private void addLineSegment(ResizingArrayBag<LineSegment> bp,
                                BST<Double, ResizingArrayBag<Point>> slopeBst,
                                Point[] copy,
                                int counter,
                                ResizingArrayBag<Point> pos,
                                int q,
                                double spq) {
        if (counter >= 3) {
            if (!slopeBst.contains(spq)) {
                count++;
                bp.add(new LineSegment(copy[0], copy[q - 1]));
                slopeBst.put(spq, pos);
            }
            else {
                boolean addSegment = true;
                ResizingArrayBag<Point> value = slopeBst.get(spq);
                for (Point pp : value) {
                    if (pp.equals(copy[0]) || pp.equals(copy[q - 1])) {
                        addSegment = false;
                        break;
                    }
                }

                if (addSegment) {
                    count++;
                    bp.add(new LineSegment(copy[0], copy[q - 1]));
                    value.add(copy[0]);
                    value.add(copy[q - 1]);
                }
            }
        }
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
