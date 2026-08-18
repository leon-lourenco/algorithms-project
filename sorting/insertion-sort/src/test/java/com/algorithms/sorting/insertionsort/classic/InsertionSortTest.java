package com.algorithms.sorting.insertionsort.classic;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InsertionSortTest {

    @Test
    void sortsAnUnorderedArrayIntoAscendingOrder() {
        Integer[] array = {5, 3, 8, 1, 9, 2};

        InsertionSort.sort(array, Comparator.naturalOrder());

        assertThat(array).containsExactly(1, 2, 3, 5, 8, 9);
    }

    @Test
    void alreadySortedArrayStaysSorted() {
        Integer[] array = {1, 2, 3, 4, 5};

        InsertionSort.sort(array, Comparator.naturalOrder());

        assertThat(array).containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    void reverseSortedArrayEndsUpAscending() {
        Integer[] array = {5, 4, 3, 2, 1};

        InsertionSort.sort(array, Comparator.naturalOrder());

        assertThat(array).containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    void duplicatesAreHandledCorrectly() {
        Integer[] array = {3, 1, 3, 2, 1};

        InsertionSort.sort(array, Comparator.naturalOrder());

        assertThat(array).containsExactly(1, 1, 2, 3, 3);
    }

    @Test
    void singleElementArrayStaysUnchanged() {
        Integer[] array = {42};

        InsertionSort.sort(array, Comparator.naturalOrder());

        assertThat(array).containsExactly(42);
    }

    @Test
    void emptyArrayStaysEmpty() {
        Integer[] array = {};

        InsertionSort.sort(array, Comparator.naturalOrder());

        assertThat(array).isEmpty();
    }

    @Test
    void sortsUsingACustomComparatorForDescendingOrder() {
        Integer[] array = {1, 5, 3, 2, 4};

        InsertionSort.sort(array, Comparator.reverseOrder());

        assertThat(array).containsExactly(5, 4, 3, 2, 1);
    }

    @Test
    void rejectsANullArray() {
        assertThatThrownBy(() -> InsertionSort.sort(null, Comparator.naturalOrder()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsANullComparator() {
        assertThatThrownBy(() -> InsertionSort.sort(new Integer[] {1, 2}, null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
