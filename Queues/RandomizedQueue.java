/* *****************************************************************************
 *  Name:    Emily Kang
 *  NetID:   etkang
 *  Precept: P05
 *
 *  Description:  implementation of RandomizedQueue data type --> similar to
 *  a stack or queue, except that the item removed is chosen uniformly among
 *  items in the data structure
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    /*
       @citation adapted from: https://algs4.cs.princeton.edu/13stacks/
       ResizingArrayStack.java.html. Accessed 03/01/2021.
     */
    private static final int INIT_CAPACITY = 8;
    private int n; // number of elements in RandomizedQueue
    private Item[] a; // resizeable array to hold elements

    // construct an empty randomized queue
    public RandomizedQueue() {
        n = 0;
        a = (Item[]) new Object[INIT_CAPACITY];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (n == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }


    // resizes the array a[]
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = a[i];
        }
        a = copy;

    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == a.length) {
            resize(2 * a.length);
        }
        a[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniform(n);
        Item item = a[index];
        a[index] = a[n - 1];
        a[n - 1] = null;
        n--;


        // shrink size of array if necessary
        if (n > 0 && n == a.length / 4) {
            resize(a.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniform(n);
        return a[index];

    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();

    }

    // RandomizedQueue iterator
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i; // int that tracks number of elements left to iterate
        private Item[] items; // new array of non-null elements of RandomizedQueue

        // RandomizedQueue iterator constructor
        // fill new array of non-null elements in a[]
        // shuffles this new array
        public RandomizedQueueIterator() {
            i = n - 1;
            items = (Item[]) new Object[n];
            int index = 0;
            while (index < n) {
                items[index] = a[index];
                index++;
            }
            StdRandom.shuffle(items);
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return items[i--];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> r = new RandomizedQueue<Integer>();
        System.out.println("true: " + r.isEmpty());
        int[] input = { 0, 1, 2, 3, 4, 5 };
        for (int a : input) {
            r.enqueue(a);
        }

        System.out.println("false: " + r.isEmpty());

        Iterator<Integer> itr1 = r.iterator();
        Iterator<Integer> itr2 = r.iterator();

        while (itr1.hasNext()) {
            System.out.print(itr1.next() + " ");
        }
        System.out.println();

        while (itr2.hasNext()) {
            System.out.print(itr2.next() + " ");
        }
        System.out.println();

        System.out.println("random: " + r.sample());
        System.out.println("random: " + r.sample());

        System.out.println("6: " + r.size());
        System.out.println(r.dequeue());
        System.out.println(r.dequeue());

        for (Integer i : r) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("4: " + r.size());
    }

}
