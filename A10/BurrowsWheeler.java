import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;


public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output


    private class Letter {
        int id;
        char c;

        Letter(int id, char c) {
            this.c = c;
            this.id = id;
        }
    }

    public static void transform() {
        String s = BinaryStdIn.readString();
        //System.out.println(s);
        CircularSuffixArray c = new CircularSuffixArray(s);
        int first = 0;
        char[] output = new char[s.length()];
        for (int i = 0; i < c.length(); i++) {
            if (c.index(i) == 0)
                first = i;
            output[i] = s.charAt((c.index(i) + c.length() - 1) % c.length());

        }

        BinaryStdOut.write(first);
        for (int j = 0; j < s.length(); j++) {
            BinaryStdOut.write(output[j]);
        }
        BinaryStdOut.close();
    }


    private void decode(String s, int start) {
        Letter[] last = new Letter[s.length()];
        for (int i = 0; i < s.length(); i++) {
            last[i] = new Letter(i, s.charAt(i));
        }

        int[] count = new int[257];

        for (int i = 0; i < s.length(); i++) {
            count[last[i].c + 1]++;
        }

        for (int i = 0; i < 256; i++) {
            count[i + 1] += count[i];
        }


        Letter[] temp = new Letter[s.length()];
        for (int i = 0; i < s.length(); i++) {
            temp[count[last[i].c]++] = last[i];
        }

//        for (int i = 0; i < s.length(); i++) {
//
//            System.out.println(temp[i].c + " :" + temp[i].id);
//        }

        char[] out = new char[s.length()];
        int pointer = start;
        for (int i = 0; i < s.length(); i++) {
           // System.out.println(pointer);
            out[i] = s.charAt(temp[pointer].id);
            pointer = temp[pointer].id;
        }

        for (int i = 0; i < s.length(); i++) {
            BinaryStdOut.write(out[i]);
        }
        BinaryStdOut.close();
    }


    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {

        int start = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        BurrowsWheeler b = new BurrowsWheeler();
        b.decode(s, start);
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}