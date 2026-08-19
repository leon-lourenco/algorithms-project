package com.algorithms.backtracking.nqueens.applied;

import com.algorithms.backtracking.nqueens.classic.NQueens;

import java.util.List;

/**
 * BACEN's end-of-day settlement window runs N batch reconciliation jobs across N parallel
 * processing lanes, under two constraints: no two jobs share a lane, and no two jobs may be
 * placed such that both their time-slot distance and their lane distance are equal - that
 * diagonal-style pattern is exactly when two jobs would contend for the same shared downstream
 * ledger-lock window. That maps directly onto the N-Queens constraint shape: job = row, assigned
 * lane = column, "two queens attacking along a diagonal" = "two jobs contending for the same
 * lock window".
 */
public final class SettlementLaneAssignment {

    public List<int[]> nonConflictingAssignments(int jobCount) {
        return NQueens.solve(jobCount);
    }

    public boolean hasNonConflictingAssignment(int jobCount) {
        return !NQueens.solve(jobCount).isEmpty();
    }
}
