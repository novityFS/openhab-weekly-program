package de.novity.openhab.hvac.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public class TimeProgramCalculator {
    public OperatingMode determineOperationModeByDateAndTime(LocalDateTime timeAndDateOfCheck, Set<TimeProgram> timePrograms, OperatingMode lastKnownMode) {
        TimeProgram selectedTimeProgram = selectTimeProgram(timeAndDateOfCheck.toLocalDate(), timePrograms);

        if (selectedTimeProgram != null) {
            return determineOperatingModeByTime(timeAndDateOfCheck.toLocalTime(), selectedTimeProgram.getTimeSchedule(), lastKnownMode);
        } else {
            return lastKnownMode;
        }
    }

    private OperatingMode determineOperatingModeByTime(LocalTime timeOfCheck, TimeSchedule timeSchedule, OperatingMode lastKnownMode) {
        SwitchCycle earliestCycle = timeSchedule.getEarliestSwitchCycle();

        if (timeOfCheck.isBefore(earliestCycle.getPointInTime())) {
            return lastKnownMode;
        }

        SwitchCycle activeSwitchCycle = timeSchedule.getEarliestSwitchCycle();

        for (SwitchCycle cycle : timeSchedule.getCycles()) {
            LocalTime timeToCompare = cycle.getPointInTime();

            if (timeOfCheck.isAfter(timeToCompare) || timeOfCheck.equals(timeToCompare)) {
                activeSwitchCycle = cycle;
            }
        }

        return activeSwitchCycle.getOperatingMode();
    }

    private TimeProgram selectTimeProgram(LocalDate dateOfCheck, Set<TimeProgram> timePrograms) {
        TimeProgram selectedTimeProgram = null;

        for (TimeProgram timeProgram : timePrograms) {
            if (timeProgram.getCalendarSchedule().matchesDate(dateOfCheck)) {
                selectedTimeProgram = timeProgram;
            }
        }

        return selectedTimeProgram;
    }
}
