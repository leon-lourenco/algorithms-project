package com.algorithms.sorting.insertionsort.classic;

import java.util.Comparator;

/**
 * Builds up a sorted prefix one element at a time: each next element is shifted left through
 * the already-sorted prefix until it reaches its correct position. Real production sorts (the
 * JDK's own {@code Arrays.sort}/TimSort among them) switch to exactly this algorithm below a
 * small size threshold, since its low constant-factor overhead beats an O(n log n) algorithm's
 * setup cost once n is small enough — the same adaptivity story as this repo's Bubble Sort
 * module, from a different angle: cost here tracks the total number of inversions in the input,
 * not just whether the array happens to already be fully sorted.
 */
public final class InsertionSort {

    private InsertionSort() {
    }

    public static <T> void sort(T[] array, Comparator<? super T> comparator) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator must not be null");
        }
        for (int i = 1; i < array.length; i++) {
            T current = array[i];
            int j = i - 1;
            while (j >= 0 && comparator.compare(array[j], current) > 0) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = current;
        }
    }
}
