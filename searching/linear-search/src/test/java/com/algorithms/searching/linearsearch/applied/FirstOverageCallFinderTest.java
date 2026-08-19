package com.algorithms.searching.linearsearch.applied;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FirstOverageCallFinderTest {

    @Test
    void findsTheFirstCallExceedingTheAllowance() {
        FirstOverageCallFinder finder = new FirstOverageCallFinder();
        CallRecord[] log = {
                new CallRecord("a", 120),
                new CallRecord("b", 600),
                new CallRecord("c", 900),
        };

        int index = finder.findFirstOverage(log, 300);

        assertThat(index).isEqualTo(1);
    }

    @Test
    void returnsMinusOneWhenNoCallExceedsTheAllowance() {
        FirstOverageCallFinder finder = new FirstOverageCallFinder();
        CallRecord[] log = {new CallRecord("a", 60), new CallRecord("b", 120)};

        int index = finder.findFirstOverage(log, 300);

        assertThat(index).isEqualTo(-1);
    }

    @Test
    void rejectsANullLog() {
        FirstOverageCallFinder finder = new FirstOverageCallFinder();

        assertThatThrownBy(() -> finder.findFirstOverage(null, 300)).isInstanceOf(IllegalArgumentException.class);
    }
}
