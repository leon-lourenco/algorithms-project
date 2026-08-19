package com.algorithms.greedy.coinchange.applied;

import com.algorithms.greedy.coinchange.classic.CoinChange;

import java.util.Arrays;

/**
 * A legacy bank's self-service withdrawal kiosk, deciding how many of each banknote/coin to
 * dispense for a withdrawal. Real currency denomination sets — Brazilian real notes and coins
 * included — are canonical: greedy (always dispense the largest denomination that still fits)
 * gives the true minimum note/coin count, which is exactly what a kiosk with limited cassette
 * capacity wants. Amounts are handled in centavos ({@code long}) to avoid floating-point error
 * on money.
 */
public final class CashDispenser {

    /** BRL notes and coins, in centavos: R$200 down to R$0.01. Canonical — greedy is optimal here. */
    public static final int[] BRL_CENTAVOS_DENOMINATIONS = {
            20000, 10000, 5000, 2000, 1000, 500, 200, 100, 50, 25, 10, 5, 1,
    };

    public int dispenseNoteCount(long withdrawalAmountCentavos) {
        if (withdrawalAmountCentavos < 0) {
            throw new IllegalArgumentException("withdrawalAmountCentavos must be >= 0");
        }
        if (withdrawalAmountCentavos > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("withdrawalAmountCentavos exceeds this kiosk's per-transaction limit");
        }
        int[] denominations = Arrays.copyOf(BRL_CENTAVOS_DENOMINATIONS, BRL_CENTAVOS_DENOMINATIONS.length);
        return CoinChange.greedyCoinCount(denominations, (int) withdrawalAmountCentavos);
    }
}
