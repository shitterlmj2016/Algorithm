import edu.princeton.cs.algs4.Shell;

public class ShellSort {

    int[] array;

    public ShellSort(int[] array) {
        this.array = array;
        int h = 1;
        int n = array.length;
        while (h < n / 3) {
            h = 3 * h + 1;
        }//h: 1 4 13 ...

        while (h >= 1) {//>=这里要注意
            for (int i = h; i < array.length; i++) {
                for (int j = i; j >= h; j -= h) {//>=这里要注意 保证-时候大于0
                    if (array[j] < array[j - h])
                        exchange(array, j, j - h);
                }
            }
            h = h / 3; // 13,4,1...
        }
    }

    private void exchange(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    private void pout() {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    public static void main(String[] args) {
        int[] a = {19, 2, 4, 6, 3, 4, 5, 1, 0, 9};
        ShellSort ss = new ShellSort(a);
        ss.pout();
    }
}
