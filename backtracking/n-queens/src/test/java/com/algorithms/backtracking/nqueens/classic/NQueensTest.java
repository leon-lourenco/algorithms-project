package com.algorithms.backtracking.nqueens.classic;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NQueensTest {

    @Test
    void aSingleQueenOnAOneByOneBoardIsTheOnlySolution() {
        assertThat(NQueens.countSolutions(1)).isEqualTo(1);
    }

    @Test
    void twoAndThreeByTwoAndThreeBoardsHaveNoSolution() {
        assertThat(NQueens.countSolutions(2)).isZero();
        assertThat(NQueens.countSolutions(3)).isZero();
    }

    @Test
    void fourQueensHasExactlyTwoSolutionsAndEachIsConflictFree() {
        List<int[]> solutions = NQueens.solve(4);

        assertThat(solutions).hasSize(2);
        solutions.forEach(NQueensTest::assertNoTwoQueensAttackEachOther);
    }

    @Test
    void eightQueensHasTheWellKnownNinetyTwoSolutions() {
        // The famous result first published by Franz Nauck in 1850.
        assertThat(NQueens.countSolutions(8)).isEqualTo(92);
    }

    @Test
    void bruteForceAgreesWithBacktrackingOnFiveQueens() {
        assertThat(NQueens.bruteForceCountSolutions(5)).isEqualTo(NQueens.countSolutions(5));
        assertThat(NQueens.countSolutions(5)).isEqualTo(10);
    }

    @Test
    void rejectsABoardSizeBelowOne() {
        assertThatThrownBy(() -> NQueens.solve(0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> NQueens.bruteForceCountSolutions(0)).isInstanceOf(IllegalArgumentException.class);
    }

    private static void assertNoTwoQueensAttackEachOther(int[] columns) {
        for (int row = 0; row < columns.length; row++) {
            for (int otherRow = row + 1; otherRow < columns.length; otherRow++) {
                assertThat(columns[row]).isNotEqualTo(columns[otherRow]);
                assertThat(Math.abs(columns[row] - columns[otherRow])).isNotEqualTo(Math.abs(row - otherRow));
            }
        }
    }
}
