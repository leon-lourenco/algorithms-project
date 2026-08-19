package com.algorithms.stringmatching.knuthmorrispratt.classic;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class KnuthMorrisPrattTest {

    @Test
    void computesTheClassicTextbookFailureFunction() {
        // The standard CLRS/Sedgewick worked example.
        assertThat(KnuthMorrisPratt.failureFunction("ABABCABAB"))
                .containsExactly(0, 0, 1, 2, 0, 1, 2, 3, 4);
    }

    @Test
    void findsAllOverlappingMatches() {
        assertThat(KnuthMorrisPratt.search("AAAA", "AA")).containsExactly(0, 1, 2);
    }

    @Test
    void findsMultipleNonOverlappingMatches() {
        assertThat(KnuthMorrisPratt.search("ABABABAB", "ABAB")).containsExactly(0, 2, 4);
    }

    @Test
    void returnsNoMatchesWhenThePatternIsAbsent() {
        assertThat(KnuthMorrisPratt.search("HELLOWORLD", "XYZ")).isEmpty();
    }

    @Test
    void aPatternEqualToTheTextMatchesOnceAtZero() {
        assertThat(KnuthMorrisPratt.search("SAME", "SAME")).containsExactly(0);
    }

    @Test
    void aPatternLongerThanTheTextNeverMatches() {
        assertThat(KnuthMorrisPratt.search("AB", "ABCDE")).isEmpty();
    }

    @Test
    void agreesWithBruteForceOnAPathologicalNearMissText() {
        String text = "AAAAAAAAAAAAAAAAAAAAB";
        String pattern = "AAAAB";

        List<Integer> kmp = KnuthMorrisPratt.search(text, pattern);
        List<Integer> bruteForce = KnuthMorrisPratt.bruteForceSearch(text, pattern);

        assertThat(kmp).isEqualTo(bruteForce);
        assertThat(kmp).containsExactly(text.length() - pattern.length());
    }

    @Test
    void rejectsNullText() {
        assertThatThrownBy(() -> KnuthMorrisPratt.search(null, "A")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> KnuthMorrisPratt.bruteForceSearch(null, "A")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsNullOrEmptyPattern() {
        assertThatThrownBy(() -> KnuthMorrisPratt.search("TEXT", null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> KnuthMorrisPratt.search("TEXT", "")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> KnuthMorrisPratt.failureFunction(null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> KnuthMorrisPratt.failureFunction("")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> KnuthMorrisPratt.bruteForceSearch("TEXT", "")).isInstanceOf(IllegalArgumentException.class);
    }
}
