/* *****************************************************************************
 *  Name: Eugene Liu
 *  NetID:  el25
 *  Precept:  P07
 *
 *  Partner Name:     Emily Kang
 *  Partner NetID:    etkang
 *  Partner Precept:  P05
 *
 *  Hours to complete assignment (optional): 4 hours
 *
 **************************************************************************** */

Programming Assignment 6: WordNet


/* *****************************************************************************
 *  Describe concisely the data structure(s) you used to store the
 *  information in synsets.txt. Why did you make this choice?
 **************************************************************************** */

I used two red-black trees to store the information from the synset. One tree
stores the synset Id as the key and the nouns as a complete string. The other
red-black tree stores the noun as a key and a queue of synset IDs where the noun
appears. I also kept an integer counter of all the synsets being added.

Inserting and retrieving values in the red-black tree take logarithmic time. This
makes it possible for isNoun to be accomplished in log(n) time.

In addition, the storing of queues of synsets IDs in the constructor
makes operations and code in sca() and distance() much more managable
and efficient.


/* *****************************************************************************
 *  Describe concisely the data structure(s) you used to store the
 *  information in hypernyms.txt. Why did you make this choice?
 **************************************************************************** */

Since the hypernyms are basically edges between the synsets, I used a digraph to
read in and store edge pairs. This also makes it easier to create the sca object.



/* *****************************************************************************
 *  Describe concisely the algorithm you use in the constructor of
 *  ShortestCommonAncestor to check if the digraph is a rooted DAG.
 *  What is the order of growth of the worst-case running times of
 *  your algorithm? Express your answer as a function of the
 *  number of vertices V and the number of edges E in the digraph.
 *  (Do not use other parameters.) Use Big Theta notation to simplify
 *  your answer.
 **************************************************************************** */

Description:

A rooted DAG is both acyclic and contains one vertex whose out-degree is zero.
A DirectedCycle object is created using the argument given in the constructor,
a Digraph G. hasCycle() is called on the DirectedCycle object to ensure that
it is acyclic. To check that G has only one vertex to with an out-degree
of zero, a boolean "rooted" is declared and instantiated as false. Essentially,
rooted is a variable that indicates whether or not a vertex with outdegree 0 has
been seen. Then a for-loop is used to cycle through all of the vertices of G.
If the outdegree of the vertex is 0, rooted is checked. If rooted is true, then
this means that there is more than one vertex with outdegree 0. If rooted is not
true, and the current vertex has outdegree 0 then rooted is set to true. After
this for loop, rooted is checked to have been set to true to ensure that there
does exist a vertex with outdegree 0 in the diagraph. If no illegal argument
exceptions have been thrown at this point, then G is a rooted DAG.

Order of growth of running time: O(E + V)


/* *****************************************************************************
 *  Describe concisely your algorithm to compute the shortest common ancestor
 *  in ShortestCommonAncestor. For each method, give the order of growth of
 *  the best- and worst-case running times. Express your answers as functions
 *  of the number of vertices V and the number of edges E in the digraph.
 *  (Do not use other parameters.) Use Big Theta notation to simplify your
 *  answers.
 *
 *  If you use hashing, assume the uniform hashing assumption so that put()
 *  and get() take constant time per operation.
 *
 *  Be careful! If you use a BreadthFirstDirectedPaths object, don't forget
 *  to count the time needed to initialize the marked[], edgeTo[], and
 *  distTo[] arrays.
 **************************************************************************** */

Description:

A BreadthFirstDirectedPaths object is created for both int vertices (or
 Iterable<Integer> subsets). For each vertex in G, the BreadthFirstDirectedPaths
 objects are used to determine whether or not that vertex is a common ancestor.
 If the vertex is a common ancestor, the path length from each of the arguments
 to the current vertex in the graph is calculated and summed.
 An int "min" is used to keep track of the shortest path length seen so far.
 An int "ancestor" is used to keep track of the index of the vertex of the shortest
 path length.

                                 running time
method                  best case            worst case
--------------------------------------------------------
length()                O(E+V)              O(E+V)

ancestor()              O(E+V)              O(E+V)

lengthSubset()          O(E+V)              O(E+V)

ancestorSubset()        O(E+V)              O(E+V)



/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */


/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */


/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */


/* *****************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */

We followed partner protocols. We both shared a screen and talked/coded through
each part of the assignment together.


/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */

