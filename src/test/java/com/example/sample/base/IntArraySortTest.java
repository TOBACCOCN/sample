package com.example.sample.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

@Slf4j
public class IntArraySortTest {

    private static int[] generate() {
        int[] array = new int[10000];
        Random random = new Random();
        for (int i = 0; i < array.length; ++i) {
            array[i] = random.nextInt(1000);
            System.out.print(array[i] + " ");
        }
        System.out.println();
        return array;
    }

    @Test
    public void insertSort() {
    }

    @Test
    public void selectSort() {
    }

    @Test
    public void bubbleSort() {
    }

    @Test
    public void mergeSort() {
        int[] array = generate();
        IntArraySort.mergeSort(array);
        Arrays.stream(array).forEach(i -> System.out.print(i + " "));
        System.out.println();
    }

    @Test
    public void quickSort() {
        int[] array = generate();
        IntArraySort.quickSort(array);
        Arrays.stream(array).forEach(i -> System.out.print(i + " "));
        System.out.println();
    }
}