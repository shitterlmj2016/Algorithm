
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.ArrayList;


public class SAP {
    private Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException();
        this.G = new Digraph(G);

    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        checkLegal(v);
        checkLegal(w);


        int[] info = getInfo(v, w);
        if (info[0] == -1)
            return -1;
        else
            return info[1];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        checkLegal(v);
        checkLegal(w);

        int[] info = getInfo(v, w);
        return info[0];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {

        if (v == null || w == null)
            throw new IllegalArgumentException();
        checkLegal(v);
        checkLegal(w);


        int[] info = getInfo(v, w);
        if (info[0] == -1)
            return -1;
        else
            return info[1];

    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException();
        checkLegal(v);
        checkLegal(w);

        int[] info = getInfo(v, w);
        return info[0];
    }

    // do unit testing of this class
    public static void main(String[] args) {
//        ArrayList<Integer> a = new ArrayList<>();
//        ArrayList<Integer> b = new ArrayList<>();
//        a.add(1);
//        a.add(2);
//        a.add(3);
//        a.add(4);
//        b.add(2);
//        a.retainAll(b);
////        System.out.println(b);

        Digraph g = new Digraph(6);
        g.addEdge(1, 0);
        g.addEdge(2, 1);
        g.addEdge(3, 1);
        g.addEdge(4, 2);

        SAP s = new SAP(g);

        ArrayList<Integer> l1= new ArrayList<Integer>();
        l1.add(4);
        l1.add(2);

        ArrayList<Integer> l2= new ArrayList<Integer>();
        l2.add(3);
        System.out.println(s.ancestor(l1, null));



    }

    private int[] getInfo(Iterable<Integer> v, Iterable<Integer> w) {
        int[] result = new int[2];
        int length = G.V();
        BreadthFirstDirectedPaths b1 = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths b2 = new BreadthFirstDirectedPaths(G, w);

        int ancestor = -1;
        int min = length;

        for (int i = 0; i < length; i++) {
            if (b1.hasPathTo(i) && b2.hasPathTo(i)) {
                if ((b1.distTo(i) + b2.distTo(i)) <= min) {
                    min = b1.distTo(i) + b2.distTo(i);
                    ancestor = i;
                }
            }
        }

        result[0] = ancestor;
        result[1] = min;
        return result;
    }

    private int[] getInfo(int v, int w) {
        int[] result = new int[2];
        int length = G.V();
        BreadthFirstDirectedPaths b1 = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths b2 = new BreadthFirstDirectedPaths(G, w);

        int ancestor = -1;
        int min = length;

        for (int i = 0; i < length; i++) {
            if (b1.hasPathTo(i) && b2.hasPathTo(i)) {
                if ((b1.distTo(i) + b2.distTo(i)) <= min) {
                    min = b1.distTo(i) + b2.distTo(i);
                    ancestor = i;
                }
            }
        }

        result[0] = ancestor;
        result[1] = min;
        return result;
    }


    private void checkLegal(int v) {
        int length = G.V();
        if (v < 0 || v >= length)
            throw new IllegalArgumentException();
    }

    private void checkLegal(Iterable<Integer> v) {
        for (Object i : v
        ) {
            if (i==null||(int)i < 0 || (int)i >= G.V())
                throw new IllegalArgumentException();
        }

    }

}
