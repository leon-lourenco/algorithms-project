package com.algorithms.sorting.quicksort.applied;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ClaimAmountSortTest {

    @Test
    void sortByAmountOrdersClaimsAscending() {
        ClaimAmountSort sorter = new ClaimAmountSort();
        InsuranceClaim[] claims = {
                claim("mid", "500.00"),
                claim("high", "9000.00"),
                claim("low", "12.50"),
        };

        InsuranceClaim[] sorted = sorter.sortByAmount(claims);

        assertThat(sorted).extracting(InsuranceClaim::claimId).containsExactly("low", "mid", "high");
    }

    @Test
    void doesNotMutateTheInputArray() {
        ClaimAmountSort sorter = new ClaimAmountSort();
        InsuranceClaim[] claims = {claim("b", "200.00"), claim("a", "10.00")};

        sorter.sortByAmount(claims);

        assertThat(claims).extracting(InsuranceClaim::claimId).containsExactly("b", "a");
    }

    @Test
    void rejectsNullClaims() {
        ClaimAmountSort sorter = new ClaimAmountSort();

        assertThatThrownBy(() -> sorter.sortByAmount(null)).isInstanceOf(IllegalArgumentException.class);
    }

    private static InsuranceClaim claim(String id, String amount) {
        return new InsuranceClaim(id, new BigDecimal(amount));
    }
}
