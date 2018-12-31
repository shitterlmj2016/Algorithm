import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

//243
public class ShortestWordDistance {
    public static int shortestDistance(String[] words, String word1, String word2) {
        int i = 0;
        int j = 0;
        int min = words.length;
        ArrayList<Integer> ai = new ArrayList<>();
        ArrayList<Integer> aj = new ArrayList<>();
        for (; i < words.length; i++) {
            if (words[i].equals(word1))
                ai.add(i);
        }
        for (; j < words.length; j++) {
            if (words[j].equals(word2))
                aj.add(j);
        }

        for (int k = 0; k < ai.size(); k++) {
            for (int l = 0; l < aj.size(); l++) {
                if (Math.abs((aj.get(l) - ai.get(k))) < min)
                    min = Math.abs((aj.get(l) - ai.get(k)));
            }
        }

        return min;
    }

    public static void main(String[] args) {
        String[] test = {"a", "b", "c", "o", "e"};
        System.out.println(shortestDistance(test, "a", "e"));
    }

}
