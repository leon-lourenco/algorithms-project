package com.algorithms.sorting.insertionsort.applied;

import com.algorithms.sorting.insertionsort.classic.InsertionSort;

import java.util.Comparator;

/**
 * Sorts a small batch of call detail records by start time before handing them to a real-time
 * rating/billing engine. A single subscriber's calls within a short billing window is exactly
 * the shape this algorithm is actually good at: a small n, and — since carrier-side event
 * ingestion is itself roughly chronological — usually already close to sorted by the time it
 * reaches this stage. That combination is the same reason real production sorts (the JDK's own
 * {@code Arrays.sort}) switch to insertion sort below a small size threshold instead of paying
 * an O(n log n) algorithm's setup cost on a batch this small.
 */
public final class CallDetailRecordSort {

    /**
     * Real-world sorts (the JDK's own {@code Arrays.sort}/TimSort among them) fall back to
     * insertion sort below roughly this many elements.
     */
    public static final int RECOMMENDED_MAX_BATCH_SIZE = 64;

    private static final Comparator<CallDetailRecord> BY_START_TIME = Comparator.comparing(CallDetailRecord::startTime);

    public CallDetailRecord[] sortByStartTime(CallDetailRecord[] records) {
        if (records == null) {
            throw new IllegalArgumentException("records must not be null");
        }
        CallDetailRecord[] sorted = records.clone();
        InsertionSort.sort(sorted, BY_START_TIME);
        return sorted;
    }
}
