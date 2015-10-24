package de.novity.openhab.hvac.domain;

import de.novity.openhab.hvac.api.SwitchCycleData;
import de.novity.openhab.hvac.api.TimeProgramService;
import de.novity.openhab.hvac.api.TimeScheduleRepository;
import de.novity.openhab.hvac.application.SwitchCycleMapper;
import mockit.Mocked;
import mockit.StrictExpectations;
import org.testng.annotations.Test;

import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TimeScheduleDefaultServiceTest {
    @Mocked
    TimeScheduleRepository repository;

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

        service.defineTimeSchedule(null);
    }

    @Test
    public void whenATimeProgramIsDefinedWithValidParametersThenTheTimeProgramIsAddedToTheRepository() {
        TimeProgramService service = new TimeProgramDefaultService(repository);

        new StrictExpectations() {{
                repository.addTimeSchedule((TimeSchedule) any);
            }
        };

        service.defineTimeSchedule("My first program");
    }

    @Test
    public void whenASwitchCycleIsAddedToATimeProgramThenTheTimeProgramHasThatSwitchCycle() {
        TimeProgramService service = new TimeProgramDefaultService(repository);
        final String id = "My first program";
        final TimeSchedule timeSchedule = new TimeSchedule(id);

        service.defineTimeSchedule(id);

        new StrictExpectations() {{
                repository.findById(id); result = timeSchedule;
            }
        };

        final SwitchCycleData switchCycleData = new SwitchCycleData();
        switchCycleData.switchTime = LocalTime.parse("06:00");
        switchCycleData.operatingMode = OperatingMode.Auto.getValue();

        final SwitchCycle switchCycle = SwitchCycleMapper.mapFromData(switchCycleData);

        service.addSwitchCycle(id, switchCycleData);
        assertThat(timeSchedule.contains(switchCycle), is(true));
    }
}
