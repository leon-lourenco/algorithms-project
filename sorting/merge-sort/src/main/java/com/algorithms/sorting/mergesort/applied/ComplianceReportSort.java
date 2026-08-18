package com.algorithms.sorting.mergesort.applied;

import com.algorithms.sorting.mergesort.classic.MergeSort;

import java.util.Comparator;

/**
 * Orders fraud-flagged transactions by risk score, highest first, for a compliance report that
 * has to be reproducible run over run: the same input must always produce the exact same output
 * ordering, including how transactions tied on risk score are broken. Merge sort's guaranteed
 * O(n log n) worst case protects against a large, duplicate-score-heavy batch degrading
 * unpredictably, and its stability means transactions with the same score keep the order they
 * were originally flagged in — an auditor re-running this report later gets an identical
 * result, not just an equally-valid one.
 */
public final class ComplianceReportSort {

    private static final Comparator<FlaggedTransaction> BY_RISK_SCORE_DESCENDING =
            Comparator.comparingInt(FlaggedTransaction::riskScore).reversed();

    public FlaggedTransaction[] sortByRiskScoreDescending(FlaggedTransaction[] transactions) {
        if (transactions == null) {
            throw new IllegalArgumentException("transactions must not be null");
        }
        FlaggedTransaction[] sorted = transactions.clone();
        MergeSort.sort(sorted, BY_RISK_SCORE_DESCENDING);
        return sorted;
    }
}
