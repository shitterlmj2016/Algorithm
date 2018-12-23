import edu.princeton.cs.algs4.In;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Stack;

public class CloneTest {
    public static void main(String[] args) {
//        int[] a = {1, 2, 3};
//        int[] b = a;
//        int[] c = a.clone();
//
//        b[1] = 111;
//        c[0] = 123;
//        System.out.println(a[1]);
//        System.out.println(a[0]);
//        //二位数组要分开clone！！！！！！！
//
//        ArrayList<Integer> aa; //= new ArrayList<>();
//        ArrayList as;
//        as = new ArrayList<Integer>();
////        int i = as.get(1);
        Stack<Integer> s1 = new Stack<Integer>();
        s1.push(1);
        s1.push(2);
        s1.push(3);
        Stack<Integer> s2=(Stack<Integer>)s1.clone();
        s2.pop();
        System.out.println(s1.pop());



    }
}
