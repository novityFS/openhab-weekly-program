package de.novity.openhab.hvac.domain;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TimeProgramBuilderImplementation implements TimeProgramBuilder {
    private final List<SwitchCycle> switchCycles = new ArrayList<SwitchCycle>();

    public TimeProgram defaultProgram() {
        List<SwitchCycle> timeProgram = new ArrayList<SwitchCycle>();
        timeProgram.add(new SwitchCycle(LocalTime.parse("00:00"), OperatingMode.Auto));

        TimeProgram defaultProgram = new TimeProgram(timeProgram);
        return defaultProgram;
    }

    public TimeProgramBuilder addSwitchCycle(SwitchCycle switchCycle) {
        switchCycles.add(switchCycle);
        return this;
    }

    public TimeProgramBuilder applyTimeProgram(TimeProgram timeProgram) {
        switchCycles.addAll(timeProgram);
        return this;
    }

    public TimeProgram build() {
        return new TimeProgram(switchCycles);
    }
}
