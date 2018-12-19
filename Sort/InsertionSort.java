import edu.princeton.cs.algs4.Insertion;

public class InsertionSort {
    int[] array;

    public InsertionSort(int[] array) {
        this.array = array;
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (array[j] < array[j-1])
                    exchange(array, j-1, j);//这里错了，是邻位比较
                else continue;
            }
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
        int[] test = {1, 22, 6, 3, 1, 2, 3, 5, 66, 42, 4, 0};
        InsertionSort is=new InsertionSort(test);
        is.pout();
    }
}