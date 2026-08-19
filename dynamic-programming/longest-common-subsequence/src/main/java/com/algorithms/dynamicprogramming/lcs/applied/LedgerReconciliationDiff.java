package com.algorithms.dynamicprogramming.lcs.applied;

import com.algorithms.dynamicprogramming.lcs.classic.Lcs;

import java.util.List;

/**
 * Aligns two transaction ledgers — an internal ledger and the correspondent bank's statement
 * for the same period — by finding the longest common subsequence of matching transaction
 * references still in their original relative order. Entries in that common subsequence are
 * confirmed reconciled; everything else is a genuine reconciliation break (present on one side,
 * missing from the other), not just an unrelated reordering — LCS only ever *drops* elements to
 * find the shared order, it never treats a reordering as a mismatch the way a strict positional
 * comparison would.
 */
public final class LedgerReconciliationDiff {

    public List<String> reconciledReferences(LedgerEntry[] internalLedger, LedgerEntry[] bankStatement) {
        if (internalLedger == null || bankStatement == null) {
            throw new IllegalArgumentException("internalLedger and bankStatement must not be null");
        }
        return Lcs.longestCommonSubsequence(referencesOf(internalLedger), referencesOf(bankStatement));
    }

    private static String[] referencesOf(LedgerEntry[] entries) {
        String[] references = new String[entries.length];
        for (int i = 0; i < entries.length; i++) {
            references[i] = entries[i].reference();
        }
        return references;
    }
}
