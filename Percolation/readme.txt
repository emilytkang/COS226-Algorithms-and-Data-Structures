/* *****************************************************************************
 *  Name: Emily Kang
 *  NetID: etkang
 *  Precept: P05
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 *
 *  Operating system: macOS Big Sur
 *  Compiler: javac 11.0.7
 *  Text editor / IDE: IntelliJ
 *
 *  Have you taken (part of) this course before: no
 *  Have you taken (part of) the Coursera course Algorithms, Part I or II: no
 *
 *  Hours to complete assignment (optional):
 *
 **************************************************************************** */

Programming Assignment 1: Percolation


/* *****************************************************************************
 *  Describe the data structures (i.e., instance variables) you used to
 *  implement the Percolation API.
 **************************************************************************** */

    private boolean[][] open --> a 2d array to model the n-by-n grid. if [i][j]
    in the grid is true, then the site at (i,j) is open. if false, then the site
    is closed.

    private final WeightedQuickUnionUF wqf --> a union find data type to represent
    a 1-dimensional model of the 2d grid. includes a virtual top and bottom to
    assist in checking for percolation (n*n+2 elements)

    private final int top --> a virtual top to help check if system percolates
    private final int bottom --> virtual bottom to help check if system percolates
    i.e, if find(top) == find(bottom) then the system percolates. the top and bottom
    represent the first and last element in the WeightedQuickUnionUF, respectively.

    private final int size --> size of grid, argument in constructor (n)

    private int numOpen --> number of open sites in the n-by-n grid

/* *****************************************************************************
 *  Briefly describe the algorithms you used to implement each method in
 *  the Percolation API.
 **************************************************************************** */

    Percolation(int n): initializes all instance variables including a
    WeightedQuickUnionUF (a union-find data type that utilizes the
    weighted-quick union algorithm). The WeightedQuickUnionUF is given n*n+2
    elements, where n is the size of the grid, to account for a virtual top
    and bottom site.

    open(int row, int col): verifies that the row and column values are within
    the prescribed grid. if not blocked, the given site is opened. the row and col
    value within the boolean[][] is opened and the number of open sites increases
    as well. the site is then unioned with the cells to the left, right, top, and
    bottom of it in the WeightedQuickUnionUF.

    isOpen(int row, int col): verifies that the row and column values are within
    the prescribed grid. then accesses the boolean[][] to determine whether or
    not the site is open. if open[row][col] is true, the site is open. if false,
    the site is closed.

    isFull(int row, int col): verifies that the row and column values are within
    the prescribed grid. utilizes indexCalculator() to determine the index of the
    given site in the WeightedQuickUnionUF. compares find(index) and find(top) to
    determine whether or not the given site is full. if find(index) == find(top)
    then the two sites have the same leader and are connected. this will make the
    site full. if find(index) != find(top), then the site is not full.

    numberOfOpenSites(): returns the instance variable that tracks the number
    of open sites in the grid.

    percolates(): compares find(top) and find(bottom). if the values are the same,
    i.e., top and bottom have the same leader, then the system will percolate.
    else, the system will not percolate.

    checkSite(int row, int col): a private helper method to validate the given
    row and col arguments. returns false if the site is not in the grid, returns
    true if the site is in the grid.

    indexCalculator(int row, int col): a private helper method to map the
    two-dimensional row and col values in boolean[][] to the index value of the
    same site in the WeightedQuickUnionUF.

/* *****************************************************************************
 *  Perform computational experiments to estimate the running time of
 *  PercolationStats.java for various values of n and T when implementing
 *  Percolation.java with QuickFindUF.java (not QuickUnionUF.java). Use a
 *  "doubling" hypothesis, where you successively increase either n or T by
 *  a constant multiplicative factor (not necessarily 2).
 *
 *  To do so, fill in the two tables below. Each table must have 5-10
 *  data points, ranging in time from around 0.25 seconds for the smallest
 *  data point to around 30 seconds for the largest one. Do not include
 *  data points that take less than 0.25 seconds.
 **************************************************************************** */

(keep T constant)
 T = 100
 multiplicative factor (for n) = 1.4

 n          time (seconds)       ratio         log ratio
--------------------------------------------------------
23          0.257000            n/a             n/a
32          0.806000            3.136           3.397
45          2.831000            3.512           3.512
63          10.07500            3.559           3.772
88          35.70500            3.544           3.767



(keep n constant)
 n = 100
 multiplicative factor (for T) = 2.6

 T          time (seconds)       ratio         log ratio
--------------------------------------------------------
1           0.59000             n/a             n/a
3           1.704000            2.888           1.109
8           4.507000            2.645           1.017
21          11.93700            2.649           1.020
54          32.22000            2.699           1.039

/* *****************************************************************************
 *  Using the empirical data from the above two tables, give a formula
 *  (using tilde notation) for the running time (in seconds) of
 *  PercolationStats.java as function of both n and T, such as
 *
 *       ~ 5.3*10^-8 * n^5.0 * T^1.5
 *
 *  Briefly explain how you determined the formula for the running time.
 *  Recall that with tilde notation, you include both the coefficient
 *  and exponents of the leading term (but not lower-order terms).
 *  Use two significant figures for each coefficient and exponent
 *  (e.g., 5.3*10^-8 or 5.0).
 **************************************************************************** */

QuickFindUF running time (in seconds) as a function of n and T:

    ~ 1.5*10^-8 * n^3.8 * T^1.0
       _______________________________________

I determined this formula for running time by looking at the first chart
and seeing that the log ratio converges to 3.8. Therefore the exponent for n
will be 3.8. I looked at the second chart and saw that the log ratio converges
to 1.0. Therefore, the exponent for T will be 1.0. I then used T(88,100) to
estimate the leading coefficient as 1.46*1^-8 and T(100, 54) as 1.49*10^-8.
I averaged these two numbers to get a leading coefficient of 1.47*10^-8
(rounded to 1.5*10^-8).

/* *****************************************************************************
 *  Repeat the previous two questions, but using WeightedQuickUnionUF
 *  (instead of QuickFindUF).
 **************************************************************************** */

(keep T constant)
 T = 100
 multiplicative factor (for n): 1.75

 n          time (seconds)       ratio         log ratio
--------------------------------------------------------
35          0.268000            n/a             n/a
61          0.805000            3.004           1.966
107         2.450000            3.043           1.988
200         7.446000            3.039           1.986
328         22.73100            3.052           1.99



(keep n constant)
 n = 100
 multiplicative factor (for T): 33

 T          time (seconds)       ratio         log ratio
--------------------------------------------------------
20          0.399000            n/a             n/a
60          1.249000            3.130           1.039
180         3.724000            2.982           0.995
540         10.94000            2.938           0.981
1620        32.316              2.954           0.986


WeightedQuickUnionUF running time (in seconds) as a function of n and T:

    ~ 2.1 * 10^-6 * n^2.0 * T^0.9
    _______________________________________

I determined this formula for running time by looking at the first chart
and seeing that the log ratio converges to 2.0. Therefore the exponent for n
will be 2.0. I looked at the second chart and saw that the log ratio converges
to 1.0. Therefore, the exponent for T will be 1.0. I then used T(100,1620) to
estimate the leading coefficient as 1.99*1^-6 and T(328, 100) as 2.11*10^-6.
I averaged these two numbers to get a leading coefficient of 2.05*10^-6
(rounded to 2.1*10^-6).

/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */

- does not account for backwash

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
