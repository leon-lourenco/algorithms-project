package com.algorithms.stringmatching.knuthmorrispratt.classic;

import java.util.ArrayList;
import java.util.List;

/**
 * Finding every occurrence of a pattern inside a text. Checking every starting position from
 * scratch (brute force) is O(n * m): a text like "AAAAAAAAAAAAB" against a pattern like "AAAAB"
 * re-walks nearly the whole pattern from each of the text's many almost-matching starting points.
 * KMP's insight: when a mismatch happens after several characters already matched, those matched
 * characters tell you something about the pattern itself — specifically, how much of what you've
 * already matched is also a prefix of the pattern, which is exactly how far you can safely skip
 * ahead without missing a possible match. That's precomputed once per pattern as the "failure
 * function" (a.k.a. LPS - longest proper prefix that's also a suffix, ending at each position),
 * so scanning the text never has to step backward - O(n + m) total, one preprocessing pass over
 * the pattern and one pass over the text.
 */
public final class KnuthMorrisPratt {

    private KnuthMorrisPratt() {
    }

    /** {@code lps[i]} = length of the longest proper prefix of {@code pattern} that is also a suffix of {@code pattern[0..i]}. */
    public static int[] failureFunction(String pattern) {
        validatePattern(pattern);
        int[] lps = new int[pattern.length()];
        int length = 0;
        int i = 1;
        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else if (length != 0) {
                length = lps[length - 1];
            } else {
                lps[i] = 0;
                i++;
            }
        }
        return lps;
    }

    public static List<Integer> search(String text, String pattern) {
        validateText(text);
        validatePattern(pattern);
        List<Integer> matches = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return matches;
        }
        int[] lps = failureFunction(pattern);
        int i = 0;
        int j = 0;
        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                if (j == pattern.length()) {
                    matches.add(i - j);
                    j = lps[j - 1];
                }
            } else if (j != 0) {
                j = lps[j - 1];
            } else {
                i++;
            }
        }
        return matches;
    }

    /** Tries every starting position directly - O(n * m) - the brute force KMP's LPS array replaces. */
    public static List<Integer> bruteForceSearch(String text, String pattern) {
        validateText(text);
        validatePattern(pattern);
        List<Integer> matches = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();
        for (int start = 0; start + m <= n; start++) {
            int j = 0;
            while (j < m && text.charAt(start + j) == pattern.charAt(j)) {
                j++;
            }
            if (j == m) {
                matches.add(start);
            }
        }
        return matches;
    }

    private static void validateText(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text must not be null");
        }
    }

    private static void validatePattern(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("pattern must not be null or empty");
        }
    }
}
