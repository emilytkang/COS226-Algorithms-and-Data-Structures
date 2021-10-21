/* *****************************************************************************
 *  Name: Emily Kang
 *  NetID: etkang
 *  Precept: P05
 *
 *  Hours to complete assignment (optional):
 *
 **************************************************************************** */

Programming Assignment 5: Kd-Trees


/* *****************************************************************************
 *  Describe the Node data type you used to implement the
 *  2d-tree data structure.
 ******************************s********************************************** */
The Node includes the point, the value that the symbol table maps the point to,
a link to the right/top subtree, a link to the left/bottom subtree, a boolean
describing the orientation of the node (either vertical or horizontal), and
an axis-aligned rectangle corresponding to the node.

The API includes the constructor and the method compareTo(). compareTo() takes a
Point2D point as the argument and returns an int. This method compares the Node's
point with the point in the argument. If the node is a vertical node, then
compareTo() compares the x coordinates of the points. Otherwise, the y coordinates
are compared. If the points are equal (have the same x and y coords) then
0 is returned. Otherwise, if the (x/y) coordinate of the argument is greater or
equal to the (x/y) coordinate of the node, 1 is returned. Otherwise, -1 is returned.

/* *****************************************************************************
 *  Describe your method for range search in a kd-tree.
 **************************************************************************** */
The range(RectHV rect) method creates a queue to store and return the 2D points
inside the provided RectHV rectangle. The range(RectHV) method then calls a
private helper method. This helper method takes a Queue<Point2D>, a RectHV, and
a Node as arguments. range(RectHV) calls the helper method using the queue that
was just created, the provided query rectangle, and the root Node.

In order to find all points contained in the query rectangle, a recursive search
is used. The search begins at the root. Then a pruning rule is followed. If
the query rectangle does not intersect the boundary box corresponding to the node,
there is no need to explore that node or its subtrees (and the method is returned).
After this pruning, if the current node.point is contained in the query rectangle,
it is enqueued to the queue. Then the range helper method is called recursively
on the left and then the right subtrees of the current node. The two base cases
in this helper method are when 1) the current node is null and 2) when the current
node's corresponding rectangle does not intersect with the query rectangle. In
essence, a subtree is only searched if it might contain a point contained in the
query rectangle. After all the points in the query rectangle have been enqueued,
the queue is returned in the range(RectHV) method.

/* *****************************************************************************
 *  Describe your method for nearest neighbor search in a kd-tree.
 **************************************************************************** */
The nearest(Point2D p) method calls a private helper method. This private
helper method takes a Node and two Point2D points as an argument. One of these points
is the target query point. The other point is the champion point – the point
that at that time in the search is closest to the target query point. nearest(Point2D)
calls this private helper method using the root Node, the query target point,
and the point associated with the root Node.

This private helper method utilizes two main optimizations: pruning and
recursive call-ordering. The recursive search begins at the root. A Point2D object
named champion is declared and initialized to the point associated with the
root Node. From there, there are two base cases that lead the private helper
method to be returned: 1) the current Node is null and 2) the champion
point is closer to the target point than the node.point's boundary box is to
the target point (meaning there is no need to explore that node or its subtrees).
After this pruning occurs, it is checked whether or not the current node.point
is closer than the champion is to the target point. If so, the current node.point
becomes the champion. Then recursive-call ordering is utilized. The current node
is compared to the target point. If the target point is found to be left or below
the node, then the left subtree is traversed and then the right subtree is traversed.
If the target point is right or above the current node, then the right
subtree is traversed before the left subtree. By choosing the subtree that is on
the same side of the splitting line as the query point, the closest point
found while exploring the first subtree may lead to pruning of the second
subtree. The champion is set to the nearest point discovered during the
recursive nearest call on the left and right subtrees. The champion is then returned
at the end of the private helper method and also subsequently returned by the
nearest(Point2D) method.

/* *****************************************************************************
 *  How many nearest-neighbor calculations can your PointST implementation
 *  perform per second for input1M.txt (1 million points), where the query
 *  points are random points in the unit square?
 *
 *  Fill in the table below, using one digit after the decimal point
 *  for each entry. Use at least 1 second of CPU time.
 *  (Do not count the time to read the points or to build the 2d-tree.)
 *
 *  Repeat the same question but with your KdTreeST implementation.
 *
 **************************************************************************** */


                 # calls to         /   CPU time     =   # calls to nearest()
                 client nearest()       (seconds)        per second
                ------------------------------------------------------
PointST:                  20        / 1.7           11.8

KdTreeST:              300000      /  1.1           272727.3

Note: more calls per second indicates better performance.


/* *****************************************************************************
 *  Did you fill out the mid-semester feedback form?
 *  If not, please do so now: https://bit.ly/349Ql18
 **************************************************************************** */
Yes

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
 *  on  how helpful the class meeting was and on how much you learned
 * from doing the assignment, and whether you enjoyed doing it.
 **************************************************************************** */
