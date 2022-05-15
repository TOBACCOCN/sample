package com.example.sample.base;

import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;

/**
 * 对象排序
 *
 * @author TOBACCO
 * @date 2022/5/13
 */
@Slf4j
public class ObjectArraySort {

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
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

    // /**
    //  * 将数组中指定起始索引和结束索引的一段序列归并排序
    //  * 注意：从索引 left 到 mid 的元素必须是排好序的，从索引 mid 到 right 的元素也必须是排好序的
    //  *
    //  * @param array     待排序对象数组
    //  * @param temp      临时数组容器用以盛放排序后的对象
    //  * @param functions 根据数组元素对象的某些字段排序时该字段的 Getter 方法, functions 中脚标小的 function 对应的关键只的排序优先级高
    //  * @param i         当前需要对 functions 中某个 function 对应的字段排序的脚标
    //  * @param indexes   indexes[0] 为左数组中已经放入临时数组中元素的索引偏移量, indexes[1] 为右数组中已经放入临时数组中元素的索引偏移量,
    //  *                  indexes[3] 为临时数组中已排序元素的索引偏移量
    //  */
    // private static <T> void mergeSortInternal0(T[] array, T[] temp, List<Function<T, Integer>> functions, int i, int[] indexes) {
    //     Integer o1filedValue = functions.get(i).apply(array[indexes[0]]);
    //     Integer o2fieldValue = functions.get(i).apply(array[indexes[1]]);
    //     if (o1filedValue < o2fieldValue) {
    //         temp[indexes[2]++] = array[indexes[0]++];
    //     } else if (o1filedValue > o2fieldValue) {
    //         temp[indexes[2]++] = array[indexes[1]++];
    //     } else {
    //         if (i < functions.size() - 1) {
    //             mergeSortInternal0(array, temp, functions, ++i, indexes);
    //         } else {
    //             // 两个数组序列中元素的关键字的值相等时，选择索引较小的那一个元素放到临时数组容器，
    //             // 因为索引较小的那一个比索引较大的那一个的其他关键字的值要小(如果之前有针对该关键字排序)
    //             // 这样就保证了稳定性
    //             temp[indexes[2]++] = indexes[0] < indexes[1] ? array[indexes[0]++] : array[indexes[1]++];
    //         }
    //     }
    // }
    //
    // /**
    //  * 将数组中指定起始索引和结束索引的一段序列归并排序
    //  * 注意：从索引 left 到 mid 的元素必须是排好序的，从索引 mid 到 right 的元素也必须是排好序的
    //  *
    //  * @param array     待排序对象数组
    //  * @param temp      临时数组容器用以盛放排序后的对象
    //  * @param functions 根据数组元素对象的某些字段排序时该字段的 Getter 方法, functions 中脚标小的 function 对应的关键只的排序优先级高
    //  * @param left      待排序数组的起始索引(左索引)
    //  * @param mid       待排序数组从索引 left 开始, 元素保持有序的最大索引, 通常 mid - left = 2^n, 且 mid < right
    //  * @param right     待排序数组的结束索引(右索引)
    //  */
    // private static <T> void mergeSortInternal(T[] array, T[] temp, List<Function<T, Integer>> functions, int left, int mid, int right) {
    //     if (functions == null || functions.size() == 0) {
    //         return;
    //     }
    //
    //     int[] indexes = {left, mid + 1, 0};
    //     while (indexes[0] <= mid && indexes[1] <= right) {
    //         mergeSortInternal0(array, temp, functions, 0, indexes);
    //     }
    //     while (indexes[0] <= mid) {
    //         temp[indexes[2]++] = array[indexes[0]++];
    //     }
    //     while (indexes[1] <= right) {
    //         temp[indexes[2]++] = array[indexes[1]++];
    //     }
    //
    //     // for (int i = 0; i < indexes[2]; ++i) {
    //     //     array[left + i] = temp[i];
    //     // }
    //     if (indexes[2] >= 0) System.arraycopy(temp, 0, array, left, indexes[2]);
    // }
    //
    // /**
    //  * 将数组中指定起始索引和结束索引的一段序列归并排序
    //  *
    //  * @param array     待排序对象数组
    //  * @param temp      临时数组容器用以盛放排序后的对象
    //  * @param functions 根据数组元素对象的某些字段排序时该字段的 Getter 方法, functions 中脚标小的 function 对应的关键只的排序优先级高
    //  * @param left      待排序数组的起始索引(左索引)
    //  * @param right     待排序数组的结束索引(右索引)
    //  */
    // private static <T> void mergeSort0(T[] array, T[] temp, List<Function<T, Integer>> functions, int left, int right) {
    //     if (left < right) {
    //         int mid = (left + right) / 2;
    //         mergeSort0(array, temp, functions, left, mid);
    //         mergeSort0(array, temp, functions, mid + 1, right);
    //         mergeSortInternal(array, temp, functions, left, mid, right);
    //     }
    // }
    //
    // /**
    //  * 归并排序
    //  *
    //  * @param array     待排序对象数组
    //  * @param functions 根据数组元素对象的某些字段排序时该字段的 Getter 方法, functions 中脚标小的 function 对应的关键只的排序优先级高
    //  */
    // public static <T> void mergeSort(T[] array, List<Function<T, Integer>> functions) {
    //     @SuppressWarnings("unchecked")
    //     T[] temp = (T[]) new Object[array.length];
    //     System.arraycopy(array, 0, temp, 0, array.length);
    //     mergeSort0(array, temp, functions, 0, array.length - 1);
    // }

