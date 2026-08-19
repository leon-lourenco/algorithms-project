package com.algorithms.math.euclideangcd.applied;

import com.algorithms.math.euclideangcd.applied.PaymentSplitReducer.SplitRatio;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaymentSplitReducerTest {

    private final PaymentSplitReducer reducer = new PaymentSplitReducer();

    @Test
    void reducesAMarketplaceSplitToItsLowestTerms() {
        assertThat(reducer.reduceToLowestTerms(3_000, 7_000)).isEqualTo(new SplitRatio(3, 7));
    }

    @Test
    void anAlreadyReducedRatioIsUnchanged() {
        assertThat(reducer.reduceToLowestTerms(1, 4)).isEqualTo(new SplitRatio(1, 4));
    }

    @Test
    void rejectsNonPositiveShares() {
        assertThatThrownBy(() -> reducer.reduceToLowestTerms(0, 5)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> reducer.reduceToLowestTerms(5, 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> reducer.reduceToLowestTerms(-1, 5)).isInstanceOf(IllegalArgumentException.class);
    }
}
