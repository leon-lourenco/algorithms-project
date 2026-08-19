package com.algorithms.math.sieveoferatosthenes.classic;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SieveOfEratosthenesTest {

    @Test
    void findsAllPrimesUpToThirty() {
        assertThat(SieveOfEratosthenes.primesUpTo(30))
                .containsExactly(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
    }

    @Test
    void aLimitBelowTwoHasNoPrimes() {
        assertThat(SieveOfEratosthenes.primesUpTo(0)).isEmpty();
        assertThat(SieveOfEratosthenes.primesUpTo(1)).isEmpty();
    }

    @Test
    void twoIsTheOnlyEvenPrime() {
        assertThat(SieveOfEratosthenes.primesUpTo(2)).containsExactly(2);
    }

    @Test
    void bruteForceAgreesWithTheSieve() {
        assertThat(SieveOfEratosthenes.bruteForcePrimesUpTo(30)).isEqualTo(SieveOfEratosthenes.primesUpTo(30));
        assertThat(SieveOfEratosthenes.bruteForcePrimesUpTo(1)).isEqualTo(SieveOfEratosthenes.primesUpTo(1));
    }

    @Test
    void rejectsANegativeLimit() {
        assertThatThrownBy(() -> SieveOfEratosthenes.primesUpTo(-1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> SieveOfEratosthenes.bruteForcePrimesUpTo(-1)).isInstanceOf(IllegalArgumentException.class);
    }
}
