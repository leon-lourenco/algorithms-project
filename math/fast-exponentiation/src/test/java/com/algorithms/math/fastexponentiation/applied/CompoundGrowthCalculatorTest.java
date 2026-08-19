package com.algorithms.math.fastexponentiation.applied;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CompoundGrowthCalculatorTest {

    private static final Offset<Double> TOLERANCE = Offset.offset(1e-9);

    private final CompoundGrowthCalculator calculator = new CompoundGrowthCalculator();

    @Test
    void projectsAReserveOverThreeCompoundingPeriods() {
        // R$1,000 at 5% per period for 3 periods: 1000 * 1.05^3 = 1157.625.
        assertThat(calculator.accumulatedValue(1_000, 0.05, 3)).isCloseTo(1157.625, TOLERANCE);
    }

    @Test
    void zeroPeriodsLeavesThePrincipalUnchanged() {
        assertThat(calculator.accumulatedValue(1_000, 0.05, 0)).isCloseTo(1000.0, TOLERANCE);
    }

    @Test
    void zeroPrincipalStaysZero() {
        assertThat(calculator.accumulatedValue(0, 0.05, 10)).isCloseTo(0.0, TOLERANCE);
    }

    @Test
    void rejectsANegativePrincipal() {
        assertThatThrownBy(() -> calculator.accumulatedValue(-1, 0.05, 3)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsAPeriodicRateOfMinusOneHundredPercentOrWorse() {
        assertThatThrownBy(() -> calculator.accumulatedValue(1_000, -1.0, 3)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> calculator.accumulatedValue(1_000, -1.5, 3)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsANegativePeriodCount() {
        assertThatThrownBy(() -> calculator.accumulatedValue(1_000, 0.05, -1)).isInstanceOf(IllegalArgumentException.class);
    }
}