    /**
     * 将数组中指定起始索引和结束索引的一段序列归并排序
     * 注意：从索引 left 到 mid 的元素必须是排好序的，从索引 mid 到 right 的元素也必须是排好序的
     *
     * @param array      待排序对象数组
     * @param temp       临时数组容器用以盛放排序后的对象
     * @param comparator 对象比较器
     * @param indexes    indexes[0] 为左数组中已经放入临时数组中元素的索引偏移量, indexes[1] 为右数组中已经放入临时数组中元素的索引偏移量,
     *                   indexes[3] 为临时数组中已排序元素的索引偏移量
     */
    private static <T> void mergeSortInternal0(T[] array, T[] temp, Comparator<T> comparator, int[] indexes) {
        int compare = comparator.compare(array[indexes[0]], array[indexes[1]]);
        if (compare < 0) {
            temp[indexes[2]++] = array[indexes[0]++];
        } else if (compare > 0) {
            temp[indexes[2]++] = array[indexes[1]++];
        } else {
            // 两个数组序列中元素的关键字的值相等时，选择索引较小的那一个元素放到临时数组容器，
            // 因为索引较小的那一个比索引较大的那一个的其他关键字的值要小(如果之前有针对该关键字排序)
            // 这样就保证了稳定性
            temp[indexes[2]++] = indexes[0] < indexes[1] ? array[indexes[0]++] : array[indexes[1]++];
        }
    }

    /**
     * 将数组中指定起始索引和结束索引的一段序列归并排序
     * 注意：从索引 left 到 mid 的元素必须是排好序的，从索引 mid 到 right 的元素也必须是排好序的
     *
     * @param array      待排序对象数组
     * @param temp       临时数组容器用以盛放排序后的对象
     * @param left       待排序数组的起始索引(左索引)
     * @param mid        待排序数组从索引 left 开始, 元素保持有序的最大索引, 通常 mid - left = 2^n - 1, 且 mid < right
     * @param right      待排序数组的结束索引(右索引)
     * @param comparator 对象比较器
     */
    private static <T> void mergeSortInternal(T[] array, T[] temp, int left, int mid, int right, Comparator<T> comparator) {
        if (comparator == null) {
            return;
        }

        int[] indexes = {left, mid + 1, 0};
        while (indexes[0] <= mid && indexes[1] <= right) {
            mergeSortInternal0(array, temp, comparator, indexes);
        }
        while (indexes[0] <= mid) {
            temp[indexes[2]++] = array[indexes[0]++];
        }
        while (indexes[1] <= right) {
            temp[indexes[2]++] = array[indexes[1]++];
        }

        // for (int i = 0; i < indexes[2]; ++i) {
        //     array[left + i] = temp[i];
        // }
        if (indexes[2] >= 0) System.arraycopy(temp, 0, array, left, indexes[2]);
    }

