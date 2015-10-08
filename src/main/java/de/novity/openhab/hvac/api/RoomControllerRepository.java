package de.novity.openhab.hvac.api;

import de.novity.openhab.hvac.domain.HVACRoomController;
import de.novity.openhab.hvac.domain.TimeProgram;

public interface RoomControllerRepository {
   HVACRoomController findById(String id);

    void addRoomController(HVACRoomController roomController);
    void updateRoomController(HVACRoomController roomController);
}
