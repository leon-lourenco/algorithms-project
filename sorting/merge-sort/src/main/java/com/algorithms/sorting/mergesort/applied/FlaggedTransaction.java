package com.algorithms.sorting.mergesort.applied;

import java.time.Instant;

public record FlaggedTransaction(String transactionId, int riskScore, Instant flaggedAt) {
}
