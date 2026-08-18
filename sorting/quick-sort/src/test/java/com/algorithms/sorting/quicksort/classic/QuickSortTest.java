package com.algorithms.sorting.quicksort.classic;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QuickSortTest {

    private static final Random SEEDED = new Random(42);

    @Test
    void sortsAnUnorderedArrayIntoAscendingOrder() {
        Integer[] array = {5, 3, 8, 1, 9, 2};

        QuickSort.sort(array, Comparator.naturalOrder(), SEEDED);

        assertThat(array).containsExactly(1, 2, 3, 5, 8, 9);
    }

    @Test
    void alreadySortedArrayStaysSorted() {
        Integer[] array = {1, 2, 3, 4, 5};

        QuickSort.sort(array, Comparator.naturalOrder(), SEEDED);

        assertThat(array).containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    void reverseSortedArrayEndsUpAscending() {
        Integer[] array = {5, 4, 3, 2, 1};

        QuickSort.sort(array, Comparator.naturalOrder(), SEEDED);

        assertThat(array).containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    void duplicatesAreHandledCorrectly() {
        Integer[] array = {3, 1, 3, 2, 1};

        QuickSort.sort(array, Comparator.naturalOrder(), SEEDED);

        assertThat(array).containsExactly(1, 1, 2, 3, 3);
    }

    @Test
    void singleElementArrayStaysUnchanged() {
        Integer[] array = {42};

        QuickSort.sort(array, Comparator.naturalOrder(), SEEDED);

        assertThat(array).containsExactly(42);
    }

    @Test
    void emptyArrayStaysEmpty() {
        Integer[] array = {};

        QuickSort.sort(array, Comparator.naturalOrder(), SEEDED);

        assertThat(array).isEmpty();
    }

    @Test
    void sortsUsingACustomComparatorForDescendingOrder() {
        Integer[] array = {1, 5, 3, 2, 4};

        QuickSort.sort(array, Comparator.reverseOrder(), SEEDED);

        assertThat(array).containsExactly(5, 4, 3, 2, 1);
    }

    @Test
    void publicSortMethodWorksWithoutAnExplicitRandom() {
        Integer[] array = {4, 2, 7, 1};

        QuickSort.sort(array, Comparator.naturalOrder());

        assertThat(array).containsExactly(1, 2, 4, 7);
    }

    @Test
    void rejectsANullArray() {
        assertThatThrownBy(() -> QuickSort.sort(null, Comparator.naturalOrder()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsANullComparator() {
        assertThatThrownBy(() -> QuickSort.sort(new Integer[] {1, 2}, null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
