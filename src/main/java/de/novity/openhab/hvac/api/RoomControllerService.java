package de.novity.openhab.hvac.api;

import java.time.LocalDateTime;

public interface RoomControllerService {
    public void defineHVACRoomControllerGroup(String roomControllerGroupName);
    public void defineRoomController(String roomControllerGroupName, String itemName);
    public void addTimeProgram(String roomControllerGroupName, String itemName, String timeScheduleId, String ... dayOfWeeks);
    public void updateOperatingMode(String roomControllerGroupName, LocalDateTime dateAndTimeOfUpdate);
}
