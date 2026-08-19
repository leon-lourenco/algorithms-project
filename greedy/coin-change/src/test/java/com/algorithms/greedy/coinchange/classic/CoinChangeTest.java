package com.algorithms.greedy.coinchange.classic;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CoinChangeTest {

    private static final int[] US_COINS = {1, 5, 10, 25};
    private static final int[] NON_CANONICAL = {1, 3, 4};

    @Test
    void greedyMatchesTheDpOptimumOnACanonicalDenominationSet() {
        assertThat(CoinChange.greedyCoinCount(US_COINS, 41)).isEqualTo(CoinChange.minCoinsDP(US_COINS, 41));
        assertThat(CoinChange.greedyCoinCount(US_COINS, 41)).isEqualTo(4); // 25 + 10 + 5 + 1
    }

    @Test
    void greedyIsProvablySuboptimalOnANonCanonicalDenominationSet() {
        // {1, 3, 4} for amount 6: greedy locks in 4 first (4 + 1 + 1 = 3 coins), but 3 + 3 = 2
        // coins is strictly better. This is the textbook counterexample to "greedy coin change
        // is always optimal" — it only holds for canonical denomination systems.
        assertThat(CoinChange.greedyCoinCount(NON_CANONICAL, 6)).isEqualTo(3);
        assertThat(CoinChange.minCoinsDP(NON_CANONICAL, 6)).isEqualTo(2);
    }

    @Test
    void zeroAmountNeedsNoCoins() {
        assertThat(CoinChange.greedyCoinCount(US_COINS, 0)).isZero();
        assertThat(CoinChange.minCoinsDP(US_COINS, 0)).isZero();
    }

    @Test
    void greedyFailsLoudlyWhenTheAmountCannotBeMade() {
        assertThatThrownBy(() -> CoinChange.greedyCoinCount(new int[] {2}, 3))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void dpFailsLoudlyWhenTheAmountCannotBeMade() {
        assertThatThrownBy(() -> CoinChange.minCoinsDP(new int[] {2}, 3))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void rejectsInvalidDenominationsAndAmounts() {
        assertThatThrownBy(() -> CoinChange.greedyCoinCount(null, 10)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> CoinChange.greedyCoinCount(new int[0], 10)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> CoinChange.greedyCoinCount(new int[] {1, 0}, 10)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> CoinChange.greedyCoinCount(US_COINS, -1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> CoinChange.minCoinsDP(null, 10)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> CoinChange.minCoinsDP(new int[] {-5}, 10)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> CoinChange.minCoinsDP(US_COINS, -1)).isInstanceOf(IllegalArgumentException.class);
    }
}
