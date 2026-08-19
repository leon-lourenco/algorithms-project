package com.algorithms.math.sieveoferatosthenes.classic;

import java.util.ArrayList;
import java.util.List;

/**
 * Finding every prime number up to a limit. Testing each number individually for primality by
 * trial division is O(sqrt(k)) per number, O(n * sqrt(n)) total across the whole range. The
 * sieve flips that: instead of asking "is this number prime?" one number at a time, it starts
 * from every prime found so far and crosses off all of its multiples in one sweep. Each number
 * gets crossed off once for each of its prime factors, and the sum of those sweeps across the
 * whole range - the harmonic-like series 1/2 + 1/3 + 1/5 + 1/7 + ... over the primes up to n -
 * converges to O(n log log n), one of the tightest bounds in classical algorithm analysis.
 */
public final class SieveOfEratosthenes {

    private SieveOfEratosthenes() {
    }

    public static List<Integer> primesUpTo(int limit) {
        validate(limit);
        boolean[] composite = new boolean[limit + 1];
        List<Integer> primes = new ArrayList<>();
        for (int candidate = 2; candidate <= limit; candidate++) {
            if (composite[candidate]) {
                continue;
            }
            primes.add(candidate);
            for (long multiple = (long) candidate * candidate; multiple <= limit; multiple += candidate) {
                composite[(int) multiple] = true;
            }
        }
        return primes;
    }

    /** Trial-divides every number up to the limit individually - O(n * sqrt(n)), the brute force the sieve replaces. */
    public static List<Integer> bruteForcePrimesUpTo(int limit) {
        validate(limit);
        List<Integer> primes = new ArrayList<>();
        for (int candidate = 2; candidate <= limit; candidate++) {
            if (isPrimeByTrialDivision(candidate)) {
                primes.add(candidate);
            }
        }
        return primes;
    }

    private static boolean isPrimeByTrialDivision(int candidate) {
        for (int divisor = 2; (long) divisor * divisor <= candidate; divisor++) {
            if (candidate % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    private static void validate(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("limit must be >= 0");
        }
    }
}
