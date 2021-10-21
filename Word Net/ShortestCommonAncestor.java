/* *****************************************************************************
 *  Name:    Emily Kang
 *  NetID:   etkang
 *  Precept: P05
 *
 *  Partner Name:    Eugene Liu
 *  Partner NetID:   el25
 *  Partner Precept: P07
 *
 *  Description:  immutable data type that takes a rooted DAG as an argument
 *  and calculates the length of the shortest ancestral path and the shortest
 * common ancestor either among two vertices v and w or two vertex subsets A and B
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

public class ShortestCommonAncestor {
    private static final boolean LENGTH = true; // calculating shortest length
    private final Digraph graph; // rooted DAG

    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {
        if (G == null) throw new IllegalArgumentException("argument is null");

        DirectedCycle dCycle = new DirectedCycle(G);

        // a Digraph is a rooted DAG if it has one vertex whose outdegree is 0
        // and it is acyclic

        // check if acyclic
        if (dCycle.hasCycle()) throw new
                IllegalArgumentException("argument is cyclic");

        // check if digraph is rooted
        boolean rooted = false;
        for (int i = 0; i < G.V(); i++) {
            if (G.outdegree(i) == 0) {
                if (rooted) throw new
                        IllegalArgumentException("argument is not rooted");
                rooted = true;
            }
        }
        if (!rooted) throw new IllegalArgumentException("argument is not rooted");

        // makes a deep copy of G
        graph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        validate(v);
        validate(w);
        BreadthFirstDirectedPaths bf1 = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bf2 = new BreadthFirstDirectedPaths(graph, w);
        return bfApplication(bf1, bf2, LENGTH);

    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        validate(v);
        validate(w);
        BreadthFirstDirectedPaths bf1 = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bf2 = new BreadthFirstDirectedPaths(graph, w);
        return bfApplication(bf1, bf2, !LENGTH);

    }

    // private helper method utilizing BreadthFirstDirectedPaths.java
    // returns either sca or shortest ancestral path length
    private int bfApplication(BreadthFirstDirectedPaths bf1, BreadthFirstDirectedPaths
            bf2, boolean length) {
        int min = 2 * graph.E() + 1; // length of shortest ancestral path
        int ancestor = -1; // shortest common ancestor
        for (int i = 0; i < graph.V(); i++) {
            // does a common ancestor exist
            if (bf1.hasPathTo(i) && bf2.hasPathTo(i)) {
                // if common ancestor exists, what is the length of ancestral path
                int path = bf1.distTo(i) + bf2.distTo(i);
                // update shortest ancestral path length, if necessary
                if (path < min) {
                    min = path;
                    ancestor = i;
                }
            }
        }

        // calculating path length or ancestor
        if (length) return min;
        else return ancestor;
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        validate(subsetA);
        validate(subsetB);
        BreadthFirstDirectedPaths bf1 = new BreadthFirstDirectedPaths(graph, subsetA);
        BreadthFirstDirectedPaths bf2 = new BreadthFirstDirectedPaths(graph, subsetB);
        return bfApplication(bf1, bf2, LENGTH);

    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        validate(subsetA);
        validate(subsetB);
        BreadthFirstDirectedPaths bf1 = new BreadthFirstDirectedPaths(graph, subsetA);
        BreadthFirstDirectedPaths bf2 = new BreadthFirstDirectedPaths(graph, subsetB);
        return bfApplication(bf1, bf2, !LENGTH);

    }

    // check if arguments are null or empty
    private void validate(Iterable<Integer> a) {
        if (a == null || !a.iterator().hasNext())
            throw new IllegalArgumentException("iterable null or empty");
    }

    // check if vertex is outside prescribed range
    private void validate(int a) {
        if (a < 0 || a > graph.V())
            throw new IllegalArgumentException("vertex is outside prescribed "
                                                       + "range");
    }

    // unit testing (required)
    public static void main(String[] args) {
        In in = new In("digraph1.txt");
        Digraph g = new Digraph(in);
        ShortestCommonAncestor s = new ShortestCommonAncestor(g);

        // shortest common ancestor: 1
        System.out.println("shortest common ancestor: " + s.ancestor(3, 10));
        // shortest ancestral path length: 4
        System.out.println("shortest ancestral path length: " + s.length(3, 10));

        // vertex outside of prescribed range  --> illegal argument exception thrown
        try {
            System.out.println(s.length(3, 15));
        }
        catch (IllegalArgumentException e) {
            System.out.println("Illegal Argument");
        }

        // subset A
        ArrayList<Integer> a = new ArrayList<>();
        a.add(13);
        a.add(23);
        a.add(24);

        // subset B
        ArrayList<Integer> b = new ArrayList<>();
        b.add(6);
        b.add(16);
        b.add(17);

        in = new In("digraph25.txt");
        g = new Digraph(in);
        s = new ShortestCommonAncestor(g);

        // shortest common ancestor: 3
        System.out.println("shortest common ancestor: " + s.ancestorSubset(a, b));
        // shortest ancestral path length: 4
        System.out.println("shortest ancestral path length: " + s.lengthSubset(a, b));

        // empty digraph --> illegal argument exception thrown
        /* try {
            Digraph empty = new Digraph(5);
            s = new ShortestCommonAncestor(empty);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Empty digraph");
        }*/


    }

}
