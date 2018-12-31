public class Outcast {

    private WordNet wordNet;

    public Outcast(WordNet wordnet)         // constructor takes a WordNet object
    {
        if (wordnet == null)
            throw new IllegalArgumentException("Null");
        this.wordNet = wordnet;

    }

    public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast

    {
        int[] cast = new int[nouns.length];
        for (int i = 0; i < nouns.length; i++) {
            for (int j = 0; j < nouns.length; j++) {
                if (j != i)
                    cast[i] = cast[i] + wordNet.distance(nouns[i], nouns[j]);
            }
        }

        int max = cast[0];
        String temp = nouns[0];
        for (int i = 0; i < cast.length; i++) {
            if (cast[i] > max) {
                max = cast[i];
                temp = nouns[i];
            }
        }
        return temp;
    }

    public static void main(String[] args)  // see test client below
    {

    }

}