package com.algorithms.dynamicprogramming.lcs.applied;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LedgerReconciliationDiffTest {

    @Test
    void aMissingTransactionOnTheBankSideIsExcludedFromTheReconciledSet() {
        LedgerReconciliationDiff diff = new LedgerReconciliationDiff();
        LedgerEntry[] internalLedger = {
                entry("TX1"), entry("TX2"), entry("TX3"), entry("TX4"),
        };
        LedgerEntry[] bankStatement = {
                entry("TX1"), entry("TX3"), entry("TX4"),
        };

        assertThat(diff.reconciledReferences(internalLedger, bankStatement))
                .containsExactly("TX1", "TX3", "TX4");
    }

    @Test
    void identicalLedgersReconcileEverything() {
        LedgerReconciliationDiff diff = new LedgerReconciliationDiff();
        LedgerEntry[] ledger = {entry("TX1"), entry("TX2")};

        assertThat(diff.reconciledReferences(ledger, ledger)).containsExactly("TX1", "TX2");
    }

    @Test
    void noOverlapReconcilesNothing() {
        LedgerReconciliationDiff diff = new LedgerReconciliationDiff();
        LedgerEntry[] internalLedger = {entry("TX1")};
        LedgerEntry[] bankStatement = {entry("TX2")};

        assertThat(diff.reconciledReferences(internalLedger, bankStatement)).isEmpty();
    }

    @Test
    void rejectsNullLedgers() {
        LedgerReconciliationDiff diff = new LedgerReconciliationDiff();

        assertThatThrownBy(() -> diff.reconciledReferences(null, new LedgerEntry[0]))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> diff.reconciledReferences(new LedgerEntry[0], null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static LedgerEntry entry(String reference) {
        return new LedgerEntry(reference, BigDecimal.TEN);
    }
}
