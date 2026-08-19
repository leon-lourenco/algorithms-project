# Linear Search

**Category:** Searching

## The problem

Finding a value in a collection with no assumption about its order — the data might genuinely
be unsorted, or sorting it just to run one lookup would cost more than the lookup itself.
Whatever the reason, there's no shortcut available: without an ordering to exploit, there's no
way to know which half of the data to discard.

## The solution

Walk the collection from the front and test each element until one matches, or the collection is
exhausted. There's nothing clever here on purpose — the entire value of this module is
establishing the honest baseline every faster search (starting with this repo's own
[Binary Search](../binary-search)) has to beat, and *why* it can't be beaten without an
assumption like "the data is sorted."

| Case | Cost | Why |
|---|---|---|
| Best (match at index 0) | O(1) | the very first comparison succeeds |
| Worst (no match, or match at the end) | O(n) | every element has to be examined |
| Average | O(n) | proportional to how far into the collection the match sits |

## Classic example

[`classic/LinearSearch`](src/main/java/com/algorithms/searching/linearsearch/classic/LinearSearch.java)
takes a `Predicate<? super T>` rather than a fixed target value — a deliberate generalization
over "find this exact value": it's what a real linear scan usually needs (find the first element
*matching a condition*, not just equal to a constant), and it's what lets the applied example
below reuse this exact method unchanged.
[`LinearSearchTest`](src/test/java/com/algorithms/searching/linearsearch/classic/LinearSearchTest.java)
covers a match in the middle, no match, a match at the very first position, a match at the very
last position, an empty array, and both null-argument guards.

## Applied example: telecom overage-call finder

[`applied/FirstOverageCallFinder`](src/main/java/com/algorithms/searching/linearsearch/applied/FirstOverageCallFinder.java)
finds the first call in a daily call-detail-record log whose duration exceeds a subscriber's
plan allowance, to trigger a real-time overage alert. The log is ordered by arrival time — calls
stream in from towers in whatever order they happen — not by duration, so there's no
sorted-by-duration view to binary search against, and re-sorting the whole log by duration on
every check would cost more than the linear scan itself. This is the honest case for linear
search: the data genuinely isn't ordered by the field being searched on.
[`FirstOverageCallFinderTest`](src/test/java/com/algorithms/searching/linearsearch/applied/FirstOverageCallFinderTest.java)
covers a call found partway through the log, no call exceeding the allowance, and the
null-argument guard.

## Benchmark

```bash
./gradlew :searching:linear-search:jmh
```

Real run on this machine (JMH 1.37, JDK 26.0.2, 2 warmup + 3 measurement iterations, 1 fork).
Worst case: the target is never present, forcing a full scan. Same task, same sizes, same
machine as this repo's [Binary Search](../binary-search) benchmark, so the two are directly
comparable:

| Search cost (missing target) | size=100 | size=10,000 | size=1,000,000 |
|---|---:|---:|---:|
| Linear Search | 36.41 ns | 6,589.62 ns | 1,676,427.30 ns |

Cost grows roughly in step with size — ~181x for a 100x size increase (100→10,000), ~254x for
another 100x increase (10,000→1,000,000) — noisy around the ideal ~100x, but unmistakably linear,
not sub-linear. At size=1,000,000, [Binary Search](../binary-search)'s equivalent lookup on this
same machine runs in 30.29 ns — this module's linear scan is **~55,353x slower** for the exact
same "is it there" question, purely because it can't assume the data is sorted. That gap is the
entire reason a sorted-data search deserves its own algorithm.

## When not to use it

- Data is already sorted, or can be sorted once and queried many times? This repo's
  [Binary Search](../binary-search) answers the same question in O(log n) — the benchmark above
  shows exactly how much that matters at scale.
- Searching the same collection repeatedly for different values? Building an index once (a hash
  table, a sorted structure) amortizes better than a fresh O(n) scan on every query.
- This module's real value is precisely when the data *can't* be assumed sorted, or when it's
  scanned so rarely that building any kind of index isn't worth the overhead.

## Test coverage

100% instruction coverage, 100% branch coverage (JaCoCo). Reproduce it yourself:

```bash
./gradlew :searching:linear-search:jacocoTestReport
```

Report at `searching/linear-search/build/reports/jacoco/test/html/index.html`.

## Further reading

- Cormen, Leiserson, Rivest & Stein — *Introduction to Algorithms* (CLRS), 3rd/4th ed.,
  Chapter 2 — linear search is the implicit baseline CLRS measures every faster search against.
- Sedgewick & Wayne — *Algorithms*, 4th ed., section 3.1, "Symbol Tables" — frames sequential
  search as the starting point the rest of the chapter's data structures improve on.
