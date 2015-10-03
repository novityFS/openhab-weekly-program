package de.novity.openhab.hvac.domain;

import org.hamcrest.BaseMatcher;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.Test;

import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class HVACRoomControllerTest {
    @Test
    public void whenNewRoomControllerIsCreatedWithNameThenTheRoomControllerHasThatName() {
        TimeProgramBuilder builder = new TimeProgramBuilder();

        final String roomControllerName = "A";
        final TimeProgram timeProgram = builder.defaultProgram();

        HVACRoomController roomController = new HVACRoomController(roomControllerName, timeProgram);

        assertThat(roomController.getName(), equalTo(roomControllerName));
    }

    @Test
    public void whenNewRoomControllerIsCreatedThenTheOperatingModeIsUndefined() {
        TimeProgramBuilder builder = new TimeProgramBuilder();

        final String roomControllerName = "A";
        final TimeProgram timeProgram = builder.defaultProgram();

        HVACRoomController roomController = new HVACRoomController(roomControllerName, timeProgram);

        assertThat(roomController.getActiveOperatingMode(), equalTo(OperatingMode.Undefined));
    }

    @Test
    public void whenRoomControllerIsCreatedAndOperatingModeIsUpdatedThenTheOperatingModeIsNotUndefinedAnymore() {
        TimeProgramBuilder builder = new TimeProgramBuilder();

        final String roomControllerName = "A";
        final TimeProgram timeProgram = builder.defaultProgram();

        HVACRoomController roomController = new HVACRoomController(roomControllerName, timeProgram);
        roomController.updateOperatingMode(LocalTime.parse("07:00"));

        assertThat(roomController.getActiveOperatingMode(), not(equalTo(OperatingMode.Undefined)));
    }
}
