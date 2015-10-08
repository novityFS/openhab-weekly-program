package de.novity.openhab.hvac.domain;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeProgramBuilder {
    private String name;
    private List<SwitchCycle> cycles = new ArrayList<SwitchCycle>();

    public TimeProgram defaultProgram() {
        name = "Default";
        cycles.add(new SwitchCycle(LocalTime.parse("00:00"), OperatingMode.Auto));
        return build();
    }

    public TimeProgramBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TimeProgramBuilder addSwitchCycle(SwitchCycle switchCycle) {
        cycles.add(switchCycle);
        return this;
    }

    public TimeProgramBuilder applyTimeProgram(TimeProgram timeProgram) {
        cycles.addAll(timeProgram.getCycles());
        return this;
    }

    public TimeProgram build() {
        TimeProgram timeProgram = new TimeProgram(name);
        timeProgram.addAll(cycles);

        return timeProgram;
    }
}
