package de.novity.openhab.hvac.api;

import java.time.LocalTime;

public interface RoomControllerService {
    public void defineHVACRoomControllerGroup(String roomControllerGroupName);
    public void defineRoomController(String roomControllerGroupName, String itemName);
    public void applyTimeProgram(String roomControllerGroupName, String itemName, String timeProgramId);
    public void updateOperatingMode(String roomControllerGroupName, LocalTime timeOfUpdate);
}
