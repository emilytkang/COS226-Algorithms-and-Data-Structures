/* *****************************************************************************
 *  Name:    Emily Kang
 *  NetID:   etkang
 *  Precept: P05
 *
 *  Partner Name:    Eugene Liu
 *  Partner NetID:   el25
 *  Partner Precept: P07
 *
 *  Description: immutable data type outcast that takes a WordNet object as an
 * argument and identifies the noun least related to the others given an array
 * of WordNet nouns
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private final WordNet w; // final WordNet object

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        w = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        String outcast = "";
        int maxDist = (int) Double.NEGATIVE_INFINITY;
        for (int i = 0; i < nouns.length; i++) {
            int dist = 0; // sum of distances between nouns[i] and every other noun
            for (String s : nouns) {
                dist += w.distance(nouns[i], s);
            }
            // update maxDist if necessary
            if (dist > maxDist) {
                maxDist = dist;
                outcast = nouns[i];
            }
        }
        return outcast;
    }

    // test client (see below)
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

}

