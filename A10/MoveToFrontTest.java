import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFrontTest
{
    private static final int R = 256;
    private char[] charWithInd, indOfChar;

    public MoveToFrontTest()
    {
        charWithInd = new char[R];
        indOfChar = new char[R];
        for (char i = 0; i < R; i++)
        {
            charWithInd[i] = i;
            indOfChar[i] = i;
        }
    }
    private void shift(char ind, char zeroth)
    {
        for (char i = ind; i > 0; i--)
        {
            char x = charWithInd[i-1];
            charWithInd[i] = x;
            indOfChar[x] = i;
        }
        indOfChar[zeroth] = 0;
        charWithInd[0] = zeroth;
    }
    // apply move-to-front encoding,
    //reading from standard input and writing to standard output
    public static void encode()
    {
        MoveToFrontTest mtf = new MoveToFrontTest();

        while (!BinaryStdIn.isEmpty())
        {
            char c = BinaryStdIn.readChar();

            char ind = mtf.indOfChar[c];
            //System.out.println(c);
            BinaryStdOut.write(ind);
            //BinaryStdOut.flush();

            mtf.shift(ind, c);
        }
        BinaryStdOut.close();

    }

    // apply move-to-front decoding,
    //reading from standard input and writing to standard output
    public static void decode()
    {
        MoveToFrontTest mtf = new MoveToFrontTest();
        while (!BinaryStdIn.isEmpty())
        {
            char ind = BinaryStdIn.readChar();
            char c = mtf.charWithInd[ind];

            BinaryStdOut.write(c);

            mtf.shift(ind, c);
        }
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args)
    {
        if (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}