public class SelectionSort {
    int[] array;

    public SelectionSort(int[] array) {
        this.array = array;
    }

    public int[] sort() {
        int size = array.length;

        for (int i = 0; i < size; i++) {
            int min = i;
            for (int j = i; j < size; j++) {
                if (array[j] < array[min]) {
                    min = j;

                }
            }

            int temp = array[i];
            array[i] = array[min];
            array[min] = temp;
        }
        return array;
    }

    public static void main(String[] args) {
        int[] a = {19, 2, 4, 6, 3, 4, 5, 1, 0, 9};
        SelectionSort ss = new SelectionSort(a);
        int[] b = ss.sort();
        System.out.println();
    }
}

