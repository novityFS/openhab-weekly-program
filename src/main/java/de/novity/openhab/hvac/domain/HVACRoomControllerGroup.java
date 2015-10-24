package de.novity.openhab.hvac.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class HVACRoomControllerGroup {
    private final String name;
    private final Map<String, HVACRoomController> hvacController;

    public HVACRoomControllerGroup(String name) {
        if ((name == null) || (name.isEmpty())) {
            throw new IllegalArgumentException("Name of HVAC room controller group must not be null or empty");
        }

        this.name = name;
        hvacController = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void connectHVACRoomController(String itemName, HVACRoomController roomController) {
        if ((itemName == null) || (itemName.isEmpty())){
            throw new IllegalArgumentException("item name must not be null or empty");
        }

        if (roomController == null) {
            throw new NullPointerException("HVAC room controller must not be null");
        }

        hvacController.put(itemName, roomController);
    }

    public HVACRoomController findByItemName(String itemName) {
        if ((itemName == null) || (itemName.isEmpty())){
            throw new IllegalArgumentException("item name must not be null or empty");
        }

        return hvacController.get(itemName);
    }

    public void updateOperatingMode(LocalDateTime dateAndTimeOfUpdate) {
        for (HVACRoomController roomController : hvacController.values()) {
            roomController.updateOperatingMode(dateAndTimeOfUpdate);
        }
    }
}
