package de.novity.openhab.hvac.domain;

import de.novity.openhab.hvac.api.OperatingModeChangedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;

public class HVACRoomController {
    private static final Logger logger = LoggerFactory.getLogger(HVACRoomController.class);

    private final OperatingModeChangedListener listener;

    private final String itemName;
    private final TimeProgramCalculator calculator;

    private TimeProgram timeProgram;

    private OperatingMode activeOperatingMode;

    public HVACRoomController(String itemName, OperatingModeChangedListener listener) {
        if ((itemName == null) || (itemName.isEmpty())) {
            throw new IllegalArgumentException("The item name must not be null or empty");
        }

        if (listener == null) {
            throw new NullPointerException("Listener must not be null");
        }

        this.listener = listener;
        this.itemName = itemName;
        this.timeProgram = null;
        this.activeOperatingMode = OperatingMode.Undefined;
        this.calculator = new TimeProgramCalculator();

        logger.info("Room controller for item name '{}' created", itemName);
    }

    public void applyTimeProgram(TimeProgram timeProgram) {
        if (timeProgram == null) {
            throw new IllegalArgumentException("Time program must not be null");
        }

        this.timeProgram = timeProgram;
        logger.info("Time program '{}' for item name '{}' applied", timeProgram.getId(), itemName);
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
        logger.trace("Checking operating mode on item '{}'", itemName);
        if (!newOperatingMode.equals(activeOperatingMode)) {
            OperatingMode oldOperatingMode = activeOperatingMode;
            activeOperatingMode = newOperatingMode;

            listener.operatingModeChanged(itemName, oldOperatingMode, newOperatingMode);
        }
    }
}
