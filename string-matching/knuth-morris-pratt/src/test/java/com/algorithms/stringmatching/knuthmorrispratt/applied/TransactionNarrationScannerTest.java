package com.algorithms.stringmatching.knuthmorrispratt.applied;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransactionNarrationScannerTest {

    private final TransactionNarrationScanner scanner = new TransactionNarrationScanner();

    @Test
    void flagsANarrationContainingAWatchlistedMerchantFragment() {
        String narration = "WIRE TRF TO SHELLCORP-HOLDINGS REF 88213";

        assertThat(scanner.containsWatchlistToken(narration, "SHELLCORP")).isTrue();
        assertThat(scanner.findWatchlistOccurrences(narration, "SHELLCORP")).containsExactly(12);
    }

    @Test
    void clearsANarrationWithNoWatchlistMatch() {
        String narration = "PAYMENT TO ACME SUPPLIES LTD";

        assertThat(scanner.containsWatchlistToken(narration, "SHELLCORP")).isFalse();
        assertThat(scanner.findWatchlistOccurrences(narration, "SHELLCORP")).isEmpty();
    }

    @Test
    void rejectsANullNarration() {
        assertThatThrownBy(() -> scanner.findWatchlistOccurrences(null, "X")).isInstanceOf(IllegalArgumentException.class);
    }
}
