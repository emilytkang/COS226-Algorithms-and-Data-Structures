/* *****************************************************************************
 *  Name:    Eugene Liu
 *  NetID:   el25
 *  Precept: P07
 *
 *  Partner Name:    Emily Kang
 *  Partner NetID:   etkang
 *  Partner Precept: P05
 *
 *  Description:  API for move-to-front encoding and decoding
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.LinkedList;

public class MoveToFront {
    // stores extended ASCII length as a global variable
    public static final int extendedASCII = 256;

    // apply move-to-front encoding, reading from stdin and writing to stdout
    public static void encode() {

        // stores alphabet
        LinkedList<Character> array = new LinkedList<Character>();
        for (char c = 0; c < extendedASCII; c++) {
            array.add(c, c);
        }

        // initializes input stream
        while (!BinaryStdIn.isEmpty()) {
            char read = BinaryStdIn.readChar(8);

            // determines where character is in alphabet
            int index = array.indexOf(read);
            BinaryStdOut.write(index, 8); // writes to std out

            // for loop moves elements back
            for (int i = index; i > 0; i--) {
                char character = array.get(i - 1);
                array.set(i, character); // moves all elements up
            }

            // sets character to the front
            array.set(0, read);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from stdin and writing to stdout
    public static void decode() {
        // stores alphabet
        LinkedList<Character> array = new LinkedList<Character>();
        for (char c = 0; c < extendedASCII; c++) {
            array.add(c, c);
        }

        // initializes input stream
        while (!BinaryStdIn.isEmpty()) {
            int read = BinaryStdIn.readInt(8);

            // determines where character is in alphabet
            char character = array.get(read);
            BinaryStdOut.write(character, 8); // writes to std out

            // for loop moves elements back
            for (int i = read; i > 0; i--) {
                char character1 = array.get(i - 1);
                array.set(i, character1); // moves all elements up
            }

            // sets character to the front
            array.set(0, character);
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {


        if (args[0].equals("-")) {
            encode();
        }

        if (args[0].equals("+")) {
            decode();
        }

    }
}
