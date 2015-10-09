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

    public void defineHVACRoomControllerGroup(String roomControllerGroupName) {
        HVACRoomControllerGroup roomControllerGroup = new HVACRoomControllerGroup(roomControllerGroupName);
        roomControllerRepository.addRoomControllerGroup(roomControllerGroup);
        logger.info("Room controller group '{}' saved to repository", roomControllerGroupName);
    }

    public void defineRoomController(String roomControllerGroupName, String itemName) {
        HVACRoomControllerGroup hvacRoomControllerGroup = roomControllerRepository.findByName(roomControllerGroupName);
        HVACRoomController roomController = new HVACRoomController(itemName, operatingModeChangedListener);
        hvacRoomControllerGroup.connectHVACRoomController(itemName, roomController);
        logger.info("Room controller for item '{}' added to room controller group {}", itemName, roomControllerGroupName);
    }

    public void applyTimeProgram(String roomControllerGroupName, String itemName, String timeProgramId) {
        HVACRoomControllerGroup hvacRoomControllerGroup = roomControllerRepository.findByName(roomControllerGroupName);
        HVACRoomController roomController = hvacRoomControllerGroup.findByItemName(itemName);

        if (roomController == null) {
            throw new NullPointerException("Room controller for item '" + itemName + "' is unknown");
        }

        TimeProgram timeProgram = timeProgramRepository.findById(timeProgramId);

        if (timeProgram == null) {
            throw new NullPointerException("Time program for id '" + timeProgramId + "' is unknown");
        }

        roomController.applyTimeProgram(timeProgram);
        logger.info("Time program '{}' applied to item '{}'", timeProgramId, itemName);
    }

    public void updateOperatingMode(String roomControllerGroupName, LocalTime timeOfUpdate) {
        HVACRoomControllerGroup hvacRoomControllerGroup = roomControllerRepository.findByName(roomControllerGroupName);

        if (hvacRoomControllerGroup == null) {
            throw new NullPointerException("Room controller for id '" + roomControllerGroupName + "' is unknown");
        }

        hvacRoomControllerGroup.updateOperatingMode(timeOfUpdate);
    }
}
