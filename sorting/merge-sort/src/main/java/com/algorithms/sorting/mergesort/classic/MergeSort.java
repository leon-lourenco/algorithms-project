package com.algorithms.sorting.mergesort.classic;

import java.util.Comparator;

/**
 * Divide-and-conquer sort with a guaranteed O(n log n) bound regardless of input order — no
 * adaptivity, no worst-case degeneration risk — at the cost of O(n) auxiliary space for the
 * merge step. Splitting a range in half recursively bottoms out at single elements (trivially
 * sorted); merging two already-sorted halves back together only ever needs to look at the
 * current head of each half, which is what makes the merge step itself linear. Implemented to
 * take from the left half on ties, which is what makes this sort stable: equal elements keep
 * their original relative order.
 */
public final class MergeSort {

    private MergeSort() {
    }

    public static <T> void sort(T[] array, Comparator<? super T> comparator) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator must not be null");
        }
        if (array.length < 2) {
            return;
        }
        Object[] buffer = new Object[array.length];
        sort(array, buffer, 0, array.length - 1, comparator);
    }

    @SuppressWarnings("unchecked")
    private static <T> void sort(T[] array, Object[] buffer, int low, int high, Comparator<? super T> comparator) {
        if (low >= high) {
            return;
        }
        int mid = low + (high - low) / 2;
        sort(array, buffer, low, mid, comparator);
        sort(array, buffer, mid + 1, high, comparator);
        merge(array, (T[]) buffer, low, mid, high, comparator);
    }

    private static <T> void merge(T[] array, T[] buffer, int low, int mid, int high, Comparator<? super T> comparator) {
        System.arraycopy(array, low, buffer, low, high - low + 1);

        int left = low;
        int right = mid + 1;
        int destination = low;

        while (left <= mid && right <= high) {
            if (comparator.compare(buffer[left], buffer[right]) <= 0) {
                array[destination++] = buffer[left++];
            } else {
                array[destination++] = buffer[right++];
            }
        }
        while (left <= mid) {
            array[destination++] = buffer[left++];
        }
        while (right <= high) {
            array[destination++] = buffer[right++];
        }
    }
}
