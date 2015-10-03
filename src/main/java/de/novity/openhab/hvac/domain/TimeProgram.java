package de.novity.openhab.hvac.domain;

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;

public class TimeProgram extends AbstractList<SwitchCycle> {
    private final List<SwitchCycle> timeProgram;

    public TimeProgram(List<SwitchCycle> timeProgram) {
        if ((timeProgram == null) || (timeProgram.isEmpty())) {
            throw new IllegalArgumentException("Time program must not be null or empty");
        }

        Collections.sort(timeProgram);
        this.timeProgram = timeProgram;
    }

    @Override
    public SwitchCycle get(int index) {
        return timeProgram.get(index);
    }

    @Override
    public int size() {
        return timeProgram.size();
    }

    public SwitchCycle getEarliestSwitchCycle() {
        return timeProgram.get(0);
    }

    public SwitchCycle getLatestSwitchCycle() {
        return timeProgram.get(timeProgram.size() - 1);
    }
}
