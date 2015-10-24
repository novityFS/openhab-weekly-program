package de.novity.openhab.hvac.api;

import de.novity.openhab.hvac.domain.TimeSchedule;

public interface TimeScheduleRepository {
    TimeSchedule findById(String id);
    void addTimeSchedule(TimeSchedule timeSchedule);
}
