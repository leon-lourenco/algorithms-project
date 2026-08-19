package com.algorithms.math.sieveoferatosthenes.applied;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HashBucketSizerTest {

    private final HashBucketSizer sizer = new HashBucketSizer();

    @Test
    void findsTheNextPrimeAtOrAboveAPowerOfTwoCapacity() {
        // 1024 = 2^10 is not prime; the next prime at or above it is 1031.
        assertThat(sizer.nextPrimeBucketCount(1024)).isEqualTo(1031);
    }

    @Test
    void aCapacityThatIsAlreadyPrimeReturnsItself() {
        assertThat(sizer.nextPrimeBucketCount(97)).isEqualTo(97);
    }

    @Test
    void rejectsACapacityBelowTwo() {
        assertThatThrownBy(() -> sizer.nextPrimeBucketCount(1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> sizer.nextPrimeBucketCount(0)).isInstanceOf(IllegalArgumentException.class);
    }
}
