package com.algorithms.greedy.coinchange.benchmark;

import com.algorithms.greedy.coinchange.classic.CoinChange;
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
 * Both methods agree on the answer here (a canonical denomination set, so greedy is optimal too)
 * — this benchmark isn't about correctness, it's about the cost of getting there. Greedy makes
 * one pass over the (fixed-size) denomination list: O(denominations), independent of the amount.
 * The DP alternative fills a table of size {@code amount + 1}: O(amount * denominations). As the
 * amount grows, DP's cost should grow linearly with it while greedy's stays flat.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
public class CoinChangeBenchmark {

    @State(Scope.Thread)
    public static class AmountInCentavos {
        // BRL notes/coins in centavos: canonical, so greedy and DP always agree on the count.
        static final int[] DENOMINATIONS = {
                20000, 10000, 5000, 2000, 1000, 500, 200, 100, 50, 25, 10, 5, 1,
        };

        @Param({"10000", "500000", "5000000"})
        public int amount;
    }

    @Benchmark
    public int greedy(AmountInCentavos state) {
        return CoinChange.greedyCoinCount(AmountInCentavos.DENOMINATIONS, state.amount);
    }

    @Benchmark
    public int dp(AmountInCentavos state) {
        return CoinChange.minCoinsDP(AmountInCentavos.DENOMINATIONS, state.amount);
    }
}
