
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
    private int size;


    public PointSET()                               // construct an empty set of points
    {
    }

    public boolean isEmpty()                      // is the set empty?
    {return size==0;
    }

    public int size()                         // number of points in the set
    {return size;
    }

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
    }

    public boolean contains(Point2D p)            // does the set contain point p?
    {
        return true;
    }

    public void draw()                         // draw all points to standard draw
    {
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        return null;
    }

    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        return null;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
        Point2D p1 = new Point2D(2,3);

    }
}