package com.algorithms.math.fastexponentiation.applied;

import com.algorithms.math.fastexponentiation.classic.FastExponentiation;

/**
 * An insurer projecting an actuarial reserve's accumulated value after many compounding periods
 * needs {@code (1 + periodicRate)^periods} - the same growth-factor computation whether the
 * periods are months in a reserve projection or years in a long-dated annuity schedule, and
 * exactly the shape exponentiation by squaring is built for.
 */
public final class CompoundGrowthCalculator {

    public double accumulatedValue(double principal, double periodicRate, long periods) {
        if (principal < 0) {
            throw new IllegalArgumentException("principal must be >= 0");
        }
        if (periodicRate <= -1.0) {
            throw new IllegalArgumentException("periodicRate must be > -1 (cannot lose more than 100% in a period)");
        }
        if (periods < 0) {
            throw new IllegalArgumentException("periods must be >= 0");
        }
        double growthFactor = FastExponentiation.power(1.0 + periodicRate, periods);
        return principal * growthFactor;
    }
}
