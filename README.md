# The Grand Algorithms Project

[![CI](https://github.com/leon-lourenco/algorithms-project/actions/workflows/ci.yml/badge.svg)](https://github.com/leon-lourenco/algorithms-project/actions/workflows/ci.yml)

**Docs site:** [leon-lourenco.github.io/algorithms-project](https://leon-lourenco.github.io/algorithms-project/) — every algorithm with a diagram, both examples, and its coverage report, browsable in English/Português/Español.

**Read this in:** [English](README.md) | [Português](README.pt-BR.md) | [Español](README.es.md)

A modular Java project covering the classic algorithms taught across a university CS
curriculum — sorting, searching, dynamic programming, greedy strategies, string matching, number
theory, and backtracking. One Gradle module per algorithm, each with its own README, a
from-scratch implementation, a second implementation applying that algorithm to a real scenario,
and a JMH microbenchmark that turns the textbook Big-O claim into a measured, reproducible
number. Everything is plain JVM: no hosted demo, no external services, `./gradlew build` and
you're done.

This is a portfolio project by [Leon Lourenço](https://github.com/leon-lourenco), a senior
backend engineer.

## A few real numbers

Every claim below is copied verbatim from an actual local JMH/JaCoCo run — see each module's
own README for the full table and how to reproduce it.

- **[Bubble Sort](sorting/bubble-sort)**, sorting the same 10,000-element array: random order is
  **~32,527x** slower than already-sorted order, on the exact same code. That's the adaptive
  early-exit claim, made measurable.
- **[Quick Sort](sorting/quick-sort)** with a randomized pivot, the same size: random order is
  only **~2.2x** slower than already-sorted — not the 100x-plus a *non-randomized* quicksort
  would show on exactly that input. Proof the randomization actually neutralizes the classic
  worst-case risk.
- **[Merge Sort](sorting/merge-sort)** and **[Heap Sort](sorting/heap-sort)** both stay within
  ~1.3–2x of each other across already-sorted, nearly-sorted, and random input at every size —
  the "guaranteed bound regardless of input order" claim, made measurable.
- **[Binary Search](searching/binary-search)** against **[Linear Search](searching/linear-search)**
  on the same 1,000,000-element array: **~55,353x faster** for the identical "is it there"
  question — the entire value of a sorted-data assumption, made measurable.
- **[Fibonacci](dynamic-programming/fibonacci)** at n=35: naive recursion is **~6,057,544x
  slower** than the tabulated version for the exact same answer — exponential vs. O(1) space,
  made measurable.
- **[N-Queens](backtracking/n-queens)** at 8 queens: pruned backtracking is **~940x faster** than
  brute force, and both agree on the same answer — the famous **92** solutions first published in
  1850.

## Why classic + applied + benchmark

A textbook implementation proves you understand an algorithm's mechanics — the loop invariant,
the recursion, the partition step. It doesn't prove you know *when* to reach for it over the
alternative, and it doesn't prove the textbook Big-O claim actually holds in a real JVM. So every
module carries three things instead of one:

- **classic/** — the algorithm itself, hand-rolled (no relying on `Arrays.sort`/
  `Collections.sort` as a shortcut), with tests that exercise its real edge cases (already
  sorted, reverse sorted, duplicates, the empty/single-element boundary).
- **applied/** — the same algorithm solving a real scenario, chosen by asking: what's the actual
  problem this algorithm solves, and where has that exact problem shown up? The mapping isn't
  fintech-only by default — it's deliberately pulled from wherever in the author's background
  (payments, insurance, telecom, mainframe modernization) the underlying problem is the most
  natural fit.
- **jmh/** — a JMH microbenchmark that measures the operation the module's complexity claim is
  about, usually as a direct A/B across input orderings: adaptive vs. not, guaranteed bound vs.
  average case. The numbers quoted in each README are copied from a real local run, not
  estimated.

## Why Java?

Every module here is written in Java on purpose, not by default — it's the language this
project's author ships in production daily, so implementing these algorithms without an
`Arrays.sort`/`Collections.sort` shortcut is also a fluency demonstration, not just an
algorithms one. That constraint is part of why Java specifically fits: the language *ships* a
mature, highly-tuned sort as a one-line call, so deliberately writing around it is a real
exercise. A language without that temptation built in — C, say — wouldn't pose quite the same
choice.

The other reason is tooling maturity. Every benchmark number in this repo is measured, not
estimated: JMH runs each benchmark through warmup iterations so the JIT has actually compiled
the hot path before anything gets timed, forks a fresh JVM per benchmark to avoid
cross-contamination, and uses blackholes to stop the JIT from optimizing away the very code
being measured. JaCoCo brings the same rigor to coverage — 100% here means every instruction and
branch genuinely ran under test. Building that level of methodological rigor from scratch in C
is its own separate project; on the JVM it's `./gradlew jmh`.

The honest tradeoff: JVM numbers include the JVM. JIT warm-up, garbage collection, and object
header overhead are folded into every microsecond quoted in this repo. This repo doesn't pretend
that layer is invisible; it leans on JMH's methodology specifically to see the algorithmic shape
(adaptive vs. not, O(n log n) vs. O(n²)) *through* the JVM rather than around it.

## The algorithms

Every module below carries the same classic/applied/benchmark implementation, its own README,
and genuine 100% JaCoCo instruction + branch coverage.

| Algorithm | Category | Applied scenario |
|-----------|----------|-------------------|
| [Bubble Sort](sorting/bubble-sort) | Sorting | Legacy mainframe daily ledger correction (legacy bank) |
| [Insertion Sort](sorting/insertion-sort) | Sorting | Call-detail-record batch sort (telecom) |
| [Merge Sort](sorting/merge-sort) | Sorting | Fraud compliance report ordering (fraud platform) |
| [Quick Sort](sorting/quick-sort) | Sorting | Claim reserve percentile sort (insurer) |
| [Heap Sort](sorting/heap-sort) | Sorting | Edge-equipment alarm sort (telecom) |
| [Linear Search](searching/linear-search) | Searching | Call-overage alert finder (telecom) |
| [Binary Search](searching/binary-search) | Searching | PIX-key snapshot lookup (PIX/BACEN) |
| [Fibonacci](dynamic-programming/fibonacci) | Dynamic Programming | Correspondent-bank payment route counting (telecom) |
| [0/1 Knapsack](dynamic-programming/knapsack) | Dynamic Programming | Capex project selection (telecom) |
| [Longest Common Subsequence](dynamic-programming/longest-common-subsequence) | Dynamic Programming | Bank ledger reconciliation diff (legacy bank) |
| [Coin Change](greedy/coin-change) | Greedy | ATM cash-out note dispensing (legacy bank) |
| [Huffman Coding](greedy/huffman-coding) | Greedy | CDR batch compression (telecom) |
| [Knuth-Morris-Pratt](string-matching/knuth-morris-pratt) | String Matching | Watchlist narration scanning (fraud platform) |
| [Euclidean GCD](math/euclidean-gcd) | Math | Split-payment ratio reduction (PIX/BACEN) |
| [Sieve of Eratosthenes](math/sieve-of-eratosthenes) | Math | Dedup cache bucket sizing (fraud platform) |
| [Fast Exponentiation](math/fast-exponentiation) | Math | Actuarial reserve growth projection (insurer) |
| [N-Queens](backtracking/n-queens) | Backtracking | Settlement lane assignment (PIX/BACEN) |

## Structure

Every algorithm module follows the same skeleton:

```
<category>/<algorithm>/
├── build.gradle.kts          # only present when the module needs extra dependencies
├── README.md                 # problem, solution, complexity, both examples, benchmark, coverage
└── src/
    ├── main/java/com/algorithms/<category>/<algorithm>/
    │   ├── classic/           # the from-scratch implementation
    │   └── applied/           # the real-scenario usage
    ├── test/java/...          # mirrors the classic/applied split
    └── jmh/java/com/algorithms/<category>/<algorithm>/benchmark/
        └── ...                # JMH microbenchmark(s) proving the complexity claim empirically
```

## Tech stack

Java 26, Gradle 9.7 (Kotlin DSL, wrapper committed — `./gradlew` works without installing
Gradle), JUnit 5, AssertJ, JaCoCo 0.8.15, JMH 1.37. No Spring, no framework — every module is
plain Java, since the point is the algorithm, not a container.

**JMH wiring note:** the community `me.champeau.jmh` Gradle plugin's last release (0.7.3,
January 2025) is only tested up to Gradle 8.10/Java 21. Rather than fight a stale plugin against
Gradle 9.7/Java 26, each module's `src/jmh/java` is wired directly as a plain Gradle source set
(see the root `build.gradle.kts`) with JMH's own annotation processor generating the benchmark
runner classes — no third-party plugin in the loop.

## Running it

```bash
./gradlew build                                          # compiles every module
./gradlew test                                            # runs every module's tests
./gradlew :sorting:merge-sort:jacocoTestReport             # per-module coverage report (HTML)
./gradlew :sorting:merge-sort:jmh                          # per-module JMH benchmark run
```

No Docker, no database, no network calls — every test and benchmark runs against in-process
code. Coverage and benchmark numbers quoted in each module's README are copied from a real
local run (JDK 26.0.2 on this machine), not estimated.

## Further reading

Books cited throughout this repo's individual module READMEs, gathered here for reference:

- Cormen, Leiserson, Rivest & Stein — *Introduction to Algorithms* (CLRS), 3rd/4th ed. — the
  canonical academic reference; nearly every university algorithms course uses this book.
- Sedgewick & Wayne — *Algorithms*, 4th ed. — Java-oriented and practical, from the Princeton
  course of the same name; the closest fit to this repo's own language and style.
- Skiena — *The Algorithm Design Manual* — strong on "when to use what" and real case studies,
  the same spirit as this repo's own "When not to use it" sections.
- Knuth — *The Art of Computer Programming*, Vol. 3 (Sorting and Searching) — the historical,
  canonical source for this repo's sorting and searching modules.
- Kleinberg & Tardos — *Algorithm Design* — a strong reference specifically for the greedy and
  dynamic-programming design paradigms this repo's later modules cover.

## License

MIT — see [LICENSE](LICENSE).
