package com.algorithms.sorting.bubblesort.applied;

import com.algorithms.sorting.bubblesort.classic.BubbleSort;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Re-sorts a small daily batch of ledger entries after a single late-arriving correction lands
 * out of order — the exact shape legacy mainframe batch jobs still run into: yesterday's file
 * closed already sorted by posting time, and now one correction entry needs to be spliced back
 * into its correct position before the batch can be reprocessed. Appending the correction and
 * re-running bubble sort over the whole (still almost entirely sorted) batch is a legitimate
 * choice here specifically because the disruption is small: bubble sort's early-exit adaptivity
 * means the actual cost tracks how far out of place the correction is, not the batch size.
 */
public final class DailyLedgerReorder {

    private static final Comparator<LedgerEntry> BY_POSTED_AT = Comparator.comparing(LedgerEntry::postedAt);

    public LedgerEntry[] reorderWithCorrection(LedgerEntry[] alreadySortedBatch, LedgerEntry correction) {
        if (alreadySortedBatch == null) {
            throw new IllegalArgumentException("alreadySortedBatch must not be null");
        }
        if (correction == null) {
            throw new IllegalArgumentException("correction must not be null");
        }
        LedgerEntry[] withCorrection = Arrays.copyOf(alreadySortedBatch, alreadySortedBatch.length + 1);
        withCorrection[alreadySortedBatch.length] = correction;
        BubbleSort.sort(withCorrection, BY_POSTED_AT);
        return withCorrection;
    }
}
