import edu.princeton.cs.algs4.Merge;

public class MergeSort {
    public static void merge(int[] input, int[] temp, int low, int mid, int high) {
        for (int i = low; i <= high; i++) {
            temp[i] = input[i];
        }

        int i = low;
        int j = mid + 1;

        for (int k = low; k <= high; k++) {
            if (j > high) input[k] = temp[i++];
            else if (i > mid) input[k] = temp[j++];
            else if (temp[i] <= temp[j]) input[k] = temp[i++];
            else input[k] = temp[j++];
        }
    }


    public static void sort(int[] input) {
        int[] output = new int[input.length];
        sort(input, output, 0, input.length - 1);
    }

    public static void sort(int[] input, int[] temp, int low, int high) {
        if (high<=low) //不可再分
            //优化，low-high<7 use insertion sort! then return
            return;
        int mid = low + (high - low) / 2;
        sort(input, temp, low, mid);
        sort(input, temp, mid + 1, high);

        //优化2 ： 看看input[low] 是不是已经小于input[mid+1] 是则直接返回
        merge(input, temp, low, mid, high);//第一轮现拍

        //递归核心
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 2, 4, 6, 8};
        MergeSort.sort(a);

        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }
}
