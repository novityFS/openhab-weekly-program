package de.novity.openhab.hvac.domain;

import de.novity.openhab.hvac.api.SwitchCycleData;
import de.novity.openhab.hvac.api.TimeProgramRepository;
import de.novity.openhab.hvac.api.TimeProgramService;
import de.novity.openhab.hvac.application.SwitchCycleMapper;
import mockit.Mocked;
import mockit.StrictExpectations;
import org.testng.annotations.Test;

import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TimeProgramDefaultServiceTest {
    @Mocked
    TimeProgramRepository repository;

    @Test(
            expectedExceptions = NullPointerException.class
    )
    public void whenTheTimeProgramDefaultServiceIsCreatedWithoutAValidRepositoryThenAnExceptionIsThrown() {
        new TimeProgramDefaultService(null);
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class
    )
    public void whenATimeProgramIsDefinedWithoutIdentifierThenAnExceptionIsThrown() {
        TimeProgramService service = new TimeProgramDefaultService(repository);

        service.defineTimeProgram(null);
    }

    @Test
    public void whenATimeProgramIsDefinedWithValidParametersThenTheTimeProgramIsAddedToTheRepository() {
        TimeProgramService service = new TimeProgramDefaultService(repository);

        new StrictExpectations() {{
                repository.addTimeProgram((TimeProgram) any);
            }
        };

        service.defineTimeProgram("My first program");
    }

    @Test
    public void whenASwitchCycleIsAddedToATimeProgramThenTheTimeProgramIsUpdatedInTheRepository() {
        TimeProgramService service = new TimeProgramDefaultService(repository);
        final String id = "My first program";
        final TimeProgram timeProgram = new TimeProgram(id);

        service.defineTimeProgram(id);

        new StrictExpectations() {{
                repository.findById(id); result = timeProgram;
                repository.updateTimeProgram(timeProgram);
            }
        };

        final SwitchCycleData switchCycleData = new SwitchCycleData();
        switchCycleData.switchTime = LocalTime.parse("06:00");
        switchCycleData.operatingMode = OperatingMode.Auto.getValue();

        final SwitchCycle switchCycle = SwitchCycleMapper.mapFromData(switchCycleData);

        service.addSwitchCycle(id, switchCycleData);
        assertThat(timeProgram.contains(switchCycle), is(true));
    }
}
