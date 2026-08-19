package com.algorithms.stringmatching.knuthmorrispratt.applied;

import com.algorithms.stringmatching.knuthmorrispratt.classic.KnuthMorrisPratt;

import java.util.List;

/**
 * A fraud detection platform scans the free-text narration/description field of every incoming
 * transaction for known watchlist tokens - blacklisted merchant fragments, sanctioned-entity
 * name substrings. That scan runs on every single transaction in a high-volume stream, so its
 * worst case matters, not just its average case: brute-force substring search's O(n * m) worst
 * case is a real algorithmic-complexity attack surface here, not a theoretical concern - an
 * adversary who can influence narration text (a wire transfer memo field, for instance) could
 * craft a near-miss pattern deliberately to slow the scanner down. KMP's O(n + m) guarantee holds
 * regardless of how adversarial the input is.
 */
public final class TransactionNarrationScanner {

    public List<Integer> findWatchlistOccurrences(String narration, String watchlistToken) {
        if (narration == null) {
            throw new IllegalArgumentException("narration must not be null");
        }
        return KnuthMorrisPratt.search(narration, watchlistToken);
    }

    public boolean containsWatchlistToken(String narration, String watchlistToken) {
        return !findWatchlistOccurrences(narration, watchlistToken).isEmpty();
    }
}
