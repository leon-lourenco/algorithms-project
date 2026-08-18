package com.algorithms.sorting.heapsort.classic;

import java.util.Comparator;

/**
 * In-place, guaranteed O(n log n) sort — the answer to what neither this repo's Quick Sort
 * (average O(n log n), in-place, but a real worst-case risk) nor Merge Sort (guaranteed
 * O(n log n), but O(n) auxiliary space) offers alone. First heapify the array in place into a
 * max-heap (bottom-up, O(n) total), then repeatedly swap the root (the current maximum) with
 * the last not-yet-sorted slot and sift it back down — shrinking the heap by one each time.
 * Every sift-down is O(log n), done n times: O(n log n), unconditionally, with zero auxiliary
 * array.
 */
public final class HeapSort {

    private HeapSort() {
    }

    public static <T> void sort(T[] array, Comparator<? super T> comparator) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator must not be null");
        }
        int n = array.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(array, i, n, comparator);
        }
        for (int end = n - 1; end > 0; end--) {
            swap(array, 0, end);
            siftDown(array, 0, end, comparator);
        }
    }

    private static <T> void siftDown(T[] array, int root, int size, Comparator<? super T> comparator) {
        int current = root;
        while (true) {
            int left = 2 * current + 1;
            int right = 2 * current + 2;
            int largest = current;
            if (left < size && comparator.compare(array[left], array[largest]) > 0) {
                largest = left;
            }
            if (right < size && comparator.compare(array[right], array[largest]) > 0) {
                largest = right;
            }
            if (largest == current) {
                return;
            }
            swap(array, current, largest);
            current = largest;
        }
    }

    private static <T> void swap(T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}
