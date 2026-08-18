package com.algorithms.sorting.bubblesort.applied;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DailyLedgerReorderTest {

    private static final Instant BASE = Instant.parse("2026-08-17T09:00:00Z");

    @Test
    void spliceALateCorrectionIntoTheMiddleOfTheBatch() {
        DailyLedgerReorder reorder = new DailyLedgerReorder();
        LedgerEntry[] batch = {
                entry("a", 0),
                entry("b", 1),
                entry("c", 3),
                entry("d", 4),
        };
        LedgerEntry correction = entry("late", 2);

        LedgerEntry[] result = reorder.reorderWithCorrection(batch, correction);

        assertThat(result).extracting(LedgerEntry::id).containsExactly("a", "b", "late", "c", "d");
    }

    @Test
    void correctionThatBelongsAtTheStartMovesToTheFront() {
        DailyLedgerReorder reorder = new DailyLedgerReorder();
        LedgerEntry[] batch = {entry("a", 1), entry("b", 2), entry("c", 3)};
        LedgerEntry correction = entry("earliest", 0);

        LedgerEntry[] result = reorder.reorderWithCorrection(batch, correction);

        assertThat(result).extracting(LedgerEntry::id).containsExactly("earliest", "a", "b", "c");
    }

    @Test
    void correctionThatBelongsAtTheEndStaysAppended() {
        DailyLedgerReorder reorder = new DailyLedgerReorder();
        LedgerEntry[] batch = {entry("a", 0), entry("b", 1)};
        LedgerEntry correction = entry("latest", 2);

        LedgerEntry[] result = reorder.reorderWithCorrection(batch, correction);

        assertThat(result).extracting(LedgerEntry::id).containsExactly("a", "b", "latest");
    }

    @Test
    void rejectsANullBatch() {
        DailyLedgerReorder reorder = new DailyLedgerReorder();

        assertThatThrownBy(() -> reorder.reorderWithCorrection(null, entry("x", 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejectsANullCorrection() {
        DailyLedgerReorder reorder = new DailyLedgerReorder();

        assertThatThrownBy(() -> reorder.reorderWithCorrection(new LedgerEntry[0], null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static LedgerEntry entry(String id, long offsetMinutes) {
        return new LedgerEntry(id, BASE.plus(offsetMinutes, ChronoUnit.MINUTES));
    }
}
