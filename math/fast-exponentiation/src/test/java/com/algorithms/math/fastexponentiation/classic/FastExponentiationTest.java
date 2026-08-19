package com.algorithms.math.fastexponentiation.classic;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FastExponentiationTest {

    private static final Offset<Double> TOLERANCE = Offset.offset(1e-9);

    @Test
    void raisesAnEvenAndOddMixOfExponentBitsCorrectly() {
        // 10 in binary is 1010 - exercises both the "bit set" and "bit unset" branches.
        assertThat(FastExponentiation.power(2, 10)).isCloseTo(1024.0, TOLERANCE);
    }

    @Test
    void anyBaseToTheZerothPowerIsOne() {
        assertThat(FastExponentiation.power(7, 0)).isCloseTo(1.0, TOLERANCE);
    }

    @Test
    void zeroToAPositivePowerIsZero() {
        assertThat(FastExponentiation.power(0, 3)).isCloseTo(0.0, TOLERANCE);
    }

    @Test
    void aNegativeExponentIsTheReciprocal() {
        assertThat(FastExponentiation.power(2, -2)).isCloseTo(0.25, TOLERANCE);
    }

    @Test
    void bruteForceAgreesWithFastExponentiation() {
        assertThat(FastExponentiation.bruteForcePower(2, 10))
                .isCloseTo(FastExponentiation.power(2, 10), TOLERANCE);
        assertThat(FastExponentiation.bruteForcePower(2, -2))
                .isCloseTo(FastExponentiation.power(2, -2), TOLERANCE);
    }

    @Test
    void rejectsZeroRaisedToANegativeExponent() {
        assertThatThrownBy(() -> FastExponentiation.power(0, -1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> FastExponentiation.bruteForcePower(0, -1)).isInstanceOf(IllegalArgumentException.class);
    }
}
