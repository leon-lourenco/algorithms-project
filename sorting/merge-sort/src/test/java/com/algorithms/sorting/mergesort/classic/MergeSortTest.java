package com.algorithms.sorting.mergesort.classic;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MergeSortTest {

    @Test
    void sortsAnUnorderedArrayIntoAscendingOrder() {
        Integer[] array = {5, 3, 8, 1, 9, 2};

        MergeSort.sort(array, Comparator.naturalOrder());

        assertThat(array).containsExactly(1, 2, 3, 5, 8, 9);
    }

    @Test
    void alreadySortedArrayStaysSorted() {
        Integer[] array = {1, 2, 3, 4, 5};

        MergeSort.sort(array, Comparator.naturalOrder());

        assertThat(array).containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    void reverseSortedArrayEndsUpAscending() {
        Integer[] array = {5, 4, 3, 2, 1};

        MergeSort.sort(array, Comparator.naturalOrder());

        assertThat(array).containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    void oddSizedArraySortsCorrectly() {
        Integer[] array = {9, 4, 7, 1, 3};

        MergeSort.sort(array, Comparator.naturalOrder());

        assertThat(array).containsExactly(1, 3, 4, 7, 9);
    }

    @Test
    void singleElementArrayStaysUnchanged() {
        Integer[] array = {42};

        MergeSort.sort(array, Comparator.naturalOrder());

        assertThat(array).containsExactly(42);
    }

    @Test
    void emptyArrayStaysEmpty() {
        Integer[] array = {};

        MergeSort.sort(array, Comparator.naturalOrder());

        assertThat(array).isEmpty();
    }

    @Test
    void sortsUsingACustomComparatorForDescendingOrder() {
        Integer[] array = {1, 5, 3, 2, 4};

        MergeSort.sort(array, Comparator.reverseOrder());

        assertThat(array).containsExactly(5, 4, 3, 2, 1);
    }

    @Test
    void isStableElementsTiedOnTheComparatorKeepTheirOriginalRelativeOrder() {
        record Tagged(int value, int originalIndex) {
        }
        Tagged[] array = {
                new Tagged(1, 0),
                new Tagged(2, 1),
                new Tagged(1, 2),
                new Tagged(2, 3),
        };

        MergeSort.sort(array, Comparator.comparingInt(Tagged::value));

        assertThat(array).extracting(Tagged::originalIndex).containsExactly(0, 2, 1, 3);
    }

    @Test
    void rejectsANullArray() {
        assertThatThrownBy(() -> MergeSort.sort(null, Comparator.naturalOrder()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsANullComparator() {
        assertThatThrownBy(() -> MergeSort.sort(new Integer[] {1, 2}, null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
