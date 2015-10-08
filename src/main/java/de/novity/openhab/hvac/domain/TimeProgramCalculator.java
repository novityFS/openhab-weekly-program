package de.novity.openhab.hvac.domain;

import java.time.LocalTime;

public class TimeProgramCalculator {
    public OperatingMode determineOperationgModeByTime(LocalTime timeOfCheck, TimeProgram timeProgram, OperatingMode lastKnownMode) {
        SwitchCycle earliestCycle = timeProgram.getEarliestSwitchCycle();

        if (timeOfCheck.isBefore(earliestCycle.getPointInTime())) {
            return lastKnownMode;
        }

        SwitchCycle activeSwitchCycle = timeProgram.getEarliestSwitchCycle();

        for (SwitchCycle cycle : timeProgram.getCycles()) {
            LocalTime timeToCompare = cycle.getPointInTime();

            if (timeOfCheck.isAfter(timeToCompare) || timeOfCheck.equals(timeToCompare)) {
                activeSwitchCycle = cycle;
            }
        }

        return activeSwitchCycle.getOperatingMode();
    }
}
