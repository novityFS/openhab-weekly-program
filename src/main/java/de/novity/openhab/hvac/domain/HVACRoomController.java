package de.novity.openhab.hvac.domain;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HVACRoomController {
    private final String name;
    private TimeProgram timeProgram;

    private LocalTime lastUpdateAppliedAt;

    private SwitchCycle activeCycle;
    private SwitchCycle nextCycleAhead;

    public HVACRoomController(String name) {
        if ((name == null) || (name.isEmpty())) {
            throw new IllegalArgumentException("Name must not be null or empty");
        }

        this.name = name;
    }

    public void applyTimeProgram(TimeProgram timeProgram) {
        if (timeProgram == null) {
            throw new IllegalArgumentException("Time program must not be null");
        }

        this.timeProgram = timeProgram;
    }

    public String getName() {
        return name;
    }

    public TimeProgram getTimeProgram() {
        return timeProgram;
    }

    public void updateOperatingMode(LocalTime timeOfUpdate) {
        if (timeOfUpdate == null) {
            throw new NullPointerException("Point in time must not be null");
        }

        // Check possible cycle switch
        if (nextCycleAhead.isBefore(timeOfUpdate)) {
        }
    }

    private void setup(LocalTime timeOfInitialization) {
        activeCycle = findPreviousCycleInThePast(timeOfInitialization);
        nextCycleAhead = findNextCycleInTheFuture(timeOfInitialization);

    }

    private SwitchCycle findNextCycleInTheFuture(LocalTime timeOfInitialization) {
        SwitchCycle foundCycle = null;

        // Search for first cycle that is after given time
        for (SwitchCycle cycle : timeProgram) {
            if (cycle.isAfter(timeOfInitialization)) {
                foundCycle = cycle;
                break;
            }
        }

        // If not found take earliest cycle (of next day) instead
        if (foundCycle == null) {
            foundCycle = timeProgram.getEarliestSwitchCycle();
        }

        return foundCycle;
    }

    private SwitchCycle findPreviousCycleInThePast(LocalTime timeOfInitialization) {
        SwitchCycle foundCycle = null;

        // Search for first cycle that is before given time
        for (SwitchCycle cycle : timeProgram) {
            if (cycle.isBefore(timeOfInitialization)) {
                foundCycle = cycle;
            }
        }

        // If not found take latest cycle (of next day) instead
        if (foundCycle == null) {
            foundCycle = timeProgram.getLatestSwitchCycle();
        }

        return foundCycle;
    }
}
