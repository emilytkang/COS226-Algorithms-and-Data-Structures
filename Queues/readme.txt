/* *****************************************************************************
 *  Name: Emily Kang
 *  NetID: etkang
 *  Precept: P05
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 *
 *  Hours to complete assignment (optional):
 *
 **************************************************************************** */

Programming Assignment 2: Deques and Randomized Queues


/* *****************************************************************************
 *  Explain briefly how you implemented the randomized queue and deque.
 *  Which data structure did you choose (array, linked list, etc.)
 *  and why?
 **************************************************************************** */
Randomized Queue:
For randomized queue, I chose to use a resizeable array. Given the fact that
a random item had to be uniformly chosen among the items in the data structure,
I felt that using indexing with an array would be more straightforward and
efficient in terms of memory and runtime. I used 3 instance variables (excluding
the instance variables in the Iterator class). One final static variable to set
the initial capacity of the array. One int variable to keep track of the number
of non-null elements in the RandomizedQueue. One resizable Item[] to hold elements.

Deque:
For deque, I chose to use a linked list. I chose a linked list because items
could only be added/removed from the front/end of the deque. I felt that this
lent itself well to a doubly linked list instead of an array because there
was no real need for indexing.

/* *****************************************************************************
 *  How much memory (in bytes) do your data types use to store n items
 *  in the worst case? Use the 64-bit memory cost model from Section
 *  1.4 of the textbook and use tilde notation to simplify your answer.
 *  Briefly justify your answers and show your work.
 *
 *  Do not include the memory for the items themselves (as this
 *  memory is allocated by the client and depends on the item type)
 *  or for any iterators, but do include the memory for the references
 *  to the items (in the underlying array or linked list).
 **************************************************************************** */

Randomized Queue:   ~  8n  bytes
- 4 bytes for n (int)
- 8 bytes for reference to []a
- 8n bytes for reference to items in []a
- 24 bytes (overhead)
- 4 bytes (padding)

Total: 8n + 40 ~ 8n


Deque:              ~  48n  bytes
- 16 bytes (object overhead)
- 8 bytes for reference to first (Node)
- 8 bytes for reference to last (Node)
- 4 bytes for n (int)
- 4 bytes (padding)
Total: 40 bytes

Node
- 16 bytes (object overhead)
- 8 bytes for reference to next (Node)
- 8 bytes for reference to previous (Node)
- 8 bites for reference to item (Item)
- 8 bytes (inner class overhead)
Total: 48n

Total: 48n + 40 ~ 48n

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
 *  you enjoyed doing it.
 **************************************************************************** */
