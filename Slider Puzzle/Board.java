/* *****************************************************************************
 *  Name:    Emily Kang
 *  NetID:   etkang
 *  Precept: P05
 *
 *  Description: immutable data type that models an n-by-n board with sliding tiles
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;

public class Board {
    private final int[] board; // 1d array representation of 2d board
    private final int n; // 2d board dimension
    private final int hamming; // hamming distance
    private final int manhattan; // manhattan distance
    private int xZero, yZero; // x and y coordinates of blank tile

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        board = new int[n * n];
        int hd = 0;
        int md = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                int i = row * n + col;
                board[i] = tiles[row][col];
                if (board[i] != 0) {
                    if (board[i] != i + 1) hd++;

                    int dx = Math.abs((board[i] - 1) / n - row);
                    int dy = Math.abs((board[i] - 1) % n - col);
                    md += dx + dy;
                }
                else {
                    xZero = row;
                    yZero = col;
                }

            }
        }
        hamming = hd;
        manhattan = md;

    }

    // private constructor to create Board with 1d array
    private Board(int[] tiles) {
        n = (int) Math.sqrt(tiles.length);
        board = new int[n * n];
        int hd = 0;
        int md = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                int i = row * n + col;
                board[i] = tiles[i];
                if (board[i] != 0) {
                    if (board[i] != i + 1) hd++;

                    int dx = Math.abs((board[i] - 1) / n - row);
                    int dy = Math.abs((board[i] - 1) % n - col);
                    md += dx + dy;
                }
                else {
                    xZero = row;
                    yZero = col;
                }
            }
        }
        hamming = hd;
        manhattan = md;

    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n + "\n");
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                sb.append(String.format("%2d ", tileAt(row, col)));
            }
            sb.append("\n");
        }

        String s = sb.toString();
        return s;
    }

    // tile at (row, col) or 0 if blank
    public int tileAt(int row, int col) {
        if (row >= 0 && row < n && col >= 0 && col < n) {
            return board[row * n + col];
        }
        throw new IllegalArgumentException();
    }

    // board size n
    public int size() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return (manhattan() == 0);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.size() != this.size() || that.manhattan() != this.manhattan()
                || that.hamming() != this.hamming()) return false;
        
        for (int i = 0; i < n * n; i++) {
            if (that.board[i] != this.board[i]) return false;
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Bag<Board> neighbors = new Bag<Board>();
        if (xZero > 0) {
            int temp = neighborGenerator(xZero - 1, yZero);
            neighbors.add(new Board(board));
            board[(xZero - 1) * n + yZero] = temp;
        }
        if (yZero > 0) {
            int temp = neighborGenerator(xZero, yZero - 1);
            neighbors.add(new Board(board));
            board[(xZero) * n + yZero - 1] = temp;

        }
        if (xZero < n - 1) {
            int temp = neighborGenerator(xZero + 1, yZero);
            neighbors.add(new Board(board));
            board[(xZero + 1) * n + yZero] = temp;
        }
        if (yZero < n - 1) {
            int temp = neighborGenerator(xZero, yZero + 1);
            neighbors.add(new Board(board));
            board[(xZero) * n + yZero + 1] = temp;
        }
        board[xZero * n + yZero] = 0;
        return neighbors;
    }

    // modifies board to swap blank tile (0) with tile located at (x,y) in the
    // 2d array
    // @returns label of tile originally at position (x,y)
    private int neighborGenerator(int x, int y) {
        int temp = board[x * n + y];
        board[xZero * n + yZero] = temp;
        board[x * n + y] = 0;
        return temp;

    }

    // is this board solvable?
    public boolean isSolvable() {
        int[] sorted = new int[n * n - 1];
        for (int i = 1; i < n * n; i++) {
            sorted[i - 1] = i;
        }
        int inversions = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                int element = board[row * n + col];
                if (element != 0) {
                    inversions += getIndex(sorted, element);
                    if (sorted.length != 0) sorted = removeElement(sorted, element);
                }
            }
        }

        if ((n % 2 == 1) && (inversions % 2 == 0)) return true;

        if ((n % 2 == 0) && ((inversions + xZero) % 2 == 1)) return true;

        return false;
    }

    // returns int[] a with int x removed
    private int[] removeElement(int[] a, int x) {
        int[] array = new int[a.length - 1];
        int index = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != x) {
                array[index] = a[i];
                index++;
            }
        }
        return array;
    }

    // returns index of int x in int[] a, return -1 if x is not in a[]
    private int getIndex(int[] a, int x) {
        for (int index = 0; index < a.length; index++) {
            if (a[index] == x) {
                return index;
            }
        }
        return -1;
    }

    // unit testing (required)
    public static void main(String[] args) {
        int[][] test = { { 1, 2, 3 }, { 8, 4, 6 }, { 0, 5, 7 } };
        int[][] solution = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        int[][] neighbors = { { 1, 0, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };
        int[][] hamming = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };

        Board b = new Board(test);
        Board s = new Board(solution);
        Board n = new Board(neighbors);
        Board h = new Board(hamming);

        System.out.println(b);
        System.out.println(b.size());
        System.out.println(h.hamming());
        System.out.println(h.manhattan());
        System.out.println(b.isSolvable());

        System.out.println(s.isGoal());
        for (Board board : n.neighbors()) {
            System.out.println(board);
        }

        System.out.println(s.equals(n));
        // System.out.println(s.equals(s));

        System.out.println(n.tileAt(1, 1));

    }

}
