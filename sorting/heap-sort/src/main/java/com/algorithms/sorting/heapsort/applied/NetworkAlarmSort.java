package com.algorithms.sorting.heapsort.applied;

import com.algorithms.sorting.heapsort.classic.HeapSort;

import java.util.Comparator;

/**
 * Sorts a batch of network alarms by severity, highest first, on constrained telecom edge
 * equipment — the one place among this repo's sorting modules where "guaranteed O(n log n)"
 * and "zero auxiliary memory" both matter at the same time, not just one or the other. Merge
 * sort's O(n) buffer risks an allocation the device's tight RAM budget can't always absorb; a
 * quicksort's (even randomized) worst-case risk is a real-time-processing constraint this
 * alarm-handling loop can't accept. Heap sort is the one sort in this repo that gives up
 * neither guarantee.
 */
public final class NetworkAlarmSort {

    private static final Comparator<NetworkAlarm> BY_SEVERITY_DESCENDING =
            Comparator.comparingInt(NetworkAlarm::severity).reversed();

    public NetworkAlarm[] sortBySeverityDescending(NetworkAlarm[] alarms) {
        if (alarms == null) {
            throw new IllegalArgumentException("alarms must not be null");
        }
        NetworkAlarm[] sorted = alarms.clone();
        HeapSort.sort(sorted, BY_SEVERITY_DESCENDING);
        return sorted;
    }
}
