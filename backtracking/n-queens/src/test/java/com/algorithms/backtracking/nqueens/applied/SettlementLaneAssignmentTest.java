package com.algorithms.backtracking.nqueens.applied;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SettlementLaneAssignmentTest {

    private final SettlementLaneAssignment assignment = new SettlementLaneAssignment();

    @Test
    void findsAllConflictFreeLaneAssignmentsForFourJobs() {
        assertThat(assignment.nonConflictingAssignments(4)).hasSize(2);
    }

    @Test
    void reportsNoConflictFreeAssignmentExistsForTwoJobs() {
        assertThat(assignment.hasNonConflictingAssignment(2)).isFalse();
    }

    @Test
    void reportsAConflictFreeAssignmentExistsForFourJobs() {
        assertThat(assignment.hasNonConflictingAssignment(4)).isTrue();
    }
}
