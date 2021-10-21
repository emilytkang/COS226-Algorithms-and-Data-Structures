/* *****************************************************************************
 *  Name:    Emily Kang
 *  NetID:   etkang
 *  Precept: P05
 *
 *  Description:  client program to test RandomizedQueue.java
 *  reads a sequence of strings from standard input and enqueues them into a
 *  RandomizedQueue. dequeues and prints k strings, where k is an integer
 *  command-line argument
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> r = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            r.enqueue(s);
        }

        for (int i = 0; i < k; i++) {
            System.out.println(r.dequeue());
        }

    }
}
