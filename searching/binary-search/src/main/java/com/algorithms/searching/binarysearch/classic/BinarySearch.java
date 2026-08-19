package com.algorithms.searching.binarysearch.classic;

import java.util.Comparator;

/**
 * If the data is already sorted, there's no need to touch every element: compare the target
 * against the middle element, and discard the half that can't contain it. Repeating that halves
 * the remaining search space every step, which is what makes this O(log n) instead of the
 * O(n) this repo's Linear Search module needs when it can't assume any ordering. Implemented
 * iteratively (a shrinking [low, high] window) rather than recursively, so an arbitrarily large
 * array never risks a stack frame per halving.
 */
public final class BinarySearch {

    private BinarySearch() {
    }

    public static <T> int search(T[] sortedArray, T target, Comparator<? super T> comparator) {
        if (sortedArray == null) {
            throw new IllegalArgumentException("sortedArray must not be null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("comparator must not be null");
        }
        int low = 0;
        int high = sortedArray.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = comparator.compare(sortedArray[mid], target);
            if (comparison == 0) {
                return mid;
            } else if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}
