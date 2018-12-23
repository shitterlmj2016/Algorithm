
import java.util.ArrayList;

public class Board {

    private final int n;//dimension
    private final int[][] blocks;

    //Save these values for recomputing
    private final int hamming; // wrong position + steps so far
    private int manhattan; // goal position + steps so far

    private final boolean isGoal;

    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    {
        if (blocks == null)
            throw new IllegalArgumentException();

        int [][]copy = copy(blocks);
        this.blocks = copy;
        this.n = this.blocks.length;
        isGoal = checkGoal();

        int countHam = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.blocks[i][j] != i * n + j + 1)
                    countHam++;
            }
        }

        hamming = --countHam;//deduct 0

        manhattan = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (this.blocks[i][j] == 0)
                    continue;

                int num =this.blocks[i][j];
                int goalI = num / n;
                int goalJ = num % n;
                if (goalJ == 0) {
                    goalI--;
                    goalJ = n - 1;
                } else {
                    goalJ--;
                }

                int diffI = 0;
                int diffJ = 0;

                if (goalI > i)
                    diffI = goalI - i;
                else
                    diffI = i - goalI;
                if (goalJ > j)
                    diffJ = goalJ - j;
                else
                    diffJ = j - goalJ;

                manhattan += diffI + diffJ;
            }

        }


    }

    public int dimension()                 // board dimension n

    {
        return n;
    }

    public int hamming()                   // number of blocks out of place

    {
        return hamming;
    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal

    {
        return manhattan;
    }

    public boolean isGoal()                // is this board the goal board?

    {
        return isGoal;
    }

    public Board twin()                    // a board that is obtained by exchanging any pair of blocks

    {
        int row = 0;
        int col = 0;
        if (blocks[row][col] == 0) {
            int[][] newBlock = copy(blocks);
            int a = newBlock[row + 1][col];
            newBlock[row + 1][col] = newBlock[row][col + 1];
            newBlock[row][col + 1] = a;
            return new Board(newBlock);
        }

        col++;
        if (blocks[row][col] == 0)
            row++;
        int[][] newBlock = copy(blocks);
        int a = newBlock[0][0];
        newBlock[0][0] = newBlock[row][col];
        newBlock[row][col] = a;
        return new Board(newBlock);
    }

    public boolean equals(Object y)        // does this board equal y?

    {
        if (y == null)
            return false;
        if (y == this)
            return true;


        if (y.getClass().isInstance(this)) {

            Board test = (Board) y;

            if (this.n != test.n)
                return false;

            if (this.blocks.equals(test.blocks))
                return true;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (this.blocks[i][j] != test.blocks[i][j])
                        return false;
                }
            }
            return true;


        }

        return false;
    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        ArrayList<Board> neighbors = new ArrayList<Board>();
        int a = 0;
        int b = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    a = i;
                    b = j;
                    break;
                }
            }
        }


        //a b
        //left
        if (a > 0) {
            int[][] copy = copy(blocks);
            exch(copy, a, b, a - 1, b);
            neighbors.add(new Board(copy));
        }
        //right

        if (a < n - 1) {
            int[][] copy = copy(blocks);
            exch(copy, a, b, a + 1, b);
            neighbors.add(new Board(copy));
        }

        //up
        if (b > 0) {
            int[][] copy = copy(blocks);
            exch(copy, a, b, a, b - 1);
            neighbors.add(new Board(copy));
        }

        //down
        if (b < n - 1) {
            int[][] copy = copy(blocks);
            exch(copy, a, b, a, b + 1);
            neighbors.add(new Board(copy));
        }

        return neighbors;
    }

    public String toString()               // string representation of this board (in the output format specified below)

    {
        String result = "";
        result += n;
        result += "\n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result += blocks[i][j];
                result += " ";
            }
            result.substring(2 * n - 1);
            result += "\n";


        }
        return result;
    }

    private void exch(int[][] array, int a, int b, int m, int n) {
        int temp = array[a][b];
        array[a][b] = array[m][n];
        array[m][n] = temp;

    }

    private int[][] copy(int[][] array) {
        int[][] newCopy = new int[array.length][array.length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                newCopy[i][j] = array[i][j];
            }
        }
        return newCopy;
    }


    public static void main(String[] args) // unit tests (not graded)
    {

        int[][] test = {{1, 2}, {3, 0}};


        Board b1 = new Board(test);

        System.out.println(b1.isGoal);


    }


    private boolean checkGoal() {
        if (blocks[n - 1][n - 1] != 0) {
            return false;
        }

        for (int i = 0; i < n - 1; i++) {
            if (blocks[n - 1][i] != (n - 1) * n + i + 1)
                return false;
        }

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != i * n + j + 1)
                    return false;
            }
        }

        return true;

    }


}