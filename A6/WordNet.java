


import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import edu.princeton.cs.algs4.ST;

import java.util.ArrayList;


public class WordNet {
    private int sLength;
    private ArrayList<String> words;
    private String[] array;
    private Digraph d;
    private ST<String, Bag<Integer>> set;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException();
        In sIn = new In(synsets);
        In hIn = new In(hypernyms);

        String[] synset = sIn.readAllLines();
        String[] hypernym = hIn.readAllLines();

//        if (synset.sLength != hypernym.sLength)
//            throw new IllegalArgumentException();

        sLength = synset.length;
        int hLength = hypernym.length;
        words = new ArrayList<String>();
        set = new ST<String, Bag<Integer>>();

        for (int i = 0; i < sLength; i++) {
            String[] spilt = synset[i].split(",");

            words.add(spilt[1]);
            String[] words = spilt[1].split(" ");

            for (int j = 0; j < words.length; j++) {


                if (!set.contains(words[j])) {
                    Bag<Integer> bag = new Bag<Integer>();
                    bag.add(Integer.parseInt(spilt[0]));
                    set.put(words[j], bag);
                } else {
                    Bag<Integer> bag = set.get(words[j]);
                    bag.add(Integer.parseInt(spilt[0]));
                    set.put(words[j], bag);
                }
            }

        }


        d = new Digraph(sLength);


        for (int i = 0; i < hLength; i++) {
            String[] spilt = hypernym[i].split(",");

            if (spilt.length <= 1) {
                //rootNumber++;
                continue;
            }
            for (int j = 1; j < spilt.length; j++) {

                d.addEdge(Integer.parseInt(spilt[0]), Integer.parseInt(spilt[j]));
            }
        }


        DirectedCycle dc = new DirectedCycle(d);
        if (dc.hasCycle() || (sLength - hLength) > 1)
            throw new IllegalArgumentException(String.valueOf(sLength - hLength));


        array = new String[words.size()];
        for (int i = 0; i < sLength; i++) {
            array[i] = words.get(i);

        }

    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return set.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {

//        if (indexOf(array, word) != -1)
//            return true;
//
//        for (int i = 0; i < words.size(); i++) {
//            if (isIn(words.get(i), word))
//                return true;
//        }
        return set.contains(word);
    }
//
//    private int indexOf(String[] a, String key) {
//        int lo = 0;
//        int hi = a.length - 1;
//        while (lo <= hi) {
//            // Key is in a[lo..hi] or not present.
//
//            int mid = lo + (hi - lo) / 2;
//
//            if (key.compareTo(a[mid]) < 0) hi = mid - 1;
//            else if (key.compareTo(a[mid]) > 0) lo = mid + 1;
//            else return mid;
//        }
//        return -1;
//    }


    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA)||!isNoun(nounB))
            throw new IllegalArgumentException("no such noun");

        SAP sap = new SAP(d);


        Bag b1 = set.get(nounA);
        Bag b2 = set.get(nounB);
        return sap.length(b1, b2);

    }


//    private ArrayList<Integer> getAll(String nounA) {
//        if (!isNoun(nounA))
//            throw new IllegalArgumentException("no such noun");
//        ArrayList<Integer> al = new ArrayList<Integer>();
//
//        Bag<Integer> bag = set.get(nounA);
//        for (int i : bag
//        ) {
//            al.add(i);
//
//        }
//        return al;
//    }

//        while (nounA.compareTo(words.get(a)) == 0) {
//            al.add(a);
//            a--;
//        }
//
//        a++;
//
//        while (nounA.compareTo(words.get(a)) == 0) {
//            if (!al.contains(a))
//                al.add(a);
//            a++;
//        }


    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("sap2");

        SAP sap = new SAP(d);

        Bag b1 = set.get(nounA);
        Bag b2 = set.get(nounB);

        return array[sap.ancestor(b1, b2)];
    }

//    private boolean isIn(String synset, String noun) {
//        String[] split = synset.split(" ");
//        for (int i = 0; i < split.length; i++) {
//            if (split[i].compareTo(noun) == 0)
//                return true;
//        }
//
//        return false;
//    }


    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet("synsets.txt", "hypernyms.txt");

        System.out.println(wn.sap("cat","lion"));

    }
}