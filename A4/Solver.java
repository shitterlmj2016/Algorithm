import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private Node root;
    private int steps;
    private MinPQ<Node> pq;

    private class Node implements Comparable<Node> {

        private Node father;
        private int steps;
        private Board board;

        public Node(Board board, int steps, Node father) {
            this.board = board;
            this.steps = steps;
            this.father = father;
        }

        public Board getBoard() {
            return board;
        }

        public Node getFather() {
            return father;
        }

        public int getSteps() {
            return steps;
        }

        public int getPriority() {
            return steps + board.manhattan();
        }

        public int compareTo(Node n) {
            return Integer.compare(this.getPriority(), n.getPriority());
        }
    }


    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null)
            throw new IllegalArgumentException("Null arguments!");

        root = new Node(initial, 0, null);
        steps = 0;
        pq.insert(root);


        int[][] a = {{1, 2}, {0, 3}};
        MinPQ pq = new MinPQ();
        Board b = new Board(a);
        b.manhattan();
    }

    public boolean isSolvable()            // is the initial board solvable?
    {

        //两个同时找，总有一个能找到，谁先找到谁出结果
        return true;
    }

    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        return 1;
    }

    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        return null;
    }

    public static void main(String[] args) // solve a slider puzzle (given below)
    {

//        MinPQ<Node> test = new MinPQ<>();
//        int[][] t1 = {{0, 1}, {2, 3}};
//        int[][] t2 = {{1, 0}, {3, 2}};
//        int[][] t3 = {{1, 2}, {3, 0}};
//        Board b1 = new Board(t1);
//        Board b2 = new Board(t2);
//        Board b3 = new Board(t3);
//        Node a = new Node(b1, 0, null);
//        Node b = new Node(b2, 1, a);
//        Node c = new Node(b3, 2, b);
//
//        test.insert(a);
//        test.insert(b);
//        test.insert(c);
//
//        System.out.println(test.delMin().getBoard());
//        System.out.println(test.delMin().getBoard());
//        System.out.println(test.delMin().getBoard());
//
//        System.out.println("------");
//
//        Node pointer = c;
//        while(pointer.getFather()!=null)
//        {   pointer=pointer.getFather();
//            System.out.println(pointer.getBoard());
//
//        }


    }


}


