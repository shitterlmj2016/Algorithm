import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.Stack;

public class SeamCarver {
    private Picture pic;

    public SeamCarver(Picture picture)                // create a seam carver object based on the given picture
    {
        if (picture == null)
            throw new IllegalArgumentException("null pic in the constructor");
        pic = new Picture(picture);
    }

    public Picture picture()                          // current picture
    {
        return pic;
    }

    public int width()                            // width of current picture
    {
        return pic.width();
    }

    public int height()                           // height of current picture
    {
        return pic.height();
    }

    public double energy(int x, int y)               // energy of pixel at column x and row y
    {
        if (x < 0 || x > width() - 1)
            throw new IllegalArgumentException("x wrong range");
        if (y < 0 || y > height() - 1)
            throw new IllegalArgumentException("y wrong range");

        //edge/corner
        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1)
            return 1000;

        int left = pic.getRGB(x - 1, y);
        int lr = (left >> 16) & 0xff;
        int lg = (left >> 8) & 0xff;
        int lb = left & 0xff;

        int right = pic.getRGB(x + 1, y);
        int rr = (right >> 16) & 0xff;
        int rg = (right >> 8) & 0xff;
        int rb = right & 0xff;

        double dx = Math.pow(rr - lr, 2) + Math.pow(rg - lg, 2) + Math.pow(rb - lb, 2);

        int up = pic.getRGB(x, y - 1);
        int[] upRGB = getRGB(up);
        int ur = upRGB[0];
        int ug = upRGB[1];
        int ub = upRGB[2];


        int down = pic.getRGB(x, y + 1);
        int[] downRGB = getRGB(down);
        int dr = downRGB[0];
        int dg = downRGB[1];
        int db = downRGB[2];

        double dy = Math.pow(ur - dr, 2) + Math.pow(dg - ug, 2) + Math.pow(ub - db, 2);

        return Math.sqrt(dy + dx);
    }

    private int[] getRGB(int color) {
        int[] rgb = new int[3];
        rgb[0] = (color >> 16) & 0xff;
        rgb[1] = (color >> 8) & 0xff;
        rgb[2] = color & 0xff;
        return rgb;
    }


    private double[][] energyMatrix() {
        double[][] energy = new double[height()][width()];
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                energy[i][j] = energy(j, i);
            }
        }
        return energy;
    }


    public int[] findVerticalSeam()                 // sequence of indices for vertical seam
    {

        if (width() == 2)
            return new int[height()];

        if (height() == 1)
            return new int[1];

        double[][] energy = energyMatrix();
        //int[] route = new int[height()];
        int[][] route = new int[height()][width()];


        for (int i = 0; i < width(); i++) {
            route[0][i] = -1;
            route[1][i] = i;
            energy[1][i] += 1000;

        }


//        for (int i = 0; i < height(); i++) {
//            route[i][width() - 1] = width() - 1;
//        }


        //printArray(route);
        //printArray(energy);


        for (int i = 2; i < height(); i++) {
            for (int j = 0; j < width(); j++) {

                double[] ans = getMinV(j, i, energy);

                route[i][j] = (int) ans[0];
                energy[i][j] += ans[1];

            }
        }


        int index = 0;
        double min = energy[height() - 1][0];
        for (int i = 1; i < width(); i++) {
            if (energy[height() - 1][i] < min) {
                min = energy[height() - 1][i];
                index = i;
            }
        }


        Stack<Integer> stack = new Stack<>();
        stack.push(index);

        int level = height() - 1;
        index = route[level][index];
        while (index != -1) {
            stack.push(index);
            level--;
            index = route[level][index];
        }


        int[] array = new int[stack.size()];
        int i = 0;
        while (!stack.isEmpty()) {

            array[i++] = stack.pop();
        }

        return array;
    }


    public int[] findHorizontalSeam()               // sequence of indices for horizontal seam
    {
        SeamCarver s = new SeamCarver(transpose(pic));
        return s.findVerticalSeam();
    }


    public void removeVerticalSeam(int[] seam)   // remove vertical seam from current picture
    {
        checkSeam(seam);
        Picture p = new Picture(width() - 1, height());
        for (int i = 0; i < seam.length; i++) {

            for (int j = 0; j < width() - 1; j++) {

                if (j < seam[i]) {
                    p.setRGB(j, i, pic.getRGB(j, i));
                } else {
                    p.setRGB(j, i, pic.getRGB(j + 1, i));
                }

            }

        }

        pic = p;
    }

    private void checkSeam(int[] seam) {

        if (width() <= 1)
            throw new IllegalArgumentException("Insufficient pixel");

        if (seam == null || seam.length != height())
            throw new IllegalArgumentException("wrong length!");

        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] > width() - 1)
                throw new IllegalArgumentException("wrong range!");
            if (i != 0)
                if (Math.abs(seam[i] - seam[i - 1]) > 1)
                    throw new IllegalArgumentException("wrong diff!");
        }

    }


    public void removeHorizontalSeam(int[] seam)     // remove vertical seam from current picture
    {
        SeamCarver s = new SeamCarver(transpose(pic));
        s.removeVerticalSeam(seam);
        this.pic = transpose(s.picture());
    }


    private Picture transpose(Picture p) {
        Picture pp = new Picture(p.height(), p.width());
        for (int i = 0; i < p.height(); i++) {
            for (int j = 0; j < p.width(); j++) {
                pp.setRGB(i, j, p.getRGB(j, i));
            }
        }

        return pp;
    }


    private void printArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("------");
    }

    private void printArray(double[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }


    private double[] getMinV(int col, int row, double[][] energy) {


        int minIndex = col - 1;
        int maxIndex = col + 1;


        if (col == 0) {
            minIndex++;
        }
        if (col == width() - 1) {
            maxIndex--;
        }

        int index = minIndex;
        double min = energy[row - 1][minIndex];

        for (int i = minIndex; i <= maxIndex; i++) {
            if (energy[row - 1][i] < min) {
                min = energy[row - 1][i];
                index = i;
            }
        }

        double[] re = {(double) index, min};
        return re;

    }

    public static void main(String[] args) {
        Picture p = new Picture("10x12.png");
//        System.out.println(p.width());
//        System.out.println(p.height());
        SeamCarver s = new SeamCarver(p);
        // System.out.println(p.get(1,3));
        int[] a = {9, 8, 9, 10, 9, 10, 11, 10, 10,10};
        s.removeHorizontalSeam(a);

        // System.arraycopy(a,2,a,1,4);

    }
}