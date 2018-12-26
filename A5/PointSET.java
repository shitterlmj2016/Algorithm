
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;
import java.util.Arrays;

public class PointSET {

    private SET<Point2D> set;

    public PointSET()                               // construct an empty set of points
    {
        set = new SET<Point2D>();
    }

    public boolean isEmpty()                      // is the set empty?
    {
        return set.size() == 0;
    }

    public int size()                         // number of points in the set
    {
        return set.size();
    }

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {

        if (p == null || p.x() > 1 || p.x() < 0 || p.y() > 1 || p.y() < 0)
            throw new IllegalArgumentException();
        Point2D pt = new Point2D(p.x(), p.y());

        set.add(pt);
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        if (p == null || p.x() > 1 || p.x() < 0 || p.y() > 1 || p.y() < 0)
            throw new IllegalArgumentException();
        return set.contains(p);
    }

    public void draw()                         // draw all points to standard draw
    {
        for (Point2D p : set
        ) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if (rect == null)
            throw new IllegalArgumentException();
        ArrayList<Point2D> al = new ArrayList<Point2D>();
        for (Point2D p : set
        ) {
            if (p.x() >= rect.xmin() && p.x() <= rect.xmax() && p.y() >= rect.ymin() && p.x() <= rect.ymax())
                al.add(p);
        }
        return al;
    }

    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null)
            throw new IllegalArgumentException();

        if (isEmpty())
            return null;


        Point2D[] array = new Point2D[size()];

        int i = 0;

        for (Point2D temp : set
        ) {
            array[i] = temp;
            i++;
        }
        Arrays.sort(array, p.distanceToOrder());

        return array[0];
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
        Point2D p1 = new Point2D(0.1, 0.1);
        Point2D p2 = new Point2D(0.2, 0);
        Point2D p3 = new Point2D(0.4, 0.4);
        Point2D p4 = new Point2D(0.9, 0.1);

        PointSET ps = new PointSET();

        ps.insert(p1);
        ps.insert(p2);
        ps.insert(p3);
        ps.insert(p4);


        RectHV r1 = new RectHV(0, 0, 0.4, 0.4);

        System.out.println(ps.size());
        System.out.println(ps.range(r1));
    }
}