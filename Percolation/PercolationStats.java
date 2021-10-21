/* *****************************************************************************
 *  Name:    Emily Kang
 *  NetID:   etkang
 *  Precept: P05
 *
 *  Description: Monte Carlo Stimulation to estimate percolation threshold
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96; // used to calculate stddev
    private final double[] results; // double array of estimated thresholds
    private final int numTrials; // number of trials

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("argument not valid");
        results = new double[trials];
        numTrials = trials;
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row, col;
                do {
                    row = StdRandom.uniform(0, n);
                    col = StdRandom.uniform(0, n);
                } while (p.isOpen(row, col));
                p.open(row, col);

            }
            results[i] = (double) p.numberOfOpenSites() / (n * n);

        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);

    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);

    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(numTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(numTrials));

    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        Stopwatch s = new Stopwatch();
        PercolationStats ps = new PercolationStats(n, trials);
        double mean = ps.mean();
        double stddev = ps.stddev();
        double confidenceLow = ps.confidenceLow();
        double confidenceHigh = ps.confidenceHigh();
        double time = s.elapsedTime();

        System.out.printf("mean() = %.6f\n", mean);
        System.out.printf("stddev() = %.6f\n", stddev);
        System.out.printf("confidenceLow = %.6f\n", confidenceLow);
        System.out.printf("confidenceHigh = %.6f\n", confidenceHigh);
        System.out.printf("elapsed time = %.6f\n", time);

    }

}
