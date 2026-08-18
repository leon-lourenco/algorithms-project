package com.algorithms.sorting.mergesort.applied;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ComplianceReportSortTest {

    private static final Instant WHEN = Instant.parse("2026-08-17T09:00:00Z");

    @Test
    void sortByRiskScoreDescendingOrdersHighestFirst() {
        ComplianceReportSort sorter = new ComplianceReportSort();
        FlaggedTransaction[] transactions = {
                transaction("low", 20),
                transaction("high", 90),
                transaction("mid", 55),
        };

        FlaggedTransaction[] sorted = sorter.sortByRiskScoreDescending(transactions);

        assertThat(sorted).extracting(FlaggedTransaction::transactionId).containsExactly("high", "mid", "low");
    }

    @Test
    void tiesOnRiskScorePreserveOriginalFlaggingOrder() {
        ComplianceReportSort sorter = new ComplianceReportSort();
        FlaggedTransaction[] transactions = {
                transaction("first-flagged", 70),
                transaction("second-flagged", 70),
                transaction("third-flagged", 70),
        };

        FlaggedTransaction[] sorted = sorter.sortByRiskScoreDescending(transactions);

        assertThat(sorted).extracting(FlaggedTransaction::transactionId)
                .containsExactly("first-flagged", "second-flagged", "third-flagged");
    }

    @Test
    void doesNotMutateTheInputArray() {
        ComplianceReportSort sorter = new ComplianceReportSort();
        FlaggedTransaction[] transactions = {transaction("b", 10), transaction("a", 90)};

        sorter.sortByRiskScoreDescending(transactions);

        assertThat(transactions).extracting(FlaggedTransaction::transactionId).containsExactly("b", "a");
    }

    @Test
    void rejectsNullTransactions() {
        ComplianceReportSort sorter = new ComplianceReportSort();

        assertThatThrownBy(() -> sorter.sortByRiskScoreDescending(null)).isInstanceOf(IllegalArgumentException.class);
    }

    private static FlaggedTransaction transaction(String id, int riskScore) {
        return new FlaggedTransaction(id, riskScore, WHEN);
    }
}
