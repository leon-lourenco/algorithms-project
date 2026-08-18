package com.algorithms.sorting.heapsort.applied;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NetworkAlarmSortTest {

    @Test
    void sortBySeverityDescendingOrdersHighestFirst() {
        NetworkAlarmSort sorter = new NetworkAlarmSort();
        NetworkAlarm[] alarms = {
                new NetworkAlarm("low", 2),
                new NetworkAlarm("critical", 9),
                new NetworkAlarm("mid", 5),
        };

        NetworkAlarm[] sorted = sorter.sortBySeverityDescending(alarms);

        assertThat(sorted).extracting(NetworkAlarm::alarmId).containsExactly("critical", "mid", "low");
    }

    @Test
    void doesNotMutateTheInputArray() {
        NetworkAlarmSort sorter = new NetworkAlarmSort();
        NetworkAlarm[] alarms = {new NetworkAlarm("b", 1), new NetworkAlarm("a", 9)};

        sorter.sortBySeverityDescending(alarms);

        assertThat(alarms).extracting(NetworkAlarm::alarmId).containsExactly("b", "a");
    }

    @Test
    void rejectsNullAlarms() {
        NetworkAlarmSort sorter = new NetworkAlarmSort();

        assertThatThrownBy(() -> sorter.sortBySeverityDescending(null)).isInstanceOf(IllegalArgumentException.class);
    }
}
