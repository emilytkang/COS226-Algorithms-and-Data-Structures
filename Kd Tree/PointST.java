/* *****************************************************************************
 *  Name:    Emily Kang
 *  NetID:   etkang
 *  Precept: P05
 *
 *
 *  Description: a mutable data type which uses a red-black BST to represent a
 * symbol table whose keys are two-dimensional points
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class PointST<Value> {
    private final RedBlackBST<Point2D, Value> st; // red-black BST symbol table

    // construct an empty symbol table of points
    public PointST() {
        st = new RedBlackBST<Point2D, Value>();
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return st.isEmpty();
    }

    // number of points
    public int size() {
        return st.size();
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null || val == null) throw new IllegalArgumentException();
        st.put(p, val);
    }

    // value associated with point p
    public Value get(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return st.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return st.contains(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        return st.keys();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> queue = new Queue<>();
        for (Point2D p : st.keys()) {
            if (rect.contains(p)) queue.enqueue(p);
        }
        return queue;

    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        Point2D nearest = null;
        double minDist = Double.POSITIVE_INFINITY;
        for (Point2D point : st.keys()) {
            double dist = p.distanceSquaredTo(point);
            if (minDist > dist) {
                minDist = dist;
                nearest = point;
            }
        }
        return nearest;
    }

    // unit testing (required)
    public static void main(String[] args) {
        PointST<Integer> pst = new PointST<Integer>();
        In in = new In(args[0]);
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            pst.put(p, i);
        }

        int m = 20;
        Stopwatch s = new Stopwatch();
        for (int i = 0; i < m; i++) {
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            Point2D p = new Point2D(x, y);
            pst.nearest(p);
        }
        double time = s.elapsedTime();
        System.out.println(time);

        System.out.println(pst.isEmpty());
        System.out.println(pst.size());

        Point2D a = new Point2D(0.2, 0.3);
        Point2D b = new Point2D(0.4, 0.2);
        Point2D c = new Point2D(0.4, 0.5);
        Point2D d = new Point2D(0.3, 0.3);
        Point2D e = new Point2D(0.1, 0.5);
        Point2D f = new Point2D(0.4, 0.4);
        // Point2D z = new Point2D(.2, .3);


        pst.put(a, 0);
        pst.put(b, 1);
        pst.put(c, 2);
        pst.put(d, 3);
        // pst.put(z, 7);

        System.out.println(pst.points());

        System.out.println(pst.contains(a));
        System.out.println(pst.get(a));
        System.out.println(pst.contains(f));

        pst.put(e, 4);
        pst.put(f, 5);

        RectHV r = new RectHV(0.35, 0.35, 0.45, 0.55);
        for (Point2D point : pst.range(r)) {
            System.out.println(point);
        }

        Point2D p = new Point2D(0.425, 0.125);
        System.out.println(pst.nearest(p));

    }

}
