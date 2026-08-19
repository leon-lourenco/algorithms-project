# Binary Search

**Category:** Searching

## The problem

This repo's [Linear Search](../linear-search) answers "is this here?" in O(n) because it can't
assume anything about the data's order. If the data *is* already sorted — a common, cheap
assumption once it's been sorted a single time — that O(n) is leaving an enormous amount of
information on the table: every comparison against an unsorted collection tells you almost
nothing about where else to look; a comparison against a *sorted* one tells you which entire half
to discard.

## The solution

Compare the target against the middle element. If it matches, done. If the target is smaller,
the entire upper half can be discarded — every element there is guaranteed larger. If it's
larger, discard the lower half. Repeat on whatever's left. Each comparison eliminates half the
remaining candidates, which is what makes this O(log n): the search space shrinks
geometrically, not linearly. Implemented iteratively with a shrinking `[low, high]` window rather
than recursively, so a very large array never risks a stack frame per halving.

| Case | Cost | Why |
|---|---|---|
| Best (match at the midpoint) | O(1) | the very first comparison succeeds |
| Worst (no match, or match at an extreme) | O(log n) | the search space still halves every step regardless |
| Average | O(log n) | same halving shape, just fewer steps than the worst case |

## Classic example

[`classic/BinarySearch`](src/main/java/com/algorithms/searching/binarysearch/classic/BinarySearch.java)
is generic over `Comparator<? super T>` — no `Arrays.binarySearch` shortcut. The loop shrinks a
`[low, high]` window each iteration instead of recursing, which is what keeps this safe on
arrays too large for a recursive call stack to handle comfortably.
[`BinarySearchTest`](src/test/java/com/algorithms/searching/binarysearch/classic/BinarySearchTest.java)
covers a target in the middle, at the start, at the end, a missing target, an empty array, a
single-element array, every position of an even-sized array (exercises both midpoint-rounding
directions), and both null-argument guards.

## Applied example: BACEN PIX-key snapshot lookup

[`applied/RegisteredPixKeyLookup`](src/main/java/com/algorithms/searching/binarysearch/applied/RegisteredPixKeyLookup.java)
checks whether a PIX key is registered against a nightly, already-sorted snapshot of every
registered key — the kind of batch export a reconciliation job pulls once and then queries many
times over. Unlike a trie built incrementally as keys register in real time, this assumes the
entire key set is already known and fixed for the day: sorting it once up front and binary
searching it per lookup is cheaper than maintaining a live structure for data that doesn't change
until tomorrow's snapshot.
[`RegisteredPixKeyLookupTest`](src/test/java/com/algorithms/searching/binarysearch/applied/RegisteredPixKeyLookupTest.java)
covers a registered key, an unregistered key, and both null-argument guards.

## Benchmark

```bash
./gradlew :searching:binary-search:jmh
```

Real run on this machine (JMH 1.37, JDK 26.0.2, 2 warmup + 3 measurement iterations, 1 fork).
Same worst case, same sizes, same machine as this repo's [Linear Search](../linear-search)
benchmark:

| Search cost (missing target) | size=100 | size=10,000 | size=1,000,000 |
|---|---:|---:|---:|
| Binary Search | 9.60 ns | 20.62 ns | 30.29 ns |

That's the O(log n) claim, made unmistakable: a 100x increase in size (100→10,000) costs only
~2.15x more time; a further 100x increase (10,000→1,000,000) costs only ~1.47x more — shrinking
multipliers for the same proportional growth in data, exactly the signature of logarithmic
scaling (`log₂(10,000)/log₂(100) ≈ 2.0`, `log₂(1,000,000)/log₂(10,000) ≈ 1.5` — the predicted
shape, matched almost exactly). Against [Linear Search](../linear-search)'s 1,676,427.30 ns for
the identical question at size=1,000,000, this module answers in 30.29 ns — **~55,353x faster**,
on the same machine, for the same "is it there" question. The only difference is one bit of
information linear search doesn't get to use: the data is sorted.

## When not to use it

- Data isn't sorted, and won't be queried enough times to justify sorting it once? This repo's
  [Linear Search](../linear-search) skips the sorting cost entirely — worth it only if the
  number of lookups stays small.
- Data changes frequently (frequent inserts/deletes) and needs to stay searchable at every step?
  Keeping an array sorted under churn costs O(n) per insert to maintain — a self-balancing tree
  structure (an AVL tree or a B-tree, for instance) keeps both operations fast instead of trading
  one for the other.
- Need the *closest* key, not just an exact match, or an ordered range? This implementation
  returns `-1` on a miss and throws away exactly where the target would have gone — a floor/
  ceiling-aware variant would need to return that boundary instead.

## Test coverage

100% instruction coverage, 100% branch coverage (JaCoCo). Reproduce it yourself:

```bash
./gradlew :searching:binary-search:jacocoTestReport
```

Report at `searching/binary-search/build/reports/jacoco/test/html/index.html`.

## Further reading

- Cormen, Leiserson, Rivest & Stein — *Introduction to Algorithms* (CLRS), 3rd/4th ed.,
  Problem 2-4 covers search-related recurrences; binary search itself is a running example
  throughout the divide-and-conquer chapters.
- Sedgewick & Wayne — *Algorithms*, 4th ed., section 3.1, "Symbol Tables" — presents binary
  search as `BinarySearch.rank`, the baseline every ordered symbol table in the book improves on.
