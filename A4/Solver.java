import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Stack;

public class Solver {
    private Node root;
    //pq不要作为成员变量
    private int steps;
    private boolean solvable;

    private Stack<Board> answerStack;

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

        public boolean inFather(Node n) {
            Node pointer = this;
//
//            if (pointer.getBoard().equals(n.getBoard()))
//                return true;

            if (pointer.father != null) {
                pointer = pointer.father;

                if (pointer.getBoard().equals(n.getBoard()))
                    return true;
            }
            //只找一层最快！！



            return false;
        }


    }


    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null)
            throw new IllegalArgumentException("Null arguments!");

        root = new Node(initial, 0, null);
        Node troot = new Node(initial.twin(), 0, null);


        steps = 0;

        MinPQ<Node> pq = new MinPQ<Node>();
        pq.insert(root);
//        System.out.println(root.getBoard());


        MinPQ<Node> tpq = new MinPQ<Node>();
        tpq.insert(troot);

        while (!pq.isEmpty()) {
            Node current = pq.delMin();
            if (current.getBoard().isGoal()) {
                solvable = true;

                answerStack = new Stack<Board>();
                steps = current.getSteps();

//
//                System.out.println("Answers found!");
//                System.out.println("Steps: " + current.getSteps());
//                System.out.println("Traces to father");

                Node pointer = current;

                answerStack.push(pointer.getBoard());
                while (pointer.getFather() != null) {
                    pointer = pointer.getFather();
                    answerStack.push(pointer.getBoard());
                }
                break;
            }


            Iterable<Board> neighbors = current.getBoard().neighbors();
            for (Board neighbour : neighbors) {
                Node ins = new Node(neighbour, current.getSteps() + 1, current);
                if (!current.inFather(ins))
                    pq.insert(ins);
            }


            if (!tpq.isEmpty()) {
                Node tcurrent = tpq.delMin();

                if (tcurrent.getBoard().isGoal()) {
                    solvable = false;
//                   System.out.println("No answer!!!");
                    break;
                }

//                ArrayList<Board> tneighbors = (ArrayList<Board>) tcurrent.getBoard().neighbors();
//                for (int i = 0; i < tneighbors.size(); i++) {
//                    Node ins = new Node(tneighbors.get(i), tcurrent.getSteps() + 1, tcurrent);
//                    if (!tcurrent.inFather(ins))
//                        tpq.insert(ins);
//
//
//                }

                Iterable<Board> tneighbors = tcurrent.getBoard().neighbors();
                for (Board tneighbour : tneighbors) {
                    Node ins = new Node(tneighbour, tcurrent.getSteps() + 1, tcurrent);
                    if (!tcurrent.inFather(ins))
                        tpq.insert(ins);
                }


            }


        }


//        int[][] a = {{1, 2}, {0, 3}};
//        MinPQ pq = new MinPQ();
//        Board b = new Board(a);
//        b.manhattan();
    }

    public boolean isSolvable()            // is the initial board solvable?
    {

        //两个同时找，总有一个能找到，谁先找到谁出结果
        return solvable;
    }

    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if (!solvable)
            return -1;
        return this.steps;
    }

    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {

        if (!isSolvable())
            return null;
        ArrayList<Board> ans = new ArrayList<Board>();
        Stack<Board> stack = (Stack<Board>) answerStack.clone();


        while (!stack.isEmpty()) {
            ans.add(stack.pop());
        }
        return ans;
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

//        int[][] test = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
//        int[][] no = {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};
//        int[][] ss = {{2,1},{3,0}};
//
//        Solver s = new Solver(new Board(test));
//        System.out.println("---------------------------------------");
//        ArrayList<Board> ab = (ArrayList<Board>) s.solution();
//        for (int i = 0; i < ab.size(); i++) {
//            System.out.println(ab.get(i));
//
//        }


        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }


    }


}


