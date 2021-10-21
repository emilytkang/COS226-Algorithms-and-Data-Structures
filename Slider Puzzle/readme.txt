/* *****************************************************************************
 *  Name: Emily Kang
 *  NetID: etkang
 *  Precept: P05
 *
 *  Partner Name:
 *  Partner NetID:
 *  Partner Precept:
 *
 *  Hours to complete assignment (optional):
 *
 **************************************************************************** */

Programming Assignment 4: Slider Puzzle


/* *****************************************************************************
 *  Explain briefly how you represented the Board data type.
 **************************************************************************** */
I represented the Board data type as a 1d array representation of the initial
2d array tile[][]. I also used final instance variables to keep track of the
hamming distance, the manhattan distance, and the dimension, n, of the n x n 2d
array tile[][]. Additionally, I had an instance variable to keep track of the
x and y coordinates of the blank tile. There is both a public and private
Board constructor, one which takes a 2d array and one which takes a 1d array.

/* *****************************************************************************
 *  Explain briefly how you represented a search node
 *  (board + number of moves + previous search node).
 **************************************************************************** */
A search node contains a board, number of moves to get to that board, a reference
to the previous (parent) board, and the priority number. The first three are
arguments while the priority is calculated from the sum of the number of moves
and the manhattan distance.

/* *****************************************************************************
 *  Explain briefly how you detected unsolvable puzzles.
 *
 *  What is the order of growth of the running time of your isSolvable()
 *  method in the worst case as function of the board size n? Use big Theta
 *  notation to simplify your answer, e.g., Theta(n log n) or Theta(n^3).
 **************************************************************************** */

Description:
In isSolvable(), I first created a sorted array from 1 to n^2. I chose to not
include zero because zero is not counted during inversions in this problem.

I then used two nested for-loops from 1 to n to loop through the 1d array
representing the board, board[]. For each element in board, I located the index
value of the element in the sorted array. The value of the index is equivalent
to the number of inversions that involve that element. I then remove the element
from the sorted array and repeat for the next element, using a variable to
sum the total number of inversions.

Let us call our current element e and the next element in the unsorted array a.
If a<e, then the pair (a,e) should not be counted as an inversion. Thus in the
sorted array, we decrease the index of e by removing a. If a>e, then removing a
from the sorted array will not change the index of e, and the pair (a,e) will be
correctly counted as an inversion. a != e because the input consists of n^2
distinct keys.

After calculating the number of inversions, I used the conditions in the
assignment description to determine whether or not the boards were solvable.

The methods for finding the index of the element/removing the element in
the sorted array each use a for loop going from 1 to n^2. These methods are called
within the double nested for loop; however, the methods themselves are not
nested. In the worst case, this gives us an order of growth of running time of
Theta(n^4).


Order of growth of running time: Theta(n^4)

/* *****************************************************************************
 *  For each of the following instances, give the minimum number of moves to
 *  solve the instance (as reported by your program). Also, give the amount
 *  of time your program takes with both the Hamming and Manhattan priority
 *  functions. If your program can't solve the instance in a reasonable
 *  amount of time (say, 5 minutes) or memory, indicate that instead. Note
 *  that your program may be able to solve puzzle[xx].txt even if it can't
 *  solve puzzle[yy].txt and xx > yy.
 **************************************************************************** */


                 min number          seconds
     instance     of moves     Hamming     Manhattan
   ------------  ----------   ----------   ----------
   puzzle28.txt     28           0.39           0.02
   puzzle30.txt     30           0.75           0.03
   puzzle32.txt     32      out of memory       0.31
   puzzle34.txt     34      out of memory       0.11
   puzzle36.txt     36      out of memory       1.07
   puzzle38.txt     38      out of memory       0.33
   puzzle40.txt     40      out of memory       0.34
   puzzle42.txt     42      out of memory       1.70


/* *****************************************************************************
 *  If you wanted to solve random 4-by-4 or 5-by-5 puzzles, which
 *  would you prefer: a faster computer (say, 2x as fast), more memory
 *  (say 2x as much), a better priority queue (say, 2x as fast),
 *  or a better priority function (say, one on the order of improvement
 *  from Hamming to Manhattan)? Why?
 **************************************************************************** */
I would prefer a better priority function because looking at the data in the chart
above the priority function has a huge impact on both speed and memory. If
you always knew which move was the right move to choose, then this would
drastically  improve both speed and memory. However, if you buy a faster computer,
this may improve speed by will not improve memory, and the same vice versa for
having more memory.

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







/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
