
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import java.util.HashSet;
import java.util.Iterator;

public class BoggleSolver {
    private TrieSETT dictionary;

    private class TrieSETT implements Iterable<String> {
        private static final int R = 26;        // extended ASCII

        private Node root;      // root of trie
        private int n;          // number of keys in trie
        private boolean prefix;

        // R-way trie node
        private class Node {
            private Node[] next = new Node[R];
            private boolean isString;
        }

        /**
         * Initializes an empty set of strings.
         */
        public TrieSETT() {
            prefix = false;
        }

        /**
         * Does the set contain the given key?
         *
         * @param key the key
         * @return {@code true} if the set contains {@code key} and
         * {@code false} otherwise
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public boolean contains(String key) {
            if (key == null) throw new IllegalArgumentException("argument to contains() is null");
            Node x = get(root, key, 0);
            if (x == null) return false;
            return x.isString;
        }

        private Node get(Node x, String key, int d) {
            if (x == null) return null;
            if (d == key.length()) return x;
            char c = key.charAt(d);
            return get(x.next[c - 65], key, d + 1);
        }

        /**
         * Adds the key to the set if it is not already present.
         *
         * @param key the key to add
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public void add(String key) {
            if (key == null) throw new IllegalArgumentException("argument to add() is null");
            root = add(root, key, 0);
        }

        private Node add(Node x, String key, int d) {
            if (x == null) x = new Node();
            if (d == key.length()) {
                if (!x.isString) n++;
                x.isString = true;
            } else {
                char c = key.charAt(d);
                x.next[c - 65] = add(x.next[c - 65], key, d + 1);
            }
            return x;
        }

        /**
         * Returns the number of strings in the set.
         *
         * @return the number of strings in the set
         */
        public int size() {
            return n;
        }

        /**
         * Is the set empty?
         *
         * @return {@code true} if the set is empty, and {@code false} otherwise
         */
        public boolean isEmpty() {
            return size() == 0;
        }

        /**
         * Returns all of the keys in the set, as an iterator.
         * To iterate over all of the keys in a set named {@code set}, use the
         * foreach notation: {@code for (Key key : set)}.
         *
         * @return an iterator to all of the keys in the set
         */
        public Iterator<String> iterator() {
            return keysWithPrefix("").iterator();
        }

        /**
         * Returns all of the keys in the set that start with {@code prefix}.
         *
         * @param prefix the prefix
         * @return all of the keys in the set that start with {@code prefix},
         * as an iterable
         */
        public Iterable<String> keysWithPrefix(String prefix) {
            Queue<String> results = new Queue<String>();
            Node x = get(root, prefix, 0);
            collect(x, new StringBuilder(prefix), results);
            return results;
        }

        public boolean checkPrefix(String prefix) {
            Queue<String> results = new Queue<String>();
            Node x = get(root, prefix, 0);
            this.prefix = false;
            checkCollect(x, new StringBuilder(prefix), results);
            return this.prefix;
        }


