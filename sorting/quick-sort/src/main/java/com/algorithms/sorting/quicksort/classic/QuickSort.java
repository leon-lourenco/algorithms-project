package com.algorithms.sorting.quicksort.classic;

import java.util.Comparator;
import java.util.Random;

/**
 * In-place, average-case O(n log n) sort: partition the range around a pivot so everything
 * smaller ends up to its left and everything larger ends up to its right, then recurse on each
 * side. The pivot is picked uniformly at random from the current range before partitioning
 * starts, and swapped into the last position — without that, a fixed pivot choice (e.g. always
 * the last element) degrades to O(n^2) on exactly the inputs this repo's benchmarks already
 * test for the other sorts: already-sorted and reverse-sorted data. Randomizing the pivot
 * doesn't change the worst case's existence, but makes it depend on the random seed instead of
 * the input's own order, which is what makes quicksort safe to run on untrusted input in
 * practice.
 */
public final class QuickSort {

    private QuickSort() {
    }

    public static <T> void sort(T[] array, Comparator<? super T> comparator) {
        sort(array, comparator, new Random());
    }

    static <T> void sort(T[] array, Comparator<? super T> comparator, Random random) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator must not be null");
        }
        quicksort(array, 0, array.length - 1, comparator, random);
    }

    private static <T> void quicksort(T[] array, int low, int high, Comparator<? super T> comparator, Random random) {
        if (low >= high) {
            return;
        }
        int pivotIndex = partition(array, low, high, comparator, random);
        quicksort(array, low, pivotIndex - 1, comparator, random);
        quicksort(array, pivotIndex + 1, high, comparator, random);
    }

    private static <T> int partition(T[] array, int low, int high, Comparator<? super T> comparator, Random random) {
        int randomIndex = low + random.nextInt(high - low + 1);
        swap(array, randomIndex, high);

        T pivot = array[high];
        int boundary = low;
        for (int i = low; i < high; i++) {
            if (comparator.compare(array[i], pivot) < 0) {
                swap(array, i, boundary);
                boundary++;
            }
        }
        swap(array, boundary, high);
        return boundary;
    }

    private static <T> void swap(T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}
