package de.novity.openhab.hvac.api;

import de.novity.openhab.hvac.domain.TimeProgram;

public interface TimeProgramRepository {
    TimeProgram findById(String id);

    void addTimeProgram(TimeProgram timeProgram);
    void updateTimeProgram(TimeProgram timeProgram);
}
