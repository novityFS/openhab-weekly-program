package de.novity.openhab.hvac.domain;

import java.util.Collection;

/**
 * Created by fs on 02.10.2015.
 */
public class TimeProgramChangedEvent {
    private final HVACRoomController roomController;

    public TimeProgramChangedEvent(HVACRoomController roomController) {
        if (roomController == null) {
            throw new IllegalArgumentException("Rommcontroller must not be null");
        }

        this.roomController = roomController;
    }
}
