package de.novity.openhab.hvac.domain;

import de.novity.openhab.hvac.api.OperatingModeChangedListener;
import de.novity.openhab.hvac.api.RoomControllerRepository;
import de.novity.openhab.hvac.api.RoomControllerService;
import de.novity.openhab.hvac.api.TimeScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class RoomControllerDefaultService implements RoomControllerService {
    private static final Logger logger = LoggerFactory.getLogger(RoomControllerDefaultService.class);

    private final RoomControllerRepository roomControllerRepository;
    private final TimeScheduleRepository timeScheduleRepository;
    private final OperatingModeChangedListener operatingModeChangedListener;

    public RoomControllerDefaultService(RoomControllerRepository roomControllerRepository, TimeScheduleRepository timeScheduleRepository, OperatingModeChangedListener operatingModeChangedListener) {
        if (roomControllerRepository == null) {
            throw new NullPointerException("RoomControllerRepository must not be null");
        }

        if (timeScheduleRepository == null) {
            throw new NullPointerException("TimeProgramRepository must not be null");
        }

        if (operatingModeChangedListener == null) {
            throw new NullPointerException("Operating mode change listener must not be null");
        }

        this.roomControllerRepository = roomControllerRepository;
        this.timeScheduleRepository = timeScheduleRepository;
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

    public void addTimeProgram(String roomControllerGroupName, String itemName, String timeScheduleId, String ... dayOfWeeks) {
        HVACRoomControllerGroup hvacRoomControllerGroup = roomControllerRepository.findByName(roomControllerGroupName);
        HVACRoomController roomController = hvacRoomControllerGroup.findByItemName(itemName);

        if (roomController == null) {
            throw new NullPointerException("Room controller for item '" + itemName + "' is unknown");
        }

        TimeSchedule timeSchedule = timeScheduleRepository.findById(timeScheduleId);

        if (timeSchedule == null) {
            throw new NullPointerException("Time schedule for id '" + timeScheduleId + "' is unknown");
        }

        Set<DayOfWeek> validDaysOfWeek = new HashSet<>();

        for (String dayOfWeek : dayOfWeeks) {
            validDaysOfWeek.add(DayOfWeek.valueOf(dayOfWeek));
        }

        CalendarWeekSchedule calendarDayOfWeek = new CalendarWeekSchedule(validDaysOfWeek);
        TimeProgram timeProgram = new TimeProgram(calendarDayOfWeek, timeSchedule);
        roomController.addTimeProgram(timeProgram);
        logger.info("Time schedule '{}' applied to item '{}'", timeScheduleId, itemName);
    }

    public void updateOperatingMode(String roomControllerGroupName, LocalDateTime dateAndTimeOfUpdate) {
        HVACRoomControllerGroup hvacRoomControllerGroup = roomControllerRepository.findByName(roomControllerGroupName);

        if (hvacRoomControllerGroup == null) {
            throw new NullPointerException("Room controller for id '" + roomControllerGroupName + "' is unknown");
        }

        hvacRoomControllerGroup.updateOperatingMode(dateAndTimeOfUpdate);
    }
}
