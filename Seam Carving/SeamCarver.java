/* *****************************************************************************
 *  Name:    Emily Kang
 *  NetID:   etkang
 *  Precept: P05
 *
 *  Partner Name:    Eugene Liu
 *  Partner NetID:   el25
 *  Partner Precept: P06
 *
 *  Description: mutable data type that resizes a W-by-H image using the
 * seam-carving technique
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.awt.Color;

public class SeamCarver {
    private Picture picture; // deep copy of picture object

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException("null argument");
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        Picture copy = new Picture(picture);
        return copy;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {

        // checks if x and y are in picture boundaries
        if (x < 0 || x > (picture.width() - 1) || y < 0 ||
                y > (picture.height() - 1)) {
            throw new IllegalArgumentException("coordinate outside range");
        }

        // calculates x-gradient
        int x1 = border(x + 1, picture.width() - 1);
        int x2 = border(x - 1, picture.width() - 1);
        double xGradient = gradient(picture.get(x1, y), picture.get(x2, y));

        // calculates y-gradient
        int y1 = border(y + 1, picture.height() - 1);
        int y2 = border(y - 1, picture.height() - 1);
        double yGradient = gradient(picture.get(x, y1), picture.get(x, y2));

        double energy = Math.sqrt(xGradient + yGradient);
        return energy;
    }

    // private helper method to calculate gradient
    private double gradient(Color x, Color y) {
        double red = x.getRed() - y.getRed();
        double green = x.getGreen() - y.getGreen();
        double blue = x.getBlue() - y.getBlue();
        double gradient = Math.pow(red, 2) + Math.pow(green, 2) + Math.pow(blue, 2);
        return gradient;
    }

    // private helper method to check and modify border pixel coordinate values
    private int border(int coord, int border) {
        if (coord < 0) {
            return border;
        }
        if (coord > border) {
            return 0;
        }
        // if coord is within boundaries, return coord
        return coord;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transpose();
        int[] seam = findVerticalSeam();
        transpose();
        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] seam = new int[height()]; // array containing indices for vertical seam
        double[][] energy = new double[width()][height()];
        double[][] distTo = new double[width()][height()];
        int[][] edgeTo = new int[width()][height()];

        // calculates appropriate energy values and fills energy[][]
        // sets first row of distTo[] equal to first row of energy[][]
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                energy[col][row] = energy(col, row);
                if (row == 0) distTo[col][row] = energy[col][row];

                    // sets all other cells in distTo[][] as infinitely far
                else distTo[col][row] = Double.POSITIVE_INFINITY;
            }
        }

        // Dijkstra's Algorithm
        for (int i = 1; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                // current minimum energy is energy at distTo[j][i-1]
                double min = distTo[j][i - 1];
                int index = j;

                // compares minimum energy with energy of pixel to the left of
                // distTo[j][i-1]
                if (j > 0) {
                    double left = distTo[j - 1][i - 1];
                    if (left < min) {
                        min = left;
                        index = j - 1;
                    }
                }

                // compares minimum energy with energy of pixel to the right of
                // distTo[j][i-1]
                if (j < width() - 1) {
                    double right = distTo[j + 1][i - 1];
                    if (right < min) {
                        min = right;
                        index = j + 1;
                    }
                }

                // updates respective arrays, if necessary
                double dist = min + energy[j][i];
                if (distTo[j][i] > dist) {
                    distTo[j][i] = dist;
                    edgeTo[j][i] = index;
                }
            }
        }

        double minTotal = Double.POSITIVE_INFINITY;
        int index = 0; // index of smallest value in last row of distTo[][]
        for (int i = 0; i < width(); i++) {
            if (distTo[i][height() - 1] < minTotal) {
                minTotal = distTo[i][height() - 1];
                index = i;
            }
        }


        // fills seam[] using index and edgeTo[]
        for (int i = seam.length - 1; i >= 0; i--) {
            seam[i] = index;
            index = edgeTo[index][i];
        }

        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        validate(seam, false);
        transpose();
        removeVerticalSeam(seam);
        transpose();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        validate(seam, true);
        Picture p = new Picture(width() - 1, height());

        // sets pixels in new picture w/o seam
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width() - 1; j++) {
                if (j < seam[i]) p.set(j, i, picture.get(j, i));
                else p.set(j, i, picture.get(j + 1, i));
            }
        }
        picture = p;
    }

    // checks seam
    private void validate(int[] seam, boolean vertical) {
        if (seam == null) throw new IllegalArgumentException("null argument");

        // orients then copies relevant dimensions
        int length;
        int alternate;
        if (vertical) {
            length = height();
            alternate = width();
        }
        else {
            length = width();
            alternate = height();
        }

        // checks length
        if (seam.length != length) {
            throw new IllegalArgumentException("invalid seam[] length");
        }

        // checks if image is only 1 cell
        if (alternate == 1) {
            throw new IllegalArgumentException("picture width/height equals 1");
        }

        for (int i = 0; i < length; i++) {
            // checks if entry is outside height/width bounds
            if (seam[i] < 0 || seam[i] > alternate - 1) {
                throw new IllegalArgumentException("invalid seam");
            }

            // checks if two adjacent entries differ by more than 1
            if (i != length - 1) {
                int current = seam[i];
                int next = seam[i + 1];
                if (Math.abs(next - current) > 1) {
                    throw new IllegalArgumentException("invalid seam");
                }
            }
        }
    }

    // transposes the photo
    private void transpose() {
        Picture transpose = new Picture(height(), width());
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                transpose.set(j, i, picture.get(i, j));
            }
        }
        // set picture to transpose
        picture = transpose;
    }

    //  unit testing (required)
    public static void main(String[] args) {
        Picture picture = new Picture("6x5.png");
        SeamCarver sc = new SeamCarver(picture);
        System.out.println(sc.width());
        System.out.println(sc.height());

        // checks energy calculations
        System.out.println(Math.sqrt(52024) + ": " + sc.energy(1, 2));
        System.out.println(Math.sqrt(52225) + ": " + sc.energy(1, 1));
        System.out.println(Math.sqrt(52020) + ": " + sc.energy(1, 0));

        // checks picture()
        Picture pic1 = sc.picture();
        pic1.set(0, 0, Color.WHITE);
        Picture pic2 = sc.picture();
        pic1.show();
        pic2.show();

        // checks vertical seam
        int[] seam = sc.findVerticalSeam();
        for (int i = 0; i < seam.length; i++) {
            System.out.println(seam[i]);
        }

        // removes vertical seam
        sc.removeVerticalSeam(seam);

        // checks horizontal seam
        seam = sc.findHorizontalSeam();
        for (int i = 0; i < seam.length; i++) {
            System.out.println(seam[i]);
        }

        // removes horizontal seam
        sc.removeHorizontalSeam(seam);


        // additional unit testing for readme.txt
        Picture picture1 = SCUtility.randomPicture(512, 512);
        SeamCarver sc1 = new SeamCarver(picture1);

        Stopwatch stopwatch = new Stopwatch();
        int[] horizontalSeams = sc1.findHorizontalSeam();
        sc1.removeHorizontalSeam(horizontalSeams);

        int[] verticalSeams = sc1.findVerticalSeam();
        sc1.removeVerticalSeam(verticalSeams);
        double time = stopwatch.elapsedTime();

        StdOut.println(time);
    }
}
