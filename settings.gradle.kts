rootProject.name = "algorithms-project"

// Modules are added incrementally, one per commit, as each algorithm is implemented.
include(
    "sorting:bubble-sort",
    "sorting:insertion-sort",
    "sorting:merge-sort",
    "sorting:quick-sort",
    "sorting:heap-sort",
    "searching:linear-search",
    "searching:binary-search",
    "dynamic-programming:fibonacci",
    "dynamic-programming:knapsack",
    "dynamic-programming:longest-common-subsequence",
    "greedy:coin-change",
    "greedy:huffman-coding",
    "string-matching:knuth-morris-pratt",
    "math:euclidean-gcd",
    "math:sieve-of-eratosthenes",
    "math:fast-exponentiation",
    "backtracking:n-queens",
)
