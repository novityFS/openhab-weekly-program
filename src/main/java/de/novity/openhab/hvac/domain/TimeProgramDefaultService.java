package de.novity.openhab.hvac.domain;

import de.novity.openhab.hvac.api.SwitchCycleData;
import de.novity.openhab.hvac.api.TimeProgramService;
import de.novity.openhab.hvac.api.TimeScheduleRepository;
import de.novity.openhab.hvac.application.SwitchCycleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeProgramDefaultService implements TimeProgramService {
    private static final Logger logger = LoggerFactory.getLogger(TimeProgramDefaultService.class);

    private final TimeScheduleRepository repository;

    public TimeProgramDefaultService(TimeScheduleRepository repository) {
        if (repository == null) {
            throw new NullPointerException("Repository must not be null");
        }

        this.repository = repository;
        logger.info("Time program default service created");
    }

    public void defineTimeSchedule(String id) {
        TimeSchedule timeSchedule = new TimeSchedule(id);
        repository.addTimeSchedule(timeSchedule);
        logger.info("Time schedule '{}' saved to repository", id);
    }

    public void addSwitchCycle(String id, SwitchCycleData cycleData) {
        SwitchCycle cycle = SwitchCycleMapper.mapFromData(cycleData);
        TimeSchedule timeSchedule = repository.findById(id);
        timeSchedule.add(cycle);
    }
}
