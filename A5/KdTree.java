

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;


public class KdTree {
    private int size;
    private Node root;
    private Point2D result;

    private class Node {


        public Point2D point;
        public Node left;
        public Node right;
        public RectHV rect;


        public Node(Point2D point) {
            this.point = point;
            left = null;
            right = null;

        }


        public String toString() {
            return point.toString();
        }

    }


    public KdTree()                               // construct an empty set of points
    {
        root = null;
        size = 0;
    }

    public boolean isEmpty()                      // is the set empty?
    {
        return size == 0;
    }

    public int size()                         // number of points in the set
    {
        return size();
    }

    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {

        if (p == null || p.x() > 1 || p.x() < 0 || p.y() > 1 || p.y() < 0)
            throw new IllegalArgumentException();
        root = put(root, p, 0, new RectHV(0, 0, 1, 1));
        size++;

    }

    //配合insert使用的递归函数
    private Node put(Node x, Point2D p, int steps, RectHV legacy) {

        if (x == null) {
            Node n = new Node(p);
            RectHV myRect = new RectHV(legacy.xmin(), legacy.ymin(), legacy.xmax(), legacy.ymax());
            n.rect = myRect;
            return n;
        }
        if (steps % 2 == 0) {

            if (p.x() < x.point.x()) {
                RectHV give = new RectHV(x.rect.xmin(), x.rect.ymin(), x.point.x(), x.rect.ymax());
                x.left = put(x.left, p, ++steps, give);
            } else {
                RectHV give = new RectHV(x.point.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax());
                x.right = put(x.right, p, ++steps, give);
                //比x

            }
        } else {


            //比y
            if (p.y() < x.point.y()) {
                RectHV give = new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.point.y());

                x.left = put(x.left, p, ++steps, give);
            } else {

                RectHV give = new RectHV(x.rect.xmin(), x.point.y(), x.rect.xmax(), x.rect.ymax());
                x.right = put(x.right, p, ++steps, give);
            }
        }
        return x;

    }


    public boolean contains(Point2D p)            // does the set contain point p?
    {
        if (p == null || p.x() > 1 || p.x() < 0 || p.y() > 1 || p.y() < 0)
            throw new IllegalArgumentException();
        return get(root, p, 0) != null;
    }


    private Node get(Node x, Point2D p, int steps) {
        if (x == null) return null;
        if (x.point.x() == p.x() && x.point.y() == p.y())
            return x;

        if (steps % 2 == 0) {
            if (p.x() < x.point.x())
                return get(x.left, p, ++steps);
            else
                return get(x.right, p, ++steps);
            //比x
        } else {
            //比y
            if (p.y() < x.point.y())
                return get(x.left, p, ++steps);
            else
                return get(x.right, p, ++steps);
        }

    }


    public void draw()                         // draw all points to standard draw
    {

        if (size == 0)
            return;
        draw(root, 0);
    }

    private void draw(Node n, int steps) {
        if (n == null)
            return;
        int a = steps + 1;
        draw(n.left, a);
        StdDraw.setPenRadius(0.01);
        if (steps % 2 == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            Point2D p1 = new Point2D(n.point.x(), n.rect.ymin());
            Point2D p2 = new Point2D(n.point.x(), n.rect.ymax());
            p1.drawTo(p2);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            Point2D p1 = new Point2D(n.rect.xmin(), n.point.y());
            Point2D p2 = new Point2D(n.rect.xmax(), n.point.y());
            p1.drawTo(p2);

        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.015);
        n.point.draw();
        draw(n.right, a);

    }


    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if (rect == null)
            throw new IllegalArgumentException();

        ArrayList<Point2D> array = new ArrayList();

        RectHV copy = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), rect.ymax());
        search(root, copy, array);

        return array;
    }


    private void search(Node node, RectHV rect, ArrayList<Point2D> array) {
        if (node == null)
            return;


        if (rect.contains(node.point)) {
            array.add(node.point);
        }

        if (node.rect.intersects(rect)) {
            search(node.left, rect, array);
            search(node.right, rect, array);
        }

    }


    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null)
            throw new IllegalArgumentException();

        if (isEmpty())
            return null;

        Point2D copy = new Point2D(p.x(), p.y());

        result = new Point2D(root.point.x(), root.point.y());

        Double distance = Double.POSITIVE_INFINITY;
        min(root, distance, copy);

        return result;
    }

    private void min(Node node, Double distance, Point2D target) {
        if (node == null) return;
        if (node.rect.distanceTo(target) > distance) {
            return;
        }


        if (node.point.distanceTo(target) < distance) {

            result = new Point2D(node.point.x(), node.point.y());
            distance = node.point.distanceTo(target);
        }
        min(node.left, distance, target);

        min(node.right, distance, target);


    }


    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
//        Point2D p1 = new Point2D(0.206107, 0.095492);
//        Point2D p2 = new Point2D(0.975528, 0.654508);
//        Point2D p3 = new Point2D(0.024472, 0.345492);
//        Point2D p4 = new Point2D(0.793893, 0.095492);
//        Point2D p5 = new Point2D(0.793893, 0.904508);
//        Point2D p6 = new Point2D(0.975528, 0.345492);
//        Point2D p7 = new Point2D(0.206107, 0.904508);
//        Point2D p8 = new Point2D(0.500000, 0.000000);
//        Point2D p9 = new Point2D(0.024472, 0.654508);
//        Point2D p10 = new Point2D(0.500000, 1.000000);

        Point2D p1 = new Point2D(0, 0);
        Point2D p2 = new Point2D(0.1, 0.1);
        Point2D p3 = new Point2D(0.2, 0.1);
        Point2D p4 = new Point2D(0.2, 0.2);
        Point2D p5 = new Point2D(0.4, 0.1);
        Point2D p6 = new Point2D(0.6, 0.3);
        Point2D p7 = new Point2D(0.5, 0.8);
        Point2D p8 = new Point2D(0.500000, 0.1);
        Point2D p9 = new Point2D(0.2, 0.7);
        Point2D p10 = new Point2D(0.9, 0.8);


        KdTree k = new KdTree();

        k.insert(p1);
        k.insert(p2);
        k.insert(p3);
        k.insert(p4);
        k.insert(p5);
        k.insert(p6);
        k.insert(p7);
        k.insert(p8);
        k.insert(p9);
        k.insert(p10);
        k.insert(p1);


        System.out.println(k.nearest(new Point2D(1, 1)));

    }
}



