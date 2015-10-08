package de.novity.openhab.hvac.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;

public class HVACRoomController {
    private static final Logger logger = LoggerFactory.getLogger(HVACRoomController.class);

    private final String name;
    private final String itemName;
    private final TimeProgramCalculator calculator;

    private TimeProgram timeProgram;

    private OperatingMode activeOperatingMode;

    public HVACRoomController(String name, String itemName) {
        if ((name == null) || (name.isEmpty())) {
            throw new IllegalArgumentException("The name of this room controller must not be null or empty");
        }

        if ((name == null) || (name.isEmpty())) {
            throw new IllegalArgumentException("The item name must not be null or empty");
        }

        this.name = name;
        this.itemName = itemName;
        this.timeProgram = null;
        this.activeOperatingMode = OperatingMode.Undefined;
        this.calculator = new TimeProgramCalculator();

        logger.info("Room controller '{}' created", name);
    }

    public void applyTimeProgram(TimeProgram timeProgram) {
        if (timeProgram == null) {
            throw new IllegalArgumentException("Time program must not be null");
        }

        this.timeProgram = timeProgram;
        logger.info("Time program '{}' to room controller '{}' applied", timeProgram.getId(), name);
    }

    public String getName() {
        return name;
    }

    public String getItemName() {
        return itemName;
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

        if (timeProgram != null) {
            OperatingMode nextOperatingMode = calculator.determineOperationgModeByTime(
                    timeOfUpdate,
                    timeProgram,
                    activeOperatingMode);

            publishOperatingMode(nextOperatingMode);
        }
    }

    private void publishOperatingMode(OperatingMode newOperatingMode) {
        logger.trace("Checking operating mode on room controller '{}'", name);
        if (!newOperatingMode.equals(activeOperatingMode)) {
            logger.info("Operating mode changed from {} to {} on room controller '{}'", activeOperatingMode, newOperatingMode, name);
            activeOperatingMode = newOperatingMode;
        }
    }
}
