package com.algorithms.dynamicprogramming.fibonacci.applied;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaymentRouteCounterTest {

    private static final Map<String, List<String>> NETWORK = Map.of(
            "A", List.of("B", "C"),
            "B", List.of("D"),
            "C", List.of("D"),
            "D", List.of()
    );

    @Test
    void countsBothTwoHopRoutesFromAToD() {
        PaymentRouteCounter counter = new PaymentRouteCounter(NETWORK);

        assertThat(counter.countRoutesMemoized("A", "D", 2)).isEqualTo(2);
    }

    @Test
    void zeroHopsOnlyCountsWhenSourceEqualsDestination() {
        PaymentRouteCounter counter = new PaymentRouteCounter(NETWORK);

        assertThat(counter.countRoutesMemoized("A", "A", 0)).isEqualTo(1);
        assertThat(counter.countRoutesMemoized("A", "D", 0)).isZero();
    }

    @Test
    void noRouteExistsForAnUnreachableHopCount() {
        PaymentRouteCounter counter = new PaymentRouteCounter(NETWORK);

        assertThat(counter.countRoutesMemoized("A", "D", 5)).isZero();
    }

    @Test
    void naiveAndMemoizedAgreeOnTheSameNetwork() {
        PaymentRouteCounter counter = new PaymentRouteCounter(NETWORK);

        assertThat(counter.countRoutesNaive("A", "D", 2))
                .isEqualTo(counter.countRoutesMemoized("A", "D", 2));
    }

    @Test
    void naiveCountsZeroWhenTheHopBudgetLandsOnTheWrongNode() {
        PaymentRouteCounter counter = new PaymentRouteCounter(NETWORK);

        assertThat(counter.countRoutesNaive("A", "D", 1)).isZero();
    }

    @Test
    void handlesACycleInTheNetworkWithoutInfiniteRecursion() {
        Map<String, List<String>> cyclic = Map.of(
                "X", List.of("Y"),
                "Y", List.of("X")
        );
        PaymentRouteCounter counter = new PaymentRouteCounter(cyclic);

        assertThat(counter.countRoutesMemoized("X", "X", 4)).isEqualTo(1);
    }

    @Test
    void rejectsANullNetwork() {
        assertThatThrownBy(() -> new PaymentRouteCounter(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsNullFromOrTo() {
        PaymentRouteCounter counter = new PaymentRouteCounter(NETWORK);

        assertThatThrownBy(() -> counter.countRoutesMemoized(null, "D", 1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> counter.countRoutesMemoized("A", null, 1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsNegativeHops() {
        PaymentRouteCounter counter = new PaymentRouteCounter(NETWORK);

        assertThatThrownBy(() -> counter.countRoutesMemoized("A", "D", -1)).isInstanceOf(IllegalArgumentException.class);
    }
}
