/* *****************************************************************************
 *  Name:    Eugene Liu
 *  NetID:   El25
 *  Precept: P07
 *
 *  Partner Name:    Emily Kang
 *  Partner NetID:   etkang
 *  Partner Precept: P05
 *
 *  Description:  CircularSuffixArray
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class CircularSuffixArray {

    private CircularSuffix[] circularArray; // array for storing all circular suffixes
    private String string; // stores a copy of the string


    // private helper class for easier suffix manipulation
    private class CircularSuffix implements Comparable<CircularSuffix> {

        private final String text; // stores string
        private final int offset; // index for where suffix begins

        // constructor [code adapted from 5.1 lecture slides]
        public CircularSuffix(String text, int offset) {

            // checks if text is null
            if (text == null) {
                throw new IllegalArgumentException("text is null");
            }

            this.text = text;
            this.offset = offset;
        }

        // returns length of suffix component
        // [code adapted from 5.1 lecture slides]
        public int length() {
            return text.length() - offset;
        }

        // return the character at any index of the circular suffix
        public char charAt(int i) {

            // code loops back and return chars not contained in the suffix
            if (i > length() - 1) {
                int difference = i - (length() - 1);
                return text.charAt(difference - 1);
            }

            // [code adapted from 5.1 lecture slides]
            return text.charAt(offset + i);
        }

        // comparable method for sorting
        public int compareTo(CircularSuffix that) {
            for (int i = 0; i < string.length(); i++) {
                int returnVal = Character.compare(charAt(i), that.charAt(i));

                if (returnVal != 0) {
                    return returnVal;
                }
            }
            return 0;
        }
    }

    // constructor for CircularSuffixArray
    public CircularSuffixArray(String s) {

        if (s == null) {
            throw new IllegalArgumentException(" s is null");
        }
        circularArray = new CircularSuffix[s.length()];
        string = s;

        for (int offset = 0; offset < length(); offset++) {
            circularArray[offset] = new CircularSuffix(s, offset);
        }

        // uses comparable to sort array
        Arrays.sort(circularArray);
    }

    // length of s
    public int length() {
        return string.length();

    }

    // returns index of ith sorted suffix
    public int index(int i) {

        // check if
        if (i < 0 || i >= length()) {
            throw new IllegalArgumentException("i is less than 0");
        }

        CircularSuffix suffix = circularArray[i];

        // index is equal to the number of chars that circle back
        return string.length() - suffix.length();
    }


    public static void main(String[] args) {
        String string = "ABRACADABRA!";
        CircularSuffixArray array = new CircularSuffixArray(string);

        // should be 12
        StdOut.println(array.length());

        // should be 2
        StdOut.println(array.index(11));

        // should be 9
        StdOut.println(array.index(10));

    }
}
