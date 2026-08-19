package com.algorithms.searching.linearsearch.applied;

import com.algorithms.searching.linearsearch.classic.LinearSearch;

/**
 * Finds the first call in a daily call-detail-record log whose duration exceeds a subscriber's
 * plan allowance, to trigger a real-time overage alert. The log is ordered by arrival time (as
 * calls stream in from towers), not by duration — there is no sorted-by-duration view to binary
 * search against, and re-sorting the whole log by duration on every check just to search it
 * would cost more than the linear scan itself. This is the honest case for linear search: the
 * data genuinely isn't ordered by the field being searched on.
 */
public final class FirstOverageCallFinder {

    public int findFirstOverage(CallRecord[] log, int allowanceSeconds) {
        if (log == null) {
            throw new IllegalArgumentException("log must not be null");
        }
        return LinearSearch.indexOf(log, record -> record.durationSeconds() > allowanceSeconds);
    }
}
