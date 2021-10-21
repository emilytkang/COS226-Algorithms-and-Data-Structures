/* *****************************************************************************
 *  Name:    Emily Kang
 *  NetID:   etkang
 *  Precept: P05
 *
 *  Partner Name:    Eugene Liu
 *  Partner NetID:   el25
 *  Partner Precept: P07
 *
 *  Description: BurrowsWheeler transform and inverseTransform
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

    private static final int R = 256; // static variable R

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {

        // read input
        String s = BinaryStdIn.readString();
        int n = s.length();
        CircularSuffixArray csa = new CircularSuffixArray(s);

        int first = 0;
        for (int i = 0; i < csa.length(); i++) {
            if (csa.index(i) == 0) first = i;
        }

        BinaryStdOut.write(first);
        for (int i = 0; i < csa.length(); i++) {
            BinaryStdOut.write(s.charAt((csa.index(i) - 1 + n) % n));
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        int n = s.length();

        char[] t = new char[n];
        for (int i = 0; i < n; i++) {
            t[i] = s.charAt(i);
        }

        int[] next = new int[n];
        int[] count = new int[R + 1];
        char[] row = new char[n];

        // key-indexed counting algorithm
        // [code adapted from 5.1 lecture slides]
        for (int i = 0; i < n; i++) {
            int index = t[i] + 1;
            count[index]++;
        }
        for (int r = 0; r < R; r++) {
            count[r + 1] += count[r];
        }
        for (int i = 0; i < n; i++) {
            next[count[t[i]]] = i;
            row[count[t[i]]++] = t[i];
        }
        for (int i = 0; i < n; i++) {
            BinaryStdOut.write(row[first]);
            first = next[first];
        }

        BinaryStdOut.close();
    }


    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
    }

}
