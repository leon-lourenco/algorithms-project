package com.algorithms.searching.linearsearch.classic;

import java.util.function.Predicate;

/**
 * The baseline: walk the array from the front and test every element against a predicate until
 * one matches, or the array is exhausted. No assumption about the data's order — which is
 * exactly the trade-off: it works on genuinely unsorted data where a faster search couldn't, at
 * the cost of O(n) worst case regardless of where (or whether) a match exists.
 */
public final class LinearSearch {

    private LinearSearch() {
    }

    public static <T> int indexOf(T[] array, Predicate<? super T> matcher) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }
        if (matcher == null) {
            throw new IllegalArgumentException("matcher must not be null");
        }
        for (int i = 0; i < array.length; i++) {
            if (matcher.test(array[i])) {
                return i;
            }
        }
        return -1;
    }
}
