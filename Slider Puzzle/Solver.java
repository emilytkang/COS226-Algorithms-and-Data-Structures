/* *****************************************************************************
 *  Name:    Emily Kang
 *  NetID:   etkang
 *  Precept: P05
 *
 *  Description: creates an immutable data type Solver and implements A* search
 * to solve n-by-n slider puzzles
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private SearchNode solution; // completed puzzle node

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        if (!initial.isSolvable()) throw new IllegalArgumentException();
        MinPQ<SearchNode> gameTree = new MinPQ<SearchNode>(); // minimum priority queue

        SearchNode node = new SearchNode(initial, null, 0);
        gameTree.insert(node);

        while (true) {
            SearchNode min = gameTree.delMin();

            if (min.board.isGoal()) {
                solution = min;
                break;
            }

            for (Board b : min.board.neighbors()) {
                if (min.previous == null) {
                    gameTree.insert(new SearchNode(b, min, min.moves + 1));
                }
                else if (!(b.equals(min.previous.board))) {
                    gameTree.insert(new SearchNode(b, min, min.moves + 1));
                }
            }
        }
    }

    // private helper SearchNode nested class
    private class SearchNode implements Comparable<SearchNode> {
        private final int moves; // num moves made to reach board
        private final int priority; // priority ranking of board
        private final SearchNode previous; // parent SearchNode
        private final Board board; // board

        // SearchNode constructor
        public SearchNode(Board b, SearchNode p, int m) {
            board = b;
            previous = p;
            moves = m;
            priority = board.manhattan() + moves;
            // priority = board.hamming() + moves;
        }

        //
        public int compareTo(SearchNode that) {
            return Integer.compare(this.priority, that.priority);
        }
    }


    // min number of moves to solve initial board
    public int moves() {
        return solution.moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        Stack<Board> s = new Stack<Board>();
        s.push(solution.board);
        SearchNode parent = solution.previous;
        while (parent != null) {
            s.push(parent.board);
            parent = parent.previous;
        }
        return s;
    }

    // test client (see below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] b = new int[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                b[row][col] = in.readInt();
            }
        }

        Board initial = new Board(b);
        if (!initial.isSolvable()) {
            System.out.println("Unsolvable puzzle");
        }
        else {
            Solver s = new Solver(initial);
            System.out.println("Minimum number of moves = " + s.moves());
            System.out.println();
            for (Board board : s.solution()) {
                System.out.println(board);
            }
        }
    }
}
