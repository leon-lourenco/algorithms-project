package com.algorithms.searching.binarysearch.classic;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BinarySearchTest {

    @Test
    void findsATargetInTheMiddle() {
        Integer[] array = {1, 3, 5, 7, 9};

        int index = BinarySearch.search(array, 5, Comparator.naturalOrder());

        assertThat(index).isEqualTo(2);
    }

    @Test
    void findsATargetAtTheStart() {
        Integer[] array = {1, 3, 5, 7, 9};

        int index = BinarySearch.search(array, 1, Comparator.naturalOrder());

        assertThat(index).isZero();
    }

    @Test
    void findsATargetAtTheEnd() {
        Integer[] array = {1, 3, 5, 7, 9};

        int index = BinarySearch.search(array, 9, Comparator.naturalOrder());

        assertThat(index).isEqualTo(4);
    }

    @Test
    void returnsMinusOneWhenTheTargetIsMissing() {
        Integer[] array = {1, 3, 5, 7, 9};

        int index = BinarySearch.search(array, 4, Comparator.naturalOrder());

        assertThat(index).isEqualTo(-1);
    }

    @Test
    void returnsMinusOneOnAnEmptyArray() {
        Integer[] array = {};

        int index = BinarySearch.search(array, 1, Comparator.naturalOrder());

        assertThat(index).isEqualTo(-1);
    }

    @Test
    void singleElementArrayFindsItsOnlyElement() {
        Integer[] array = {42};

        int index = BinarySearch.search(array, 42, Comparator.naturalOrder());

        assertThat(index).isZero();
    }

    @Test
    void evenSizedArrayFindsEveryElement() {
        Integer[] array = {1, 2, 3, 4};

        for (int expected = 0; expected < array.length; expected++) {
            assertThat(BinarySearch.search(array, array[expected], Comparator.naturalOrder())).isEqualTo(expected);
        }
    }

    @Test
    void rejectsANullArray() {
        assertThatThrownBy(() -> BinarySearch.search(null, 1, Comparator.naturalOrder()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsANullComparator() {
        assertThatThrownBy(() -> BinarySearch.search(new Integer[] {1}, 1, null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
