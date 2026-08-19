package com.algorithms.math.euclideangcd.applied;

import com.algorithms.math.euclideangcd.classic.EuclideanGcd;

/**
 * A PIX marketplace split-payment rule divides one incoming payment among several recipients
 * according to a ratio (e.g. a platform keeps 3,000 basis units and a seller receives 7,000 out
 * of 10,000). BACEN's settlement rule engine stores that ratio in lowest terms - both for a
 * canonical, auditable representation and so the numbers involved in the arrangement rule stay as
 * small as possible.
 */
public final class PaymentSplitReducer {

    public SplitRatio reduceToLowestTerms(long platformShare, long recipientShare) {
        if (platformShare <= 0 || recipientShare <= 0) {
            throw new IllegalArgumentException("platformShare and recipientShare must both be positive");
        }
        long divisor = EuclideanGcd.gcd(platformShare, recipientShare);
        return new SplitRatio(platformShare / divisor, recipientShare / divisor);
    }

    public record SplitRatio(long platformShare, long recipientShare) {
    }
}
