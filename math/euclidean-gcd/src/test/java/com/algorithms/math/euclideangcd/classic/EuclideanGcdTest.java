package com.algorithms.math.euclideangcd.classic;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EuclideanGcdTest {

    @Test
    void computesTheGcdOfTwoOrdinaryNumbers() {
        assertThat(EuclideanGcd.gcd(48, 18)).isEqualTo(6);
    }

    @Test
    void gcdWithZeroReturnsTheOtherNumber() {
        assertThat(EuclideanGcd.gcd(0, 5)).isEqualTo(5);
        assertThat(EuclideanGcd.gcd(5, 0)).isEqualTo(5);
    }

    @Test
    void gcdOfANumberWithItselfIsItself() {
        assertThat(EuclideanGcd.gcd(42, 42)).isEqualTo(42);
    }

    @Test
    void coprimeNumbersHaveGcdOne() {
        assertThat(EuclideanGcd.gcd(17, 13)).isEqualTo(1);
    }

    @Test
    void handlesConsecutiveFibonacciNumbersTheAlgorithmsOwnWorstCase() {
        // Consecutive Fibonacci numbers force the maximum number of steps for a given magnitude.
        assertThat(EuclideanGcd.gcd(89, 55)).isEqualTo(1);
    }

    @Test
    void computesTheLcmOfTwoOrdinaryNumbers() {
        assertThat(EuclideanGcd.lcm(4, 6)).isEqualTo(12);
    }

    @Test
    void lcmWithZeroIsZero() {
        assertThat(EuclideanGcd.lcm(0, 5)).isZero();
        assertThat(EuclideanGcd.lcm(5, 0)).isZero();
    }

    @Test
    void bruteForceAgreesWithEuclidOnEveryCase() {
        assertThat(EuclideanGcd.bruteForceGcd(48, 18)).isEqualTo(EuclideanGcd.gcd(48, 18));
        assertThat(EuclideanGcd.bruteForceGcd(0, 5)).isEqualTo(EuclideanGcd.gcd(0, 5));
        assertThat(EuclideanGcd.bruteForceGcd(5, 0)).isEqualTo(EuclideanGcd.gcd(5, 0));
        assertThat(EuclideanGcd.bruteForceGcd(17, 13)).isEqualTo(EuclideanGcd.gcd(17, 13));
    }

    @Test
    void rejectsNegativeInputs() {
        assertThatThrownBy(() -> EuclideanGcd.gcd(-1, 5)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> EuclideanGcd.gcd(5, -1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> EuclideanGcd.bruteForceGcd(-1, 5)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> EuclideanGcd.lcm(-1, 5)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsBothInputsBeingZero() {
        assertThatThrownBy(() -> EuclideanGcd.gcd(0, 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> EuclideanGcd.bruteForceGcd(0, 0)).isInstanceOf(IllegalArgumentException.class);
    }
}
