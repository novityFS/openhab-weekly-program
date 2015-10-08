package de.novity.openhab.hvac.domain;

import de.novity.openhab.hvac.api.OperatingModeChangedListener;
import de.novity.openhab.hvac.api.RoomControllerRepository;
import de.novity.openhab.hvac.api.RoomControllerService;
import de.novity.openhab.hvac.api.TimeProgramRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;

public class RoomControllerDefaultService implements RoomControllerService {
    private static final Logger logger = LoggerFactory.getLogger(RoomControllerDefaultService.class);

    private final RoomControllerRepository roomControllerRepository;
    private final TimeProgramRepository timeProgramRepository;
    private final OperatingModeChangedListener operatingModeChangedListener;

    public RoomControllerDefaultService(RoomControllerRepository roomControllerRepository, TimeProgramRepository timeProgramRepository, OperatingModeChangedListener operatingModeChangedListener) {
        if (roomControllerRepository == null) {
            throw new NullPointerException("RoomControllerRepository must not be null");
        }

        if (timeProgramRepository == null) {
            throw new NullPointerException("TimeProgramRepository must not be null");
        }

        if (operatingModeChangedListener == null) {
            throw new NullPointerException("Operating mode change listener must not be null");
        }

        this.roomControllerRepository = roomControllerRepository;
        this.timeProgramRepository = timeProgramRepository;
        this.operatingModeChangedListener = operatingModeChangedListener;
        logger.info("Room controller default service created");
    }

    public void defineRoomController(String name, String itemName) {
        HVACRoomController roomController = new HVACRoomController(name, itemName);
        roomControllerRepository.addRoomController(roomController);
        logger.info("Room controller '{}' for item '{}' saved to repository", name, itemName);
    }

    public void applyTimeProgram(String roomControllerName, String timeProgramId) {
        if ((roomControllerName == null) || (roomControllerName.isEmpty())){
            throw new IllegalArgumentException("Room controller name must not be null or empty");
        }

        if ((timeProgramId == null) || (timeProgramId.isEmpty())){
            throw new IllegalArgumentException("Time program id must not be null or empty");
        }

        HVACRoomController roomController = roomControllerRepository.findById(roomControllerName);

        if (roomController == null) {
            throw new NullPointerException("Room controller for id '" + roomControllerName + "' is unknown");
        }

        TimeProgram timeProgram = timeProgramRepository.findById(timeProgramId);

        if (timeProgram == null) {
            throw new NullPointerException("Time program for id '" + roomControllerName + "' is unknown");
        }

        roomController.applyTimeProgram(timeProgram);
        roomControllerRepository.updateRoomController(roomController);
        logger.info("Room controller '{}' saved to repository", roomControllerName);
    }

    public void updateOperatingMode(String roomControllerName, LocalTime timeOfUpdate) {
        if ((roomControllerName == null) || (roomControllerName.isEmpty())){
            throw new IllegalArgumentException("Room controller id must not be null or empty");
        }

        HVACRoomController roomController = roomControllerRepository.findById(roomControllerName);

        if (roomController == null) {
            throw new NullPointerException("Room controller for id '" + roomControllerName + "' is unknown");
        }

        OperatingMode oldMode = roomController.getActiveOperatingMode();
        roomController.updateOperatingMode(timeOfUpdate);
        OperatingMode newMode = roomController.getActiveOperatingMode();

        if (oldMode.equals(newMode) == false) {
            operatingModeChangedListener.operatingModeChanged(roomController.getItemName(), oldMode, newMode);
        }
    }
}
