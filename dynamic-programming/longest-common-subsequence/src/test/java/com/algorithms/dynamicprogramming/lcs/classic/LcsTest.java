package com.algorithms.dynamicprogramming.lcs.classic;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LcsTest {

    @Test
    void findsTheExactSubsequenceForAnUnambiguousCase() {
        Character[] a = {'X', 'A', 'X', 'B', 'X', 'C'};
        Character[] b = {'A', 'B', 'C'};

        assertThat(Lcs.longestCommonSubsequence(a, b)).containsExactly('A', 'B', 'C');
    }

    @Test
    void knownTextbookExampleHasLcsLengthFour() {
        Character[] a = {'A', 'B', 'C', 'B', 'D', 'A', 'B'};
        Character[] b = {'B', 'D', 'C', 'A', 'B', 'A'};

        assertThat(Lcs.length(a, b)).isEqualTo(4);
    }

    @Test
    void noCommonElementsProducesAnEmptySubsequence() {
        Character[] a = {'A'};
        Character[] b = {'B'};

        assertThat(Lcs.longestCommonSubsequence(a, b)).isEmpty();
    }

    @Test
    void identicalArraysProduceTheFullSequence() {
        Character[] a = {'A', 'B', 'C'};
        Character[] b = {'A', 'B', 'C'};

        assertThat(Lcs.longestCommonSubsequence(a, b)).containsExactly('A', 'B', 'C');
    }

    @Test
    void eitherArrayEmptyProducesAnEmptySubsequence() {
        Character[] a = {};
        Character[] b = {'A', 'B'};

        assertThat(Lcs.longestCommonSubsequence(a, b)).isEmpty();
    }

    @Test
    void bruteForceAgreesWithTheDpLengthOnTheSameInputs() {
        Character[] a = {'A', 'B', 'C', 'B', 'D', 'A', 'B'};
        Character[] b = {'B', 'D', 'C', 'A', 'B', 'A'};

        assertThat(Lcs.bruteForceLength(a, b)).isEqualTo(Lcs.length(a, b));
    }

    @Test
    void rejectsNullArrays() {
        assertThatThrownBy(() -> Lcs.longestCommonSubsequence(null, new Character[0]))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Lcs.longestCommonSubsequence(new Character[0], null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
