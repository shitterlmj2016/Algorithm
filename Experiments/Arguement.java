public class Arguement {


    public static void change(int a) {
        a = a + 1;
    }


    public static void main(String[] args) {
        int b = 1;
        change(b);
        System.out.println(b);
    }
}