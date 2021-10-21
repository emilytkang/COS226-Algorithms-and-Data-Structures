/* *****************************************************************************
 *  Name: Emily Kang
 *  NetID: etkang
 *  Precept: P05
 *
 *  Name:    Eugene Liu
 *  NetID:   el25
 *  Precept: P07
 *
 *  Hours to complete assignment (optional):
 *
 **************************************************************************** */

Programming Assignment 8: Burrows-Wheeler

/* *****************************************************************************
 *  Which sorting algorithm did you use in CircularSuffixArray.java?
 *  Why did you choose it?
 **************************************************************************** */

After sorting the the circular suffixes and implementing comparableTo for
circular suffixes. I used Arrays.sort. Arrays.sort uses a dual-pivot quicksort.
This algorithm was chosen because defining a helper method, circularSuffix, and
writing a compareTo method was clean and efficient and made visualizing and
thinking about CircularSuffixArrays much easier. While not the most efficient
sorting solution, this code was concise and easy to understand while still
meeting performance requirements.

/* *****************************************************************************
 *  How long does your implementation of the Burrows-Wheeler data compression
 *  algorithm (BurrowsWheeler + MoveToFront + Huffman) take to compress and
 *  expand mobydick.txt? What is the compression ratio (number of bytes in
 *  compressed message divided by the number of bytes in the message)?
 *  Compare the results to that for running Huffman alone and GNU
 *  command-line utility Gzip.
 **************************************************************************** */

Algorithm       Compression time    Expansion time           Compression ratio
------------------------------------------------------------------------------
My program      1.608s              0.285s              0.347

Huffman alone   0.216s              0.167s              0.560 (667651 / 1191463)

gzip            0.084s              0.007s              0.407

See the Checklist for Bash commands to compress/expand.


/* *****************************************************************************
 *  What is the running time of each of the following methods as a function
 *  of the input size n and the alphabet size R, both in practice
 *  (on typical English text inputs) and in theory (in the worst case)?
 *  Use big Theta notation to simplify your answer (e.g., n, n + R, n log n,
 *  n log^2n n, n^2, or R n).
 *
 *  Include the time for sorting circular suffixes in Burrows-Wheeler
 *  transform().
 **************************************************************************** */

                                      typical            worst
---------------------------------------------------------------------
CircularSuffixArray constructor       nlogn              nlogR
BurrowsWheeler transform()            n                  n
BurrowsWheeler inverseTransform()     n + R              n + R
MoveToFront encode()                  n + R              nR
MoveToFront decode()                  n + R              nR
Huffman compress()                    n + R log R        n + R log R
Huffman expand()                      n                  n



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
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it. Additionally, you may include any suggestions
 *  for what to change or what to keep (assignments or otherwise) in future
 *  semesters.
 **************************************************************************** */
