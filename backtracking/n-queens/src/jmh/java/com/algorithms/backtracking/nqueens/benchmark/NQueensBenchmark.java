package com.algorithms.backtracking.nqueens.benchmark;

import com.algorithms.backtracking.nqueens.classic.NQueens;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

/**
 * Brute force builds and checks every one-queen-per-row assignment: n^n of them. That blows up
 * fast enough (8^8 is already ~16.8 million) that {@code n} is kept deliberately small here,
 * unlike this repo's other benchmarks - the same lesson learned sizing the
 * longest-common-subsequence benchmark's disjoint-alphabet worst case. Backtracking prunes as it
 * places each queen, so its actual search tree is a small fraction of n^n.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
public class NQueensBenchmark {

    @State(Scope.Thread)
    public static class BoardSize {
        @Param({"6", "7", "8"})
        public int n;
    }

    @Benchmark
    public int backtracking(BoardSize state) {
        return NQueens.countSolutions(state.n);
    }

    @Benchmark
    public int bruteForce(BoardSize state) {
        return NQueens.bruteForceCountSolutions(state.n);
    }
}
