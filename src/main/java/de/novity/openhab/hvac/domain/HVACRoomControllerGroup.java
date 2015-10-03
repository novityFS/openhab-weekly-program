package de.novity.openhab.hvac.domain;

import java.util.Collection;

public class HVACRoomControllerGroup {
    private final Collection<HVACRoomController> hvacController;

    public HVACRoomControllerGroup(Collection<HVACRoomController> hvacController) {
        this.hvacController = hvacController;
    }
}
