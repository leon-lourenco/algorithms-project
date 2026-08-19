package com.algorithms.greedy.coinchange.applied;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CashDispenserTest {

    private final CashDispenser dispenser = new CashDispenser();

    @Test
    void dispensesTheMinimumNoteAndCoinCountForAWithdrawal() {
        // R$237.85: 200 + 20 + 10 + 5 + 2 + 0.50 + 0.25 + 0.10 = 8 notes/coins.
        assertThat(dispenser.dispenseNoteCount(23_785L)).isEqualTo(8);
    }

    @Test
    void zeroWithdrawalNeedsNoNotes() {
        assertThat(dispenser.dispenseNoteCount(0L)).isZero();
    }

    @Test
    void aSingleLargeNoteCoversAnExactMatch() {
        assertThat(dispenser.dispenseNoteCount(20_000L)).isEqualTo(1);
    }

    @Test
    void rejectsANegativeWithdrawal() {
        assertThatThrownBy(() -> dispenser.dispenseNoteCount(-1L)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsAWithdrawalBeyondTheKiosksPerTransactionLimit() {
        assertThatThrownBy(() -> dispenser.dispenseNoteCount((long) Integer.MAX_VALUE + 1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
