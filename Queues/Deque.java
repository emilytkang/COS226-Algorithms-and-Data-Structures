/* *****************************************************************************
 *  Name:    Emily Kang
 *  NetID:   etkang
 *  Precept: P05
 *
 *  Description: Implementation fo Deque data type --> generalization of stack
 *  and queue
 *
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    /*
        @citation adapted from: https://algs4.cs.princeton.edu/13stacks/
        LinkedStack.java.html. Accessed 03/01/2021.
     */
    private Node first, last; // top and bottom of stack, respectively
    private int n; // size of deque

    // construct an empty deque
    public Deque() {
        n = 0;
        first = null;
        last = null;

    }

    // helper linked list class
    private class Node {
        private Item item; // linked list node value
        private Node next; // next node in linked list
        private Node previous; // previous node in linked list
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (n == 0);
    }

    // return the number of items on the deque
    public int size() {
        return n;

    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (isEmpty()) {
            last = first;
        }
        else {
            oldfirst.previous = first;
        }
        n++;

    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.previous = oldlast;
        if (isEmpty()) {
            first = last;
        }
        else {
            oldlast.next = last;
        }
        n++;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) {
            last = null;
        }
        else {
            first.previous = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        last = last.previous;
        n--;
        if (isEmpty()) {
            first = null;
        }
        else {
            last.next = null;
        }
        return item;

    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first; // node to keep track of current item

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // returns a string representation of the stack as the sequence of items in
    // LIFO order, separated by spaces
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deq = new Deque<Integer>();
        System.out.println("0: " + deq.size());
        System.out.println("true: " + deq.isEmpty());

        deq.addFirst(2);
        deq.addFirst(1);
        System.out.println("1 2: " + deq);
        deq.addLast(3);

        System.out.println("3: " + deq.size());
        System.out.println("false: " + deq.isEmpty());
        System.out.println("1 2 3: " + deq);

        deq.removeLast();
        System.out.println("2: " + deq.size());
        System.out.println("2: " + deq.removeLast());
        System.out.println("false: " + deq.isEmpty());
        System.out.println("1: " + deq.removeFirst());
        System.out.println(deq);

        deq.addLast(4);
        System.out.println("4: " + deq);
        deq.removeLast();
        System.out.println("true: " + deq.isEmpty());
        System.out.println(deq);

        deq.addFirst(100);
        deq.addFirst(10);

        System.out.println(deq);
        Iterator<Integer> itr = deq.iterator();
        System.out.println(itr.next());
        System.out.println(itr.next());
        // System.out.println(itr.next());
    }

}
