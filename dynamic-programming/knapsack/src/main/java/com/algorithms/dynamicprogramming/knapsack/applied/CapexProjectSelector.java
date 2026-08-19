package com.algorithms.dynamicprogramming.knapsack.applied;

import com.algorithms.dynamicprogramming.knapsack.classic.Knapsack;
import com.algorithms.dynamicprogramming.knapsack.classic.KnapsackResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Selects which candidate infrastructure projects to fund out of a fixed annual capex budget,
 * maximizing total projected value — the textbook business framing of 0/1 knapsack: a project
 * either gets funded in full or not at all, there's no such thing as funding 60% of a fiber
 * build-out, and the budget is the hard capacity constraint. Real project lists are small enough
 * (dozens, not millions) that the DP table's O(projects x budget) cost is trivial in practice,
 * but the *selection* problem itself is exactly as combinatorially hard as any other knapsack
 * instance - which is why picking projects "by best ROI first" (a greedy shortcut) doesn't
 * reliably find the optimal combination the way it happens to for this repo's Coin Change module
 * on ordinary currency.
 */
public final class CapexProjectSelector {

    public List<CapexProject> selectWithinBudget(List<CapexProject> candidates, int budget) {
        if (candidates == null) {
            throw new IllegalArgumentException("candidates must not be null");
        }
        int n = candidates.size();
        int[] costs = new int[n];
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            costs[i] = candidates.get(i).cost();
            values[i] = candidates.get(i).projectedValue();
        }

        KnapsackResult result = Knapsack.solve(costs, values, budget);
        List<CapexProject> selected = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (result.selected()[i]) {
                selected.add(candidates.get(i));
            }
        }
        return selected;
    }
}
