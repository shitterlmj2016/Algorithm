public class CloneTest {
    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        int[] b = a;
        int[] c = a.clone();

        b[1] = 111;
        c[0] = 123;
        System.out.println(a[1]);
        System.out.println(a[0]);
    }
}
