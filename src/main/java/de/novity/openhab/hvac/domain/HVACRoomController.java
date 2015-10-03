package de.novity.openhab.hvac.domain;

import java.time.LocalTime;

public class HVACRoomController {
    private final String name;
    private final TimeProgramCalculator calculator;

    private TimeProgram timeProgram;

    private OperatingMode activeOperatingMode;

    public HVACRoomController(String assignedName, TimeProgram assignedTimeProgram) {
        if ((assignedName == null) || (assignedName.isEmpty())) {
            throw new IllegalArgumentException("Name must not be null or empty");
        }

        this.name = assignedName;
        this.activeOperatingMode = OperatingMode.Undefined;
        this.calculator = new TimeProgramCalculator();

        applyTimeProgram(assignedTimeProgram);
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

    public OperatingMode getActiveOperatingMode() {
        return activeOperatingMode;
    }

    public void updateOperatingMode(LocalTime timeOfUpdate) {
        if (timeOfUpdate == null) {
            throw new NullPointerException("Point in time must not be null");
        }

        OperatingMode nextOperatingMode = calculator.determineOperationgModeByTime(
                timeOfUpdate,
                timeProgram,
                activeOperatingMode);

        publishOperatingMode(nextOperatingMode);
    }

    private void publishOperatingMode(OperatingMode newOperatingMode) {
        if (!newOperatingMode.equals(activeOperatingMode)) {
            System.out.println("Changing operating mode from " + activeOperatingMode + " to " + newOperatingMode);

            activeOperatingMode = newOperatingMode;
        }
    }
}
