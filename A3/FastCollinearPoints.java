

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private Point[] points;
    private ArrayList<LineSegment> ls;

    public FastCollinearPoints(Point[] p)     // finds all line segments containing 4 or more points
    {

        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        for (int i = 0; i < p.length; i++) {
            if (p[i] == null) {
                throw new java.lang.IllegalArgumentException();
            }
            //null
            //contains repeated points
            for (int j = i + 1; j < p.length; j++) {
                if (p[j] == null) {
                    throw new java.lang.IllegalArgumentException();
                }

                if (p[i].slopeTo(p[j]) == Double.NEGATIVE_INFINITY)
                    throw new java.lang.IllegalArgumentException();
            }


        }
        points = p.clone();
        ls = new ArrayList<LineSegment>();


        Arrays.sort(points);
        int length = points.length;
        for (int i = 0; i < length; i++) {
            //take turns to be the base point
            Point base = points[i];
            Point[] friends = new Point[length - 1];
            for (int j = 0; j < length; j++)

            //Create new matrix
            {
                if (j < i)
                    friends[j] = points[j];
                if (j > i)
                    friends[j - 1] = points[j];
            }

            Arrays.sort(friends, base.slopeOrder());

            int start = 0;
            int end = 1;

            while (start < length - 3) {
                while (end < length - 1 && base.slopeTo(friends[start]) == base.slopeTo(friends[end]))
                    end++;

                if (end - start >= 3) {

                    Point[] temp = new Point[end - start];

                    for (int k = start; k < end; k++) {
                        //test

                        temp[k - start] = friends[k];

                    }

                    if (isMin(base, temp)) {
                        ls.add(new LineSegment(base, getHigh(temp)));
                    }


                }

                start = end;
                end++;


            }


        }
    }

    public int numberOfSegments()        // the number of line segments

    {
        return ls.size();
    }

    public LineSegment[] segments() {


        LineSegment[]temp = new LineSegment[ls.size()];
        for (int i = 0; i <ls.size() ; i++) {
            temp[i]=ls.get(i);
        }
        return temp;
    }                // the line segments

    public static void main(String[] args) {
        Point p0 = new Point(0, 0);
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);
        Point p3 = new Point(3, 3);

        Point p4 = new Point(4, 0);
        Point p5 = new Point(0, 1);
        Point p6 = new Point(0, 2);
        Point p7 = new Point(0, 3);
        Point p8 = new Point(2, 0);
        Point p9 = new Point(3, 0);

        Point[] parray = {p1, p2, p7, p0, p4, p5, p3, p6, p8, p9};

        FastCollinearPoints fp = new FastCollinearPoints(parray);
        System.out.println(fp.numberOfSegments());
        LineSegment[] ls = fp.segments();
        for (int i = 0; i < ls.length; i++) {
            System.out.println(ls[i]);

        }


    }


    private boolean isMin(Point base, Point[] friends) {
        Point[] clone = friends.clone();
        Arrays.sort(clone);
        //Smaller than the smallest
        return base.compareTo(clone[0]) < 0;
    }

    private Point getHigh(Point[] friends) {
        Point[] clone = friends.clone();
        Arrays.sort(clone);
        return clone[clone.length - 1];
    }
}