/* *****************************************************************************
 *  Name:    Eugene Liu
 *  NetID:   el25
 *  Precept: P07
 *
 *  Partner Name:    Emily Kang
 *  Partner NetID:   etkang
 *  Partner Precept: P05
 *
 *  Description:  WordNet object allows for easier analysis on semantic
 *  relationships with nouns. This is accomplished by leveraging two
 *  RedBlackTrees for retrieval of nouns and synsets that have a given noun.
 *  A ShortestCommonAncestor object is used to calculate semantic relationships
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {

    private int synsetCounter; // counter to keep track of how many nodes
    private ShortestCommonAncestor sca; // sca object from digraph

    // search tree with strings are the keys and values are queues of
    // corresponding vertices
    private final RedBlackBST<String, Queue<Integer>> nounsVertices;

    // synset ID is the key of the BST, values are the strings in the synset
    private final RedBlackBST<Integer, String> nounsInVertices;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        if ((synsets == null || hypernyms == null)) {
            throw new IllegalArgumentException("one of the files doesn't exit");
        }

        // initialize instance variables
        synsetCounter = 0;
        nounsVertices = new RedBlackBST<String, Queue<Integer>>();
        nounsInVertices = new RedBlackBST<Integer, String>();

        synsetParseRead(synsets);
        hypernymsParseRead(hypernyms);

    }

    // helper method to read and parse synsets
    private void synsetParseRead(String synsets) {
        // initializes synset input stream
        In synsetInput = new In(synsets);

        // reads in new line, breaks up into fields, adds to RedBlackST
        while (synsetInput.hasNextLine()) {
            String fullString = synsetInput.readLine();
            String[] arrayOfFields = fullString.split("\\,");
            int key = Integer.parseInt(arrayOfFields[0]);
            String[] arrayOfNouns = arrayOfFields[1].split("\\ ");

            nounsInVertices.put(key, arrayOfFields[1]); // adds full string BST

            // operations on every noun
            for (int i = 0; i < arrayOfNouns.length; i++) {

                // following code associates links all vertices that have a noun
                // to that noun
                Queue<Integer> verticesWithNoun =
                        nounsVertices.get(arrayOfNouns[i]);

                // if no vertices have been associated yet, create a new queue
                // and enqueue the vertex
                if (verticesWithNoun == null) {
                    verticesWithNoun = new Queue<Integer>();
                    verticesWithNoun.enqueue(key);
                    nounsVertices.put(arrayOfNouns[i], verticesWithNoun);
                }

                // if noun is already in the search tree, retrieve the queue,
                // enqueue new key, insert back in
                else {
                    verticesWithNoun.enqueue(key);
                    nounsVertices.put(arrayOfNouns[i], verticesWithNoun);
                }
            }

            synsetCounter++;
        }
    }

    // helper method to read and parse hypernyms
    private void hypernymsParseRead(String hypernyms) {
        // creates input stream
        In hypernymInput = new In(hypernyms);

        // initializes Digraph to length of synsets
        Digraph wordnet = new Digraph(synsetCounter);

        // reads in new line, breaks up into fields, adds edges to digraph
        while (hypernymInput.hasNextLine()) {
            String fullString = hypernymInput.readLine();
            String[] idStrings = fullString.split("\\,");

            int v = Integer.parseInt(idStrings[0]);

            for (int i = 1; i < idStrings.length; i++) {
                wordnet.addEdge(v, Integer.parseInt(idStrings[i]));
            }
        }
        sca = new ShortestCommonAncestor(wordnet);
    }

    // the set of all WordNet nouns
    public Iterable<String> nouns() {
        return nounsVertices.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nounsVertices.contains(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {
        if (!(isNoun(noun1) && isNoun(noun2))) {
            throw new IllegalArgumentException("One of the nouns is not in "
                                                       + "wordnet");
        }

        Queue<Integer> noun1set = nounsVertices.get(noun1);
        Queue<Integer> noun2set = nounsVertices.get(noun2);

        int key = sca.ancestorSubset(noun1set, noun2set);


        return (nounsInVertices.get(key));


    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        if (!(isNoun(noun1) && isNoun(noun2))) {
            throw new IllegalArgumentException("One of the nouns is not in "
                                                       + "wordnet");
        }

        Queue<Integer> noun1set = nounsVertices.get(noun1);
        Queue<Integer> noun2set = nounsVertices.get(noun2);

        return (sca.lengthSubset(noun1set, noun2set));

    }

    // unit testing (required)
    public static void main(String[] args) {
        WordNet test = new WordNet("synsets.txt", "hypernyms.txt");

        // tests if parsing is correct
        int synsets = 0;
        for (String noun : test.nouns()) {
            StdOut.println(noun);
            synsets++;
        }

        // should be 120,119
        StdOut.println(synsets);

        // testing if nouns are in the WordNet
        StdOut.println(test.isNoun("white_marlin")); // should be true
        StdOut.println(test.isNoun("mileage")); // should be true

        // testing distance between two strings
        StdOut.println(test.distance("white_marlin", "mileage"));

        // testing if SCA works. Should return: "physical_entity"
        StdOut.println(test.sca("individual", "edible_fruit"));

        // testing corner cases. Should throw exceptions
        StdOut.println(test.distance("I love programming", "I love COS"));
        StdOut.println(test.sca("I love programming", "I love COS"));
        WordNet test2 = new WordNet(null, null);
        test2.nouns();


    }
}
