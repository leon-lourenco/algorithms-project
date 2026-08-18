package com.algorithms.sorting.insertionsort.applied;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CallDetailRecordSortTest {

    private static final Instant BASE = Instant.parse("2026-08-17T09:00:00Z");

    @Test
    void sortByStartTimeOrdersRecordsAscendingByStartTime() {
        CallDetailRecordSort sorter = new CallDetailRecordSort();
        CallDetailRecord[] records = {
                record("c", 5),
                record("a", 1),
                record("b", 3),
        };

        CallDetailRecord[] sorted = sorter.sortByStartTime(records);

        assertThat(sorted).extracting(CallDetailRecord::callId).containsExactly("a", "b", "c");
    }

    @Test
    void sortByStartTimeDoesNotMutateTheInputArray() {
        CallDetailRecordSort sorter = new CallDetailRecordSort();
        CallDetailRecord[] records = {record("b", 2), record("a", 1)};

        sorter.sortByStartTime(records);

        assertThat(records).extracting(CallDetailRecord::callId).containsExactly("b", "a");
    }

    @Test
    void rejectsNullRecords() {
        CallDetailRecordSort sorter = new CallDetailRecordSort();

        assertThatThrownBy(() -> sorter.sortByStartTime(null)).isInstanceOf(IllegalArgumentException.class);
    }

    private static CallDetailRecord record(String callId, long offsetMinutes) {
        return new CallDetailRecord(callId, BASE.plus(offsetMinutes, ChronoUnit.MINUTES));
    }
}
