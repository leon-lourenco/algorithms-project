# Coin Change

**Category:** Greedy

## The problem

Making change for an amount using the fewest coins/notes from a given set of denominations.
Trying every combination to find the true minimum is exponential. Greedy offers a much cheaper
shortcut — but the catch is it isn't always right, and knowing exactly when it stops being right
is the actual point of this module.

## The solution

Greedy: sort the denominations, then repeatedly take as many of the largest denomination as still
fit, moving to the next-smaller one only once the current one no longer fits. One pass over a
fixed-size denomination list — O(denominations), completely independent of the amount.

That's the true minimum coin count *only* for a **canonical** denomination system, where no
combination of smaller coins ever beats a larger one greedy would have picked. Real currency
systems — including the Brazilian real's notes and coins — are canonical, which is exactly why
greedy is what every ATM actually runs. But not every denomination set is canonical, and for one
that isn't, greedy can lock in a large coin early that a smaller combination would have avoided,
landing on a valid answer that isn't the best one. [`classic/CoinChange.minCoinsDP`](src/main/java/com/algorithms/greedy/coinchange/classic/CoinChange.java)
solves the same problem with dynamic programming — O(amount × denominations), slower, but correct
for *any* positive denomination set — specifically so the gap between the two can be shown, not
just claimed.

## Classic example

[`classic/CoinChange`](src/main/java/com/algorithms/greedy/coinchange/classic/CoinChange.java)
implements both `greedyCoinCount` and `minCoinsDP` side by side.
[`CoinChangeTest`](src/test/java/com/algorithms/greedy/coinchange/classic/CoinChangeTest.java)
proves the textbook counterexample directly: with denominations `{1, 3, 4}` and amount `6`,
greedy takes a `4` first and finishes with `4 + 1 + 1` — **3 coins**. The DP method finds
`3 + 3` — **2 coins**. Same inputs, same problem, two different answers, because one of the two
methods is only correct under an assumption the other doesn't need. The same test file confirms
they *do* agree on a canonical set (US coins, `{1, 5, 10, 25}`), and both methods fail loudly
(rather than silently under-counting) when an amount genuinely can't be made from the given coins.

## Applied example: bank ATM cash-out kiosk

[`applied/CashDispenser`](src/main/java/com/algorithms/greedy/coinchange/applied/CashDispenser.java)
models a legacy bank's self-service withdrawal kiosk deciding how many of each banknote/coin to
dispense. Brazilian real denominations (`R$200` down to `1` centavo) are canonical, so greedy
gives the true minimum note/coin count here — precisely what a kiosk with limited cassette
capacity per denomination wants. Amounts are handled in centavos as a `long` specifically to keep
money arithmetic exact and avoid floating-point rounding.
[`CashDispenserTest`](src/test/java/com/algorithms/greedy/coinchange/applied/CashDispenserTest.java)
covers a realistic mixed withdrawal, an exact single-note match, a zero withdrawal, and the
guard rails (negative amounts, amounts past the kiosk's per-transaction limit).

## Benchmark

```bash
./gradlew :greedy:coin-change:jmh
```

Real run on this machine (JMH 1.37, JDK 26.0.2, 2 warmup + 3 measurement iterations, 1 fork).
Both methods run against the same canonical BRL-centavos denomination set, so they always agree
on the answer — this measures the cost of getting there, not correctness:

| Cost | amount=10,000 | amount=500,000 | amount=5,000,000 |
|---|---:|---:|---:|
| greedy | 0.164 µs | 0.158 µs | 0.159 µs |
| DP | 198.98 µs | 14,734.68 µs | 149,802.96 µs |

Greedy stays flat across three orders of magnitude of amount, exactly as O(denominations)
predicts — it's doing the same fixed 13-denomination pass regardless of how large the amount is.
DP tracks its O(amount) prediction cleanly at the cleaner end of the range: going from
amount=500,000 to amount=5,000,000 is a 10x increase in amount, and DP's measured cost grew
**10.16x** — a close match. (The smaller step, 10,000 → 500,000, carries much wider error bars in
this run — likely JIT warmup noise at that end of the range — so the cleaner 10x confirmation
at the larger sizes is the one worth trusting.) At amount=5,000,000, DP is **~942,157x** slower
than greedy for an answer greedy already had.

## When not to use it

- The denomination set isn't confirmed canonical (arbitrary/custom denominations, loyalty-point
  tiers, non-decimal currencies)? Greedy's speed isn't worth an answer that might be wrong — use
  `minCoinsDP`, or prove canonicity first.
- Need to know not just the count but *which* coins were used? This module's DP path would need
  the same backtracking-through-the-table technique as [Knapsack](../../dynamic-programming/knapsack)
  to recover the actual selection, not just its size.
- Amount is very large and the denomination set is confirmed canonical? Greedy is already the
  right and fast choice — this is precisely the case it's built for.

## Test coverage

100% instruction coverage, 100% branch coverage (JaCoCo). Reproduce it yourself:

```bash
./gradlew :greedy:coin-change:jacocoTestReport
```

Report at `greedy/coin-change/build/reports/jacoco/test/html/index.html`.

## Further reading

- Cormen, Leiserson, Rivest & Stein — *Introduction to Algorithms* (CLRS), 3rd/4th ed.,
  Chapter 16, "Greedy Algorithms" — covers exactly this gap between greedy's simplicity and its
  conditional correctness.
- Kleinberg & Tardos — *Algorithm Design* — Chapter 4 develops the general conditions under which
  a greedy choice is provably optimal (the "greedy stays ahead" and exchange-argument styles of
  proof), the same kind of reasoning that separates a canonical denomination set from one where
  greedy just happens to fail.