    /**
     * 将数组中指定起始索引和结束索引的一段序列归并排序
     *
     * @param array      待排序对象数组
     * @param temp       临时数组容器用以盛放排序后的对象
     * @param left       待排序数组的起始索引(左索引)
     * @param right      待排序数组的结束索引(右索引)
     * @param comparator 对象比较器
     */
    private static <T> void mergeSort0(T[] array, T[] temp, int left, int right, Comparator<T> comparator) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort0(array, temp, left, mid, comparator);
            mergeSort0(array, temp, mid + 1, right, comparator);
            mergeSortInternal(array, temp, left, mid, right, comparator);
        }
    }

    /**
     * 归并排序
     *
     * @param array      待排序对象数组
     * @param comparator 对象比较器
     */
    public static <T> void mergeSort(T[] array, Comparator<T> comparator) {
        if (array == null || array.length == 0) {
            return;
        }

        @SuppressWarnings("unchecked")
        T[] temp = (T[]) new Object[array.length];
        System.arraycopy(array, 0, temp, 0, array.length);
        mergeSort0(array, temp, 0, array.length - 1, comparator);
    }

    /**
     * 归并排序
     *
     * @param array 待排序对象数组
     */
    public static <T> void mergeSort(T[] array) {
        mergeSort(array, Comparator.comparingInt(Object::hashCode));
    }

    /**
     * 将数组中比索引为 0 的元素的指定字段值大的其他元素放到数组右边，小的放到左边
     *
     * @param array      待排序数组
     * @param left       待排序数组的起始索引(左索引)
     * @param right      待排序数组的结束索引(右索引)
     * @param comparator 对象比较器
     * @return 数组经过排序后, 最开始索引为 0 的元素存放位置索引
     */
    private static <T> int partition(T[] array, int left, int right, Comparator<T> comparator) {
        T pivot = array[left];
        while (left < right) {
            while (left < right && comparator.compare(pivot, array[right]) <= 0) {
                right--;
            }
            array[left] = array[right];
            while (left < right && comparator.compare(pivot, array[left]) > 0) {
                left++;
            }
            array[right] = array[left];
        }
        array[left] = pivot;
        return left;
    }

    /**
     * 将数组中指定起始索引和结束索引的一段序列快速排序
     *
     * @param array      待排序数组
     * @param left       待排序数组的起始索引(左索引)
     * @param right      待排序数组的结束索引(右索引)
     * @param comparator 对象比较器
     */
    private static <T> void quickSort0(T[] array, int left, int right, Comparator<T> comparator) {
        if (left < right) {
            int pivot = partition(array, left, right, comparator);
            quickSort0(array, left, pivot, comparator);
            quickSort0(array, pivot + 1, right, comparator);
        }
    }

    /**
     * 快速排序
     *
     * @param array      待排序数组
     * @param comparator 对象比较器
     */
    public static <T> void quickSort(T[] array, Comparator<T> comparator) {
        if (array == null || array.length == 0) {
            return;
        }

        quickSort0(array, 0, array.length - 1, comparator);
    }

    /**
     * 快速排序
     *
     * @param array 待排序数组
     */
    public static <T> void quickSort(T[] array) {
        quickSort(array, Comparator.comparingInt(Object::hashCode));
    }

}
