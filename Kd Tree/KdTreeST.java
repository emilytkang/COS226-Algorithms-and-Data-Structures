/* *****************************************************************************
 *  Name:    Emily Kang
 *  NetID:   etkang
 *  Precept: P05
 *
 *  Description:  a mutable data type that uses a 2d-tree to represent a
 * symbol table whose keys are two-dimensional points
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class KdTreeST<Value> {
    private static final boolean VERTICAL = true;
    private static final double MAX = Double.POSITIVE_INFINITY;
    private static final double MIN = Double.NEGATIVE_INFINITY;
    private int n; // number of keys
    private Node root; // root of BST

    // construct an empty symbol table of points
    public KdTreeST() {
        n = 0;
        root = null;
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return (n == 0);
    }

    // number of points
    public int size() {
        return n;
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null || val == null) throw new IllegalArgumentException();
        root = put(root, p, val, VERTICAL, MIN, MIN, MAX, MAX);
    }

    // private helper method to associate the value val with point p
    private Node put(Node x, Point2D p, Value val, boolean vertical, double
            xmin, double ymin, double xmax, double ymax) {
        // base case --> empty place in tree is reached
        if (x == null) {
            n++;
            RectHV r = new RectHV(xmin, ymin, xmax, ymax);
            return new Node(p, val, vertical, r);
        }
        int cmp = x.compareTo(p);
        // if target point is less than node.point, left subtree is traversed
        if (cmp < 0) {
            if (vertical) xmax = x.p.x();
            else ymax = x.p.y();
            x.left = put(x.left, p, val, !vertical, xmin, ymin, xmax, ymax);
        }
        // if target point is greater than node.point, right subtree is traversed
        else if (cmp > 0) {
            if (vertical) xmin = x.p.x();
            else ymin = x.p.y();
            x.right = put(x.right, p, val, !vertical, xmin, ymin, xmax, ymax);
        }
        // changes the value associated with x (occurs when a point is inserted
        // twice)
        else {
            x.val = val;
        }
        return x;
    }

    // private helper nested class representing a Node data type
    private class Node {
        private final Point2D p;     // the point
        private Value val;     // the symbol table maps the point to this value
        private Node left;       // the left/bottom subtree
        private Node right;       // the right/top subtree
        private final boolean vertical; // orientation
        private final RectHV rect;   // the axis-aligned rectangle corresponding
        // to this node

        // constructor
        public Node(Point2D point, Value value, boolean b, RectHV r) {
            p = point;
            val = value;
            vertical = b;
            rect = r;
        }

        // compares Node to point
        public int compareTo(Point2D point) {
            int cmp;
            if (vertical) {
                cmp = Double.compare(point.x(), p.x());
            }
            else cmp = Double.compare(point.y(), p.y());
            if (cmp == 0 && !p.equals(point)) {
                cmp = 1;
            }
            return cmp;
        }

    }

    // value associated with point p
    public Value get(Point2D p) {
        return get(root, p);
    }

    // private helper method to get value associated with point p
    private Value get(Node x, Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (x == null) return null;
        int cmp = x.compareTo(p);
        if (cmp < 0) return get(x.left, p);
        else if (cmp > 0) return get(x.right, p);
        else {
            return x.val;
        }
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return (get(p) != null);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        Queue<Node> tree = new Queue<Node>();
        Queue<Point2D> queue = new Queue<Point2D>();
        tree.enqueue(root);
        while (!tree.isEmpty()) {
            Node x = tree.dequeue();
            if (x == null) continue;
            queue.enqueue(x.p);
            tree.enqueue(x.left);
            tree.enqueue(x.right);
        }
        return queue;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> queue = new Queue<Point2D>();
        range(queue, rect, root);
        return queue;

    }

    // private helper method to find all points that are inside the rectangle
    private void range(Queue<Point2D> queue, RectHV rect, Node x) {
        if (x == null) return;
        if (!rect.intersects(x.rect)) return;
        if (rect.contains(x.p)) queue.enqueue(x.p);
        range(queue, rect, x.left);
        range(queue, rect, x.right);
    }


    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;

        return nearest(root, p, root.p);
    }

    // private helper method to locate nearest neighbor of point p
    private Point2D nearest(Node x, Point2D target, Point2D p) {
        Point2D champion = p;
        if (x == null) return champion;

        double minDist = champion.distanceSquaredTo(target);
        double dist = x.rect.distanceSquaredTo(target);

        // if the bounding box is not closer than best so far, return
        if (!(dist < minDist)) return champion;

        double currDist = x.p.distanceSquaredTo(target);

        // check if the point is closer than best so far
        if (currDist < minDist) {
            champion = x.p;
        }

        int cmp = x.compareTo(target);
        // if left or below, go left then right
        if (cmp < 0) {
            champion = nearest(x.left, target, champion);
            champion = nearest(x.right, target, champion);
        }
        // else go right then left
        else {
            champion = nearest(x.right, target, champion);
            champion = nearest(x.left, target, champion);
        }

        return champion;


    }


    // unit testing (required)
    public static void main(String[] args) {
        KdTreeST<Integer> kd = new KdTreeST<Integer>();
        In in = new In(args[0]);
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kd.put(p, i);
        }

        int m = 300000;
        Stopwatch s = new Stopwatch();
        for (int i = 0; i < m; i++) {
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            Point2D p = new Point2D(x, y);
            kd.nearest(p);
        }
        double time = s.elapsedTime();
        System.out.println(time);

        System.out.println();
        Point2D a = new Point2D(0.2, 0.3);
        Point2D b = new Point2D(0.4, 0.2);
        Point2D c = new Point2D(0.4, 0.5);
        Point2D d = new Point2D(0.3, 0.3);
        Point2D e = new Point2D(0.1, 0.5);
        Point2D f = new Point2D(0.4, 0.4);
        // Point2D g = new Point2D(0.1, 0.1);
        // Point2D z = new Point2D(.2, .3);


        System.out.println(kd.isEmpty());
        kd.put(a, 0);
        kd.put(b, 1);
        kd.put(c, 2);
        kd.put(d, 3);
        kd.put(e, 4);
        kd.put(f, 5);
        // kd.put(g, 6);
        // kd.put(z, 7);
        System.out.println(kd.size());
        System.out.println(kd.points());
        System.out.println(kd.get(a));

        RectHV r = new RectHV(0.35, 0.35, 0.45, 0.55);

        for (Point2D point : kd.range(r)) {
            System.out.println(point.x() + " " + point.y());
        }

        Point2D p = new Point2D(0.425, 0.125);
        System.out.println(kd.nearest(p));

        Point2D p1 = new Point2D(0.5, 0.4);
        kd.put(p1, 12);
        System.out.println(kd.get(p1));
        System.out.println(kd.contains(p1));
        Point2D p2 = new Point2D(0.5, 0.5);
        System.out.println(kd.get(p2));
        System.out.println(kd.contains(p2));

        KdTreeST<String> test = new KdTreeST<String>();
        Point2D a1 = new Point2D(0.5, 1.0);
        Point2D b1 = new Point2D(0.75, 0.25);
        Point2D c1 = new Point2D(0.75, 0.75);
        Point2D d1 = new Point2D(0.5, 0.5);

        test.put(a1, "A");
        test.put(b1, "B");
        test.put(c1, "C");
        test.put(d1, "D");
        StringBuilder sb = new StringBuilder();
        for (Point2D point : test.points()) {
            sb.append(test.get(point));
        }
        System.out.println(sb);


    }

}
