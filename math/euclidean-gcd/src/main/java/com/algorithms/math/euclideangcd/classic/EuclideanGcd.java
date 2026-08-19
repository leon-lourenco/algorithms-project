package com.algorithms.math.euclideangcd.classic;

/**
 * The greatest common divisor of two non-negative integers - the largest integer that divides
 * both with no remainder. Checking every candidate divisor down from the smaller of the two
 * numbers is O(min(a, b)). Euclid's algorithm relies on one identity instead: gcd(a, b) equals
 * gcd(b, a mod b). Repeatedly replacing the pair with (b, a mod b) shrinks the numbers at least
 * as fast as the Fibonacci sequence grows in reverse - the algorithm's own textbook worst case is
 * consecutive Fibonacci numbers - giving O(log(min(a, b))) steps overall, provably faster than
 * any comparable subtraction-based approach.
 */
public final class EuclideanGcd {

    private EuclideanGcd() {
    }

    public static long gcd(long a, long b) {
        validate(a, b);
        while (b != 0) {
            long remainder = a % b;
            a = b;
            b = remainder;
        }
        return a;
    }

    public static long lcm(long a, long b) {
        validate(a, b);
        if (a == 0 || b == 0) {
            return 0;
        }
        return (a / gcd(a, b)) * b;
    }

    /** Counts down from the smaller value looking for a common divisor - O(min(a, b)), the brute force Euclid's algorithm replaces. */
    public static long bruteForceGcd(long a, long b) {
        validate(a, b);
        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }
        long smaller = Math.min(a, b);
        for (long candidate = smaller; candidate > 1; candidate--) {
            if (a % candidate == 0 && b % candidate == 0) {
                return candidate;
            }
        }
        return 1;
    }

    private static void validate(long a, long b) {
        if (a < 0 || b < 0) {
            throw new IllegalArgumentException("a and b must be >= 0");
        }
        if (a == 0 && b == 0) {
            throw new IllegalArgumentException("gcd(0, 0) is undefined");
        }
    }
}
