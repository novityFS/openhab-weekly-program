package de.novity.openhab.hvac.api;

import java.time.LocalTime;

public interface RoomControllerService {
    void defineRoomController(String name, String itemName);
    void applyTimeProgram(String roomControllerName, String timeProgramId);
    void updateOperatingMode(String roomControllerName, LocalTime timeOfUpdate);
}
