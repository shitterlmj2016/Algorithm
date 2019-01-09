import java.util.Arrays;

public class CircularSuffixArray {
    private String s;
    private int[] index;
    private int length;

    public CircularSuffixArray(String s)    // circular suffix array of s
    {
        if (s == null)
            throw new IllegalArgumentException("Null argument in construction!");
        this.s = s;
        length = s.length();
        index = new int[length];
        Suffix[] suffixes = new Suffix[length];
        for (int i = 0; i <length ; i++) {
            suffixes[i]=new Suffix(i);
        }
        Arrays.sort(suffixes);
        for (int i = 0; i <length ; i++) {
            index[i]=suffixes[i].id;
        }
    }

    public int length()                     // length of s
    {
        return length;
    }

    public int index(int i)                 // returns index of ith sorted suffix
    {
        if (i < 0 || i > length - 1)
            throw new IllegalArgumentException("Index out of bound :" + i);
        return index[i];
    }

    public static void main(String[] args)  // unit testing (required)
    {
        CircularSuffixArray c = new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i <c.length ; i++) {
            System.out.println(c.index(i));
        }
    }

    private class Suffix implements Comparable {
        private int id;//no need for array, use id and string s


        public Suffix(int id) {
            this.id = id;
        }


        @Override
        public int compareTo(Object o) {//no need for array, use id and string s
            Suffix that = (Suffix) o;
            for (int i = 0; i < length; i++) {
                char a = s.charAt((i + id) % length);
                char b = s.charAt((that.id + i) % length);
                if (a > b)
                    return 1;
                if (a < b)
                    return -1;
            }
            return 0;
        }
    }
}