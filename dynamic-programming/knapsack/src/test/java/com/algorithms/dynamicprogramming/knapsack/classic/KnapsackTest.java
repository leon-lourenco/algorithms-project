package com.algorithms.dynamicprogramming.knapsack.classic;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class KnapsackTest {

    @Test
    void picksTheHighestValueCombinationWithinCapacity() {
        int[] weights = {1, 3, 4, 5};
        int[] values = {1, 4, 5, 7};

        KnapsackResult result = Knapsack.solve(weights, values, 7);

        assertThat(result.maxValue()).isEqualTo(9);
        assertThat(result.selected()).containsExactly(false, true, true, false);
    }

    @Test
    void zeroCapacityTakesNothing() {
        KnapsackResult result = Knapsack.solve(new int[] {2, 3}, new int[] {10, 20}, 0);

        assertThat(result.maxValue()).isZero();
        assertThat(result.selected()).containsExactly(false, false);
    }

    @Test
    void noItemsProducesZeroValue() {
        KnapsackResult result = Knapsack.solve(new int[0], new int[0], 10);

        assertThat(result.maxValue()).isZero();
        assertThat(result.selected()).isEmpty();
    }

    @Test
    void aSingleItemThatFitsIsTaken() {
        KnapsackResult result = Knapsack.solve(new int[] {5}, new int[] {10}, 5);

        assertThat(result.maxValue()).isEqualTo(10);
        assertThat(result.selected()).containsExactly(true);
    }

    @Test
    void aSingleItemThatDoesNotFitIsSkipped() {
        KnapsackResult result = Knapsack.solve(new int[] {10}, new int[] {10}, 5);

        assertThat(result.maxValue()).isZero();
        assertThat(result.selected()).containsExactly(false);
    }

    @Test
    void bruteForceAgreesWithTheDpSolutionOnTheSameInputs() {
        int[] weights = {1, 3, 4, 5};
        int[] values = {1, 4, 5, 7};

        int dpValue = Knapsack.solve(weights, values, 7).maxValue();
        int bruteForceValue = Knapsack.bruteForceMaxValue(weights, values, 7);

        assertThat(bruteForceValue).isEqualTo(dpValue);
    }

    @Test
    void rejectsNullWeightsOrValues() {
        assertThatThrownBy(() -> Knapsack.solve(null, new int[0], 1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Knapsack.solve(new int[0], null, 1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsMismatchedArrayLengths() {
        assertThatThrownBy(() -> Knapsack.solve(new int[] {1, 2}, new int[] {1}, 5))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsANegativeCapacity() {
        assertThatThrownBy(() -> Knapsack.solve(new int[] {1}, new int[] {1}, -1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
