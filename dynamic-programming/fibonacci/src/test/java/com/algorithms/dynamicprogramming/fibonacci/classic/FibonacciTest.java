package com.algorithms.dynamicprogramming.fibonacci.classic;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FibonacciTest {

    @Test
    void naiveComputesKnownValues() {
        assertThat(Fibonacci.naive(0)).isZero();
        assertThat(Fibonacci.naive(1)).isEqualTo(1);
        assertThat(Fibonacci.naive(10)).isEqualTo(55);
    }

    @Test
    void memoizedMatchesNaiveForTheSameInputs() {
        for (int n = 0; n <= 20; n++) {
            assertThat(Fibonacci.memoized(n)).isEqualTo(Fibonacci.naive(n));
        }
    }

    @Test
    void tabulatedMatchesNaiveForTheSameInputs() {
        for (int n = 0; n <= 20; n++) {
            assertThat(Fibonacci.tabulated(n)).isEqualTo(Fibonacci.naive(n));
        }
    }

    @Test
    void allThreeAgreeAtTheOverflowBoundary() {
        assertThat(Fibonacci.tabulated(90)).isEqualTo(Fibonacci.memoized(90));
        assertThat(Fibonacci.tabulated(90)).isEqualTo(2880067194370816120L);
    }

    @Test
    void rejectsANegativeN() {
        assertThatThrownBy(() -> Fibonacci.naive(-1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Fibonacci.memoized(-1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Fibonacci.tabulated(-1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsAnNThatWouldOverflowALong() {
        assertThatThrownBy(() -> Fibonacci.naive(91)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Fibonacci.memoized(91)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Fibonacci.tabulated(91)).isInstanceOf(IllegalArgumentException.class);
    }
}
