package com.algorithms.backtracking.nqueens.classic;

import java.util.ArrayList;
import java.util.List;

/**
 * Placing N queens on an N x N chessboard so that no two attack each other - no shared row,
 * column, or diagonal. Trying every possible one-queen-per-row assignment and checking the whole
 * board's validity only once all N queens are placed is O(n^n): every combination gets built in
 * full before it's ever checked. Backtracking checks as it goes instead - placing one queen per
 * row, and only ever trying a column for the *next* row if it doesn't conflict with any queen
 * already placed. A conflict prunes that entire remaining subtree immediately, before any of the
 * wasted placements underneath it are ever built. Each returned solution is an {@code int[]}
 * where the index is the row and the value is that row's queen's column.
 */
public final class NQueens {

    private NQueens() {
    }

    public static List<int[]> solve(int n) {
        validate(n);
        List<int[]> solutions = new ArrayList<>();
        backtrack(new int[n], 0, solutions);
        return solutions;
    }

    public static int countSolutions(int n) {
        return solve(n).size();
    }

    /** Builds every one-queen-per-row assignment before checking validity - O(n^n), the brute force backtracking's pruning replaces. */
    public static int bruteForceCountSolutions(int n) {
        validate(n);
        return bruteForceCount(new int[n], 0, n);
    }

    private static void backtrack(int[] columns, int row, List<int[]> solutions) {
        if (row == columns.length) {
            solutions.add(columns.clone());
            return;
        }
        for (int col = 0; col < columns.length; col++) {
            if (isSafe(columns, row, col)) {
                columns[row] = col;
                backtrack(columns, row + 1, solutions);
            }
        }
    }

    private static boolean isSafe(int[] columns, int row, int col) {
        for (int previousRow = 0; previousRow < row; previousRow++) {
            int previousCol = columns[previousRow];
            if (previousCol == col || Math.abs(previousCol - col) == Math.abs(previousRow - row)) {
                return false;
            }
        }
        return true;
    }

    private static int bruteForceCount(int[] columns, int row, int n) {
        if (row == n) {
            return isValidBoard(columns) ? 1 : 0;
        }
        int count = 0;
        for (int col = 0; col < n; col++) {
            columns[row] = col;
            count += bruteForceCount(columns, row + 1, n);
        }
        return count;
    }

    private static boolean isValidBoard(int[] columns) {
        for (int i = 0; i < columns.length; i++) {
            for (int j = i + 1; j < columns.length; j++) {
                if (columns[i] == columns[j] || Math.abs(columns[i] - columns[j]) == Math.abs(i - j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void validate(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n must be >= 1");
        }
    }
}
