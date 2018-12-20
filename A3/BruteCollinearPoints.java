import java.util.Arrays;

public class BruteCollinearPoints {
    private Point[] points;
    private int num;
    private LineSegment[] ls;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.IllegalArgumentException();
            }
            //null
            //contains repeated points
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null) {
                    throw new java.lang.IllegalArgumentException();
                }


                if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY)
                    throw new java.lang.IllegalArgumentException();
            }
        }
        this.points = points.clone();
        Arrays.sort(this.points);

        int count = 0;
        int n = points.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) &&
                                points[i].slopeTo(points[k]) == points[i].slopeTo(points[l]))
                            count++;

                    }
                }
            }
        }
        num=count;

        int num = numberOfSegments();
        ls = new LineSegment[num];
        int countt = 0;
        int nn = points.length;
        for (int i = 0; i < nn; i++) {
            for (int j = i + 1; j < nn; j++) {
                for (int k = j + 1; k < nn; k++) {
                    for (int l = k + 1; l < nn; l++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) &&
                                points[i].slopeTo(points[k]) == points[i].slopeTo(points[l])) {
                            int hi = i;
                            int lo = i;

                            if (points[j].compareTo(points[hi]) > 0) {
                                hi = j;
                            }

                            if (points[j].compareTo(points[lo]) < 0) {
                                lo = j;
                            }

                            if (points[k].compareTo(points[hi]) > 0) {
                                hi = k;
                            }

                            if (points[k].compareTo(points[lo]) < 0) {
                                lo = k;
                            }

                            if (points[l].compareTo(points[hi]) > 0) {
                                hi = l;
                            }

                            if (points[l].compareTo(points[lo]) < 0) {
                                lo = l;
                            }

                            ls[countt] = new LineSegment(points[lo], points[hi]);
                            countt++;
                        }
                    }
                }
            }

        }

    }

    public int numberOfSegments()        // the number of line segments
    {
       return num;
    }

    public LineSegment[] segments()                // the line segments
    {


        return ls;
    }

    public static void main(String[] args) {
        Point p0 = new Point(0, 0);
        Point p1 = new Point(1, 1);
        Point p4 = new Point(4, 4);
        Point p2 = new Point(2, 2);
        Point p3 = new Point(3, 3);
        Point p5 = new Point(1, 2);
        Point p6 = new Point(2, 4);
        Point p7 = new Point(3, 6);
        Point p8 = new Point(4, 8);
        Point p9 = new Point(1, 3);

//        Point p1 = new Point(1, 1);
//        Point p4 = new Point(1, 2);
//        Point p2 = new Point(1, 3);
//        Point p3 = new Point(1, 4);
//        Point p5 = new Point(2, 2);
//        Point p6 = new Point(2, 4);
//        Point p7 = new Point(2, 6);
//        Point p8 = new Point(2, 8);
//        Point p9 = new Point(0, 3);


        Point[] pp = {p2, p6, p4, p3, p5, p9, p7, p1, p8, null};
        BruteCollinearPoints bc = new BruteCollinearPoints(pp);
//        LineSegment[] ls = bc.segments();
//        Arrays.sort(pp);
//        Arrays.sort(pp,p0.slopeOrder());
//        for (int i = 0; i <pp.length ; i++) {
//
//            System.out.println(pp[i].toString());
//        }
//    }


    }
}