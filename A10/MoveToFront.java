import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;


public class MoveToFront {
    // apply move-to-front encoding, reading from standard input and writing to standard output


    private char[] list;
    private char[] position;

    public MoveToFront() {
        list = new char[256];
        position = new char[256];
        for (char i = 0; i < 256; i++) {
            list[i] = i;
            position[i] = i;
        }

    }

    private void shift(char i) {
        char index = position[i];
        for (char j = index; j > 0; j--) {
            position[list[j - 1]] += 1;
            list[j] = list[j - 1];

        }
        position[i] = 0;
        list[0] = i;
        System.out.print("PosA: ");
        HexDump.dump(position['A']);
    }

    public static void encode() {
        MoveToFront mtf = new MoveToFront();

        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            BinaryStdOut.write(mtf.position[c]);
            HexDump.dump(mtf.position[c]);

            mtf.shift(c);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {

    }

    private void printPosition() {
        for (int i = 0; i < position.length; i++) {
            HexDump.dump(position[i]);
        }
    }

    private void printList() {
        for (int i = 0; i < position.length; i++) {
            HexDump.dump(list[i]);
        }
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
//        if (args[0].equals("-")) encode();
//        else if (args[0].equals("+")) decode();
//        else throw new IllegalArgumentException("Illegal command line argument");

        MoveToFront m = new MoveToFront();
        m.encode();



    }

}