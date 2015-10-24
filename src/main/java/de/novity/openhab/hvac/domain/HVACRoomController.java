package de.novity.openhab.hvac.domain;

import de.novity.openhab.hvac.api.OperatingModeChangedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class HVACRoomController {
    private static final Logger logger = LoggerFactory.getLogger(HVACRoomController.class);

    private final OperatingModeChangedListener listener;

    private final String itemName;
    private final TimeProgramCalculator calculator;

    private Set<TimeProgram> timePrograms;

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
        this.timePrograms = new HashSet<>();
        this.activeOperatingMode = OperatingMode.Undefined;
        this.calculator = new TimeProgramCalculator();

        logger.info("Room controller for item name '{}' created", itemName);
    }

    public void addTimeProgram(TimeProgram timeProgram) {
        if (timeProgram == null) {
            throw new IllegalArgumentException("Time program must not be null");
        }

        timePrograms.add(timeProgram);
    }

    public String getItemName() {
        return itemName;
    }

    public OperatingMode getActiveOperatingMode() {
        return activeOperatingMode;
    }

    public void updateOperatingMode(LocalDateTime dateAndTimeOfUpdate) {
        if (dateAndTimeOfUpdate == null) {
            throw new NullPointerException("Point in time must not be null");
        }

        OperatingMode nextOperatingMode = calculator.determineOperationModeByDateAndTime(
                dateAndTimeOfUpdate,
                timePrograms,
                activeOperatingMode);

        publishOperatingMode(activeOperatingMode, nextOperatingMode);
    }

    private void publishOperatingMode(OperatingMode oldOperatingMode, OperatingMode newOperatingMode) {
        if (newOperatingMode != oldOperatingMode) {
            activeOperatingMode = newOperatingMode;
            listener.operatingModeChanged(itemName, oldOperatingMode, newOperatingMode);
        }
    }
}
