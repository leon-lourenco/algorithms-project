package com.algorithms.dynamicprogramming.knapsack.applied;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CapexProjectSelectorTest {

    @Test
    void selectsTheHighestValueCombinationWithinBudget() {
        CapexProjectSelector selector = new CapexProjectSelector();
        List<CapexProject> candidates = List.of(
                new CapexProject("fiber-north", 3, 4),
                new CapexProject("fiber-south", 4, 5),
                new CapexProject("tower-upgrade", 1, 1),
                new CapexProject("backup-power", 5, 7)
        );

        List<CapexProject> selected = selector.selectWithinBudget(candidates, 7);

        assertThat(selected).extracting(CapexProject::name).containsExactly("fiber-north", "fiber-south");
    }

    @Test
    void emptyCandidateListSelectsNothing() {
        CapexProjectSelector selector = new CapexProjectSelector();

        List<CapexProject> selected = selector.selectWithinBudget(List.of(), 100);

        assertThat(selected).isEmpty();
    }

    @Test
    void zeroBudgetSelectsNothing() {
        CapexProjectSelector selector = new CapexProjectSelector();
        List<CapexProject> candidates = List.of(new CapexProject("fiber-north", 3, 4));

        List<CapexProject> selected = selector.selectWithinBudget(candidates, 0);

        assertThat(selected).isEmpty();
    }

    @Test
    void rejectsANullCandidateList() {
        CapexProjectSelector selector = new CapexProjectSelector();

        assertThatThrownBy(() -> selector.selectWithinBudget(null, 10)).isInstanceOf(IllegalArgumentException.class);
    }
}
