/* *****************************************************************************
 *  Name:    Emily Kang
 *  NetID:   etkang
 *  Precept: P05
 *
 *  Description:  Models a percolation system using an n-by-n grid of open
 * or blocked sites
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] open; // 2d array to model n-by-n grid
    private final WeightedQuickUnionUF wqf; // 1 dimensional model of 2d grid
    private final int top; // virtual top to help check if system percolates
    private final int bottom; // virtual bottom to help check if system percolates
    private final int size; // size of grid
    private int numOpen; // number of open sites

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n<=0");
        size = n;
        open = new boolean[size][size];
        wqf = new WeightedQuickUnionUF(size * size + 2);
        top = 0;
        bottom = size * size + 1;
        numOpen = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!checkSite(row, col))
            throw new IllegalArgumentException("argument outside prescribed range");
        if (!isOpen(row, col)) {
            open[row][col] = true;
            numOpen++;

            int index = indexCalculator(row, col);
            if (row == 0) wqf.union(top, index);
            if (row == size - 1) wqf.union(bottom, index);
            // left
            if (checkSite(row, col - 1) && isOpen(row, col - 1))
                wqf.union(index, indexCalculator(row, col - 1));
            // right
            if (checkSite(row, col + 1) && isOpen(row, col + 1))
                wqf.union(index, indexCalculator(row, col + 1));
            // top
            if (checkSite(row + 1, col) && isOpen(row + 1, col))
                wqf.union(index, indexCalculator(row + 1, col));
            // bottom
            if (checkSite(row - 1, col) && isOpen(row - 1, col))
                wqf.union(index, indexCalculator(row - 1, col));

        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!checkSite(row, col))
            throw new IllegalArgumentException("argument outside prescribed range");
        return (open[row][col]);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!checkSite(row, col))
            throw new IllegalArgumentException("argument outside prescribed range");
        int index = indexCalculator(row, col);
        int root1 = wqf.find(top);
        int root2 = wqf.find(index);

        if (root1 == root2) return true;
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        int root1 = wqf.find(top);
        int root2 = wqf.find(bottom);
        if (root1 == root2) return true;
        return false;
    }

    // helper method to validate indices. true is index is on grid, false otherwise.
    private boolean checkSite(int row, int col) {
        return !(row < 0 || col < 0 || row >= size || col >= size);
    }

    // helper method to map two-dimensional to one-dimensional coordinates
    private int indexCalculator(int row, int col) {
        int index = row * size + col + 1;
        return index;
    }

    // unit testing (required)
    public static void main(String[] args) {
        int size = 5;
        Percolation p = new Percolation(size);
        p.open(0, 0);
        p.open(1, 0);
        System.out.println(p.percolates());
        System.out.println(p.isOpen(1, 1));
        System.out.println(p.isOpen(1, 0));
        System.out.println(p.numberOfOpenSites());
        System.out.println(p.isFull(1, 0));
        System.out.println(p.isFull(2, 0));
        p.open(2, 0);
        p.open(3, 0);
        p.open(4, 0);
        System.out.println(p.percolates());

    }

}
