package de.novity.openhab.hvac.domain;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeScheduleBuilder {
    private String name;
    private List<SwitchCycle> cycles = new ArrayList<SwitchCycle>();

    public TimeSchedule defaultSchedule() {
        name = "Default";
        cycles.add(new SwitchCycle(LocalTime.parse("00:00"), OperatingMode.Auto));
        return build();
    }

    public TimeScheduleBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TimeScheduleBuilder addSwitchCycle(SwitchCycle switchCycle) {
        cycles.add(switchCycle);
        return this;
    }

    public TimeScheduleBuilder applyTimeProgram(TimeSchedule timeSchedule) {
        cycles.addAll(timeSchedule.getCycles());
        return this;
    }

    public TimeSchedule build() {
        TimeSchedule timeSchedule = new TimeSchedule(name);
        timeSchedule.addAll(cycles);

        return timeSchedule;
    }
}
