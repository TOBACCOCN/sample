package com.example.sample.base;

public class IntArraySort {

    private static void swap(int[] array, int i, int j) {
        // array[i] = array[i] + array[j];
        // array[j] = array[i] - array[j];
        // array[i] = array[i] - array[j];
        array[i] = array[i] ^ array[j];
        array[j] = array[i] ^ array[j];
        array[i] = array[i] ^ array[j];
    }

    public static void insertSort(int[] array) {
        for (int i = 0; i < array.length; ++i) {
            for (int j = 0; j < i; ++j) {
                if (array[j] < array[j + 1]) {
                    swap(array, j, j + 1);
                }
            }
        }
    }

    public static void selectSort(int[] array) {

    }

    public static void bubbleSort(int[] array) {

    }

    private static void mergeSortInternal(int[] array, int[] temp, int l, int mid, int r) {
        int i = l, j = mid + 1, k = 0;
        while (i <= mid && j <= r) {
            temp[k++] = array[i] < array[j] ? array[i++] : array[j++];
        }
        while (i <= mid) {
            temp[k++] = array[i++];
        }
        while (j <= r) {
            temp[k++] = array[j++];
        }
        for (i = 0; i < k; ++i) {
            array[l + i] = temp[i];
        }
    }

    private static void mergeSort0(int[] array, int[] temp, int l, int r) {
        if (l < r) {
            int mid = (l + r) / 2;
            mergeSort0(array, temp, l, mid);
            mergeSort0(array, temp, mid + 1, r);
            mergeSortInternal(array, temp, l, mid, r);
        }
    }

    public static void mergeSort(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }

        int[] temp = new int[array.length];
        mergeSort0(array, temp, 0, array.length - 1);
    }

    private static int partition(int[] array, int left, int right) {
        int pivot = array[left];
        while (left < right) {
            while (left < right && array[right] >= pivot) {
                right--;
            }
            array[left] = array[right];
            while (left < right && array[left] < pivot) {
                left++;
            }
            array[right] = array[left];
        }
        array[left] = pivot;
        return left;
    }

    private static void quickSort0(int[] array, int left, int right) {
        if (left < right) {
            int pivot = partition(array, left, right);
            quickSort0(array, left, pivot - 1);
            quickSort0(array, pivot + 1, right);
        }
    }

    public static void quickSort(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }

        quickSort0(array, 0, array.length - 1);
    }

}