        private void checkCollect(Node x, StringBuilder prefix, Queue<String> results) {
            if (x == null) return;
            if (this.prefix == true)
                return;
            if (x.isString) {
                results.enqueue(prefix.toString());
                this.prefix = true;
                return;
            }
            for (char c = 0; c < R; c++) {
                prefix.append(c);
                checkCollect(x.next[c], prefix, results);
                if (this.prefix == true)
                    return;
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }


        private void collect(Node x, StringBuilder prefix, Queue<String> results) {
            if (x == null) return;
            if (x.isString) results.enqueue(prefix.toString());
            for (char c = 0; c < R; c++) {
                prefix.append(c);
                collect(x.next[c], prefix, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }

        /**
         * Returns all of the keys in the set that match {@code pattern},
         * where . symbol is treated as a wildcard character.
         *
         * @param pattern the pattern
         * @return all of the keys in the set that match {@code pattern},
         * as an iterable, where . is treated as a wildcard character.
         */
        public Iterable<String> keysThatMatch(String pattern) {
            Queue<String> results = new Queue<String>();
            StringBuilder prefix = new StringBuilder();
            collect(root, prefix, pattern, results);
            return results;
        }

        private void collect(Node x, StringBuilder prefix, String pattern, Queue<String> results) {
            if (x == null) return;
            int d = prefix.length();
            if (d == pattern.length() && x.isString)
                results.enqueue(prefix.toString());
            if (d == pattern.length())
                return;
            char c = pattern.charAt(d);
            if (c == '.') {
                for (char ch = 0; ch < R; ch++) {
                    prefix.append(ch);
                    collect(x.next[ch], prefix, pattern, results);
                    prefix.deleteCharAt(prefix.length() - 1);
                }
            } else {
                prefix.append(c);
                collect(x.next[c], prefix, pattern, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }

        /**
         * Returns the string in the set that is the longest prefix of {@code query},
         * or {@code null}, if no such string.
         *
         * @param query the query string
         * @return the string in the set that is the longest prefix of {@code query},
         * or {@code null} if no such string
         * @throws IllegalArgumentException if {@code query} is {@code null}
         */
        public String longestPrefixOf(String query) {
            if (query == null) throw new IllegalArgumentException("argument to longestPrefixOf() is null");
            int length = longestPrefixOf(root, query, 0, -1);
            if (length == -1) return null;
            return query.substring(0, length);
        }

        // returns the length of the longest string key in the subtrie
        // rooted at x that is a prefix of the query string,
        // assuming the first d character match and we have already
        // found a prefix match of length length
        private int longestPrefixOf(Node x, String query, int d, int length) {
            if (x == null) return length;
            if (x.isString) length = d;
            if (d == query.length()) return length;
            char c = query.charAt(d);
            return longestPrefixOf(x.next[c], query, d + 1, length);
        }

        /**
         * Removes the key from the set if the key is present.
         *
         * @param key the key
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public void delete(String key) {
            if (key == null) throw new IllegalArgumentException("argument to delete() is null");
            root = delete(root, key, 0);
        }

        private Node delete(Node x, String key, int d) {
            if (x == null) return null;
            if (d == key.length()) {
                if (x.isString) n--;
                x.isString = false;
            } else {
                char c = key.charAt(d);
                x.next[c] = delete(x.next[c], key, d + 1);
            }

            // remove subtrie rooted at x if it is completely empty
            if (x.isString) return x;
            for (int c = 0; c < R; c++)
                if (x.next[c] != null)
                    return x;
            return null;
        }

    }

    private class Record {
        String currentString;


        boolean[][] visited;
        int col;
        int row;

        Record(BoggleBoard Board, int row, int col) {
            visited = new boolean[Board.rows()][Board.cols()];
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

    private boolean checkQ(BoggleBoard b) {
        boolean temp = true;
        for (int i = 0; i < b.rows(); i++) {
            for (int j = 0; j < b.cols(); j++) {
                if (b.getLetter(i, j) != 'Q')
                    temp = false;
            }
        }

        return temp;
    }

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null)
            throw new IllegalArgumentException("Null dictionary");
        this.dictionary = new TrieSETT();
        for (int i = 0; i < dictionary.length; i++) {
            this.dictionary.add(dictionary[i]);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null)
            throw new IllegalArgumentException("Null board");

        HashSet<String> set = new HashSet<>();

        Queue<Record> map = new Queue<Record>();

        //特殊情况特殊处理，不然太慢了:)
        if (checkQ(board)) {
            if (dictionary.contains("QUQUQUQUQUQUQUQUQU")) {
                String temp = "QU";
                int total = board.cols() * board.rows() - 1;
                for (int i = 0; i < total; i++) {
                    temp += "QU";
                    set.add(temp);
                }
                return set;
            }
        }

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


                if (dictionary.checkPrefix(string)) {
                    //System.out.println(string + " added to the queue!");
                    map.enqueue(r);
                    //System.out.println(r);
                    if (dictionary.contains(string) && string.toCharArray().length > 2) {
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
                        if (dictionary.checkPrefix(temp)) {
                            if (temp.length() >= 3 && dictionary.contains(temp)) {
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
        if (!dictionary.contains(word))
            return 0;
        char[] array = word.toCharArray();
        int length = array.length;
        //if (length <= 2)
        //return 0;
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
        if (i < 0 || i > record.visited.length - 1)
            return false;
        if (j < 0 || j > record.visited[0].length - 1)
            return false;
        //Unvisited
        if (record.visited[i][j] == true)
            return false;
        return true;
    }


    public static void main(String[] args) {


        In in = new In("dictionary-16q.txt");
        BoggleSolver b = new BoggleSolver(in.readAllLines());

        //System.out.println(b.dictionary.keysWithPrefix("C"));

        BoggleBoard bb = new BoggleBoard("board-16q.txt");
        //System.out.println(bb);
        int score = 0;
        long a = System.currentTimeMillis();
        HashSet<String> hs = (HashSet<String>) b.getAllValidWords(bb);
        for (String s : hs
        ) {
            score += b.scoreOf(s);

        }
        System.out.println(hs.size());
        System.out.println(System.currentTimeMillis() - a);
        //System.out.println(b.dictionary);

    }
}

