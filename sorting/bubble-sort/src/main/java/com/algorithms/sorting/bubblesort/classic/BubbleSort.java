package com.algorithms.sorting.bubblesort.classic;

import java.util.Comparator;

/**
 * Adaptive bubble sort: repeatedly walks the array swapping out-of-order adjacent pairs, but
 * stops the moment an entire pass makes zero swaps. That single early-exit check is what makes
 * this O(n) on already-sorted (or nearly-sorted) input instead of always paying the full O(n^2)
 * — without it, this module would have nothing left to teach that insertion sort doesn't
 * already teach better.
 */
public final class BubbleSort {

    private BubbleSort() {
    }

    public static <T> void sort(T[] array, Comparator<? super T> comparator) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator must not be null");
        }
        int n = array.length;
        for (int pass = 0; pass < n - 1; pass++) {
            boolean swapped = false;
            for (int i = 0; i < n - 1 - pass; i++) {
                if (comparator.compare(array[i], array[i + 1]) > 0) {
                    swap(array, i, i + 1);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    private static <T> void swap(T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}
