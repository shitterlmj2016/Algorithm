
import edu.princeton.cs.algs4.In;

import edu.princeton.cs.algs4.Queue;

import edu.princeton.cs.algs4.TrieSET;

import java.util.HashSet;

public class BoggleSolver {
    private TrieSET dictionary;

    private class Record {
        String currentString;
        BoggleBoard board;
        boolean[][] visited;
        int col;
        int row;

        Record(BoggleBoard Board, int row, int col) {
            this.board = Board;
            visited = new boolean[board.rows()][board.cols()];
            currentString = "";
            this.col = col;
            this.row = row;
        }

        @Override
        public String toString() {
            String s = "";
            s += "Current string is " + currentString;
            s += "; Current position is " + row + " " + col;
            return s;
        }
    }

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null)
            throw new IllegalArgumentException("Null dictionary");
        this.dictionary = new TrieSET();
        for (int i = 0; i < dictionary.length; i++) {
            this.dictionary.add(dictionary[i]);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null)
            throw new IllegalArgumentException("Null board");


        long a = System.currentTimeMillis();
        HashSet<String> set = new HashSet<>();

        Queue<Record> map = new Queue<Record>();


        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                Record r = new Record(board, i, j);
                r.visited[i][j] = true;
                char letter = board.getLetter(i, j);
                String string = String.valueOf(letter);
                if (letter == 'Q') {
                    string += "U";
                }
                r.currentString += string;
                //test
                //System.out.println(r.currentString);
                //

                edu.princeton.cs.algs4.Queue<String> it = (edu.princeton.cs.algs4.Queue<String>) dictionary.keysWithPrefix(string);
                if (!it.isEmpty()) {
                    //System.out.println(string + " added to the queue!");
                    map.enqueue(r);
                    //System.out.println(r);
                    if (dictionary.contains(string)) {
                        set.add(string);
                        //System.out.println(string + " is added to the set!");
                    }
                }
            }
        }

        while (!map.isEmpty()) {
            Record r = map.dequeue();
            int col = r.col;
            int row = r.row;
            for (int i = row - 1; i <= row + 1; i++)
                for (int j = col - 1; j <= col + 1; j++) {
                    if (check(r, i, j)) {
                        String temp = r.currentString;
                        char c = board.getLetter(i, j);
                        temp += String.valueOf(c);
                        if (c == 'Q')
                            temp += "U";
                        edu.princeton.cs.algs4.Queue<String> it = (edu.princeton.cs.algs4.Queue<String>) dictionary.keysWithPrefix(temp);
                        if (!it.isEmpty()) {
                            if (dictionary.contains(temp)) {
                                set.add(temp);
                                //
                              //  System.out.println(temp + " is added to the dictionary!");
                            }
                            Record child = new Record(board, i, j);
                            child.visited = clone(r.visited);
                            child.visited[i][j] = true;
                            child.currentString = temp;
                            map.enqueue(child);
                            //System.out.println(child);
                        }
                    }
                }
        }


        //System.out.println(System.currentTimeMillis() - a);
        return set;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        char[] array = word.toCharArray();
        int length = array.length;
        if (length <= 2)
            return 0;
        if (length == 3 || length == 4)
            return 1;
        if (length == 5)
            return 2;
        if (length == 6)
            return 3;
        if (length == 7)
            return 5;
        if (length >= 8)
            return 11;
        return 0;
    }

    private boolean[][] clone(boolean[][] array) {
        boolean[][] clone = new boolean[array.length][];
        for (int i = 0; i < array.length; i++) {
            clone[i] = array[i].clone();
        }

        return clone;
    }

    private boolean check(Record record, int i, int j) {

        //is in bound
        if (i < 0 || i > record.board.rows() - 1)
            return false;
        if (j < 0 || j > record.board.cols() - 1)
            return false;
        //Unvisited
        if (record.visited[i][j] == true)
            return false;
        return true;
    }


    public static void main(String[] args) {


        In in = new In("dictionary-yawl.txt");
        BoggleSolver b = new BoggleSolver(in.readAllLines());

        //System.out.println(b.dictionary.keysWithPrefix("C"));

        BoggleBoard bb = new BoggleBoard("board-points26539.txt");
        //System.out.println(bb);
        int score = 0;
        HashSet<String> hs = (HashSet<String>) b.getAllValidWords(bb);
        for (String s : hs
        ) {
            score += b.scoreOf(s);

        }
        System.out.println(score);

    }
}
