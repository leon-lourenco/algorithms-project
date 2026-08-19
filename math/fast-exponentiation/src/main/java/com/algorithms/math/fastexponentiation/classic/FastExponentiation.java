package com.algorithms.math.fastexponentiation.classic;

/**
 * Raising a number to an integer power. Multiplying the base by itself one exponent at a time is
 * O(exponent). Exponentiation by squaring cuts that down to O(log exponent): {@code base^n}
 * equals {@code (base^2)^(n/2)} when {@code n} is even, so squaring the base and halving the
 * exponent reaches the same result in about half as many multiplications - and that halving
 * compounds every step. For an odd exponent, one factor of {@code base} is peeled off first so
 * the remaining exponent is even again, and the process repeats on the (now smaller) exponent's
 * bits, one per iteration.
 */
public final class FastExponentiation {

    private FastExponentiation() {
    }

    public static double power(double base, long exponent) {
        validate(base, exponent);
        if (exponent < 0) {
            return 1.0 / power(base, -exponent);
        }
        double result = 1.0;
        double currentBase = base;
        long currentExponent = exponent;
        while (currentExponent > 0) {
            if ((currentExponent & 1) == 1) {
                result *= currentBase;
            }
            currentBase *= currentBase;
            currentExponent >>= 1;
        }
        return result;
    }

    /** Multiplies the base by itself one exponent at a time - O(exponent), the brute force this module replaces. */
    public static double bruteForcePower(double base, long exponent) {
        validate(base, exponent);
        if (exponent < 0) {
            return 1.0 / bruteForcePower(base, -exponent);
        }
        double result = 1.0;
        for (long i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }

    private static void validate(double base, long exponent) {
        if (base == 0.0 && exponent < 0) {
            throw new IllegalArgumentException("0 cannot be raised to a negative exponent");
        }
    }
}
