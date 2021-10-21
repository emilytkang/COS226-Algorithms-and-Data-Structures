/* *****************************************************************************
 *  Name:     Eugene Liu
 *  NetID:    el25
 *  Precept:  p07
 *
 *  Partner Name:    Emily Kang
 *  Partner NetID:   etkang
 *  Partner Precept: p05
 *
 *  Hours to complete assignment (optional):
 *
 **************************************************************************** */

Programming Assignment 7: Seam Carving


/* *****************************************************************************
 *  Describe concisely your algorithm to find a horizontal (or vertical)
 *  seam.
 **************************************************************************** */

The algorithms is based off of the Dijkstra's shortest path algorithm. In short,
we are calculating the shortest energy path for every pixel from the top
row column to the opposite end. Each pixel is in theory "connected" to a maximum
of three other pixels (depending on where the pixel is located). To see which of
these three candidate pixels would be in the seam path, we chose the pixel that
have the smallest impact on the total path. After the inclusion of each new
candidate pixel, we compared the new distance to the existing distance at that
pixel. If the new distance is shorter, we updated the shortest distance.


/* *****************************************************************************
 *  Describe what makes an image suitable to the seam-carving approach
 *  (in terms of preserving the content and structure of the original
 *  image, without introducing visual artifacts). Describe an image that
 *  would not work well.
 **************************************************************************** */

An image with a clear subject and background would be suitable for seam-carving.
In this scenario, there are regions that are clearly less important, so removing
a seam would make more sense.

A picture with multiple subjects in close proximity (e.g. a class photo) would
be less ideal as the paths would theoretically be of equal importance. Therefore,
while it is possible to isolate and remove a the smallest-energy seam, it would
severely impact the picture.


/* *****************************************************************************
 *  Perform computational experiments to estimate the running time to reduce
 *  a W-by-H image by one column and one row (i.e., one call each to
 *  findVerticalSeam(), removeVerticalSeam(), findHorizontalSeam(), and
 *  removeHorizontalSeam()). Use a "doubling" hypothesis, where you
 *  successively increase either W or H by a constant multiplicative
 *  factor (not necessarily 2).
 *
 *  To do so, fill in the two tables below. Each table must have 5-10
 *  data points, ranging in time from around 0.25 seconds for the smallest
 *  data point to around 30 seconds for the largest one.
 **************************************************************************** */

(keep W constant)
 W = 2000
 multiplicative factor (for H) = 2

 H           time (seconds)      ratio       log ratio
------------------------------------------------------
4              0.22
8              0.448               2.036       1.0257
16             0.96                2.1428      1.09949
32             2.261               2.355       1.2357
64             4.067               1.799       0.8472
128            8.441               2.075       1.0531
256            15.725              1.863       0.897
512            31.412              1.998       0.998




(keep H constant)
 H = 2000
 multiplicative factor (for W) = 2

 W           time (seconds)      ratio       log ratio
------------------------------------------------------
4               0.249
8               0.504            2.024          1.017
16              1.006            1.996          0.997
32              2.015            2.0029         1.00209
64              4.047            2.00843        1.00606
128             8.014            2.0084         1.00604
256             15.624           1.949          0.9627
512             31.352           2.006          1.004



/* *****************************************************************************
 *  Using the empirical data from the above two tables, give a formula
 *  (using tilde notation) for the running time (in seconds) as a function
 *  of both W and H, such as
 *
 *       ~ 5.3*10^-8 * W^5.1 * H^1.5
 *
 *  Briefly explain how you determined the formula for the running time.
 *  Recall that with tilde notation, you include both the coefficient
 *  and exponents of the leading term (but not lower-order terms).
 *  Round each coefficient and exponent to two significant digits.
 **************************************************************************** */


Running time (in seconds) to find and remove one horizontal seam and one
vertical seam, as a function of both W and H:


    ~
       3.2 * 10^-5 * W * H

       From both trials it is clear that varying width and height converge on a
       b value of ~1. I did an additional trial of a picture that was 512x512
       pixels. The time for this trial was 8.343 secs. Dividing this val by
       512^2 gave me the a component of 3.2 * 10^-5.




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

We followed partner protocol as specified in the course syllabus. We shared a
screen either via zoom or in-person and discussed the code/readme together.

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
