package com.algorithms.searching.linearsearch.classic;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LinearSearchTest {

    @Test
    void findsTheFirstElementMatchingThePredicate() {
        Integer[] array = {5, 3, 8, 1, 9, 8};

        int index = LinearSearch.indexOf(array, value -> value == 8);

        assertThat(index).isEqualTo(2);
    }

    @Test
    void returnsMinusOneWhenNoElementMatches() {
        Integer[] array = {5, 3, 8};

        int index = LinearSearch.indexOf(array, value -> value == 100);

        assertThat(index).isEqualTo(-1);
    }

    @Test
    void matchAtTheFirstPositionReturnsZero() {
        Integer[] array = {7, 3, 8};

        int index = LinearSearch.indexOf(array, value -> value == 7);

        assertThat(index).isZero();
    }

    @Test
    void matchAtTheLastPositionReturnsTheLastIndex() {
        Integer[] array = {7, 3, 8};

        int index = LinearSearch.indexOf(array, value -> value == 8);

        assertThat(index).isEqualTo(2);
    }

    @Test
    void emptyArrayNeverMatches() {
        Integer[] array = {};

        int index = LinearSearch.indexOf(array, value -> true);

        assertThat(index).isEqualTo(-1);
    }

    @Test
    void rejectsANullArray() {
        assertThatThrownBy(() -> LinearSearch.indexOf(null, value -> true))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsANullMatcher() {
        assertThatThrownBy(() -> LinearSearch.indexOf(new Integer[] {1}, null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
