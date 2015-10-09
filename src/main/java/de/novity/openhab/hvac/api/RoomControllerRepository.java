package de.novity.openhab.hvac.api;

import de.novity.openhab.hvac.domain.HVACRoomControllerGroup;

public interface RoomControllerRepository {
    HVACRoomControllerGroup findByName(String name);
    void addRoomControllerGroup(HVACRoomControllerGroup roomControllerGroup);
}
