package de.novity.openhab.hvac.domain;

import de.novity.openhab.hvac.api.SwitchCycleData;
import de.novity.openhab.hvac.api.TimeProgramRepository;
import de.novity.openhab.hvac.api.TimeProgramService;
import de.novity.openhab.hvac.application.SwitchCycleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeProgramDefaultService implements TimeProgramService {
    private static final Logger logger = LoggerFactory.getLogger(TimeProgramDefaultService.class);

    private final TimeProgramRepository repository;

    public TimeProgramDefaultService(TimeProgramRepository repository) {
        if (repository == null) {
            throw new NullPointerException("Repository must not be null");
        }

        this.repository = repository;
        logger.info("Time program default service created");
    }

    public void defineTimeProgram(String id) {
        TimeProgram timeProgram = new TimeProgram(id);
        repository.addTimeProgram(timeProgram);
        logger.info("Time program '{}' saved to repository", id);
    }

    public void addSwitchCycle(String id, SwitchCycleData cycleData) {
        SwitchCycle cycle = SwitchCycleMapper.mapFromData(cycleData);
        TimeProgram timeProgram = repository.findById(id);
        timeProgram.add(cycle);
    }
}
