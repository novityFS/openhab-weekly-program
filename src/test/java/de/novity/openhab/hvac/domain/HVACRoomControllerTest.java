package de.novity.openhab.hvac.domain;

import org.testng.annotations.Test;

import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class HVACRoomControllerTest {
    @Test
    public void whenNewRoomControllerIsCreatedWithNameAndItemNameThenTheRoomControllerHasThatNameAndItemName() {
        final String roomControllerName = "A";
        final String itemName = "Item";
        HVACRoomController roomController = new HVACRoomController(roomControllerName, itemName);
        assertThat(roomController.getName(), equalTo(roomControllerName));
        assertThat(roomController.getItemName(), equalTo(itemName));
    }

    @Test
    public void whenNewRoomControllerIsCreatedThenTheOperatingModeIsUndefined() {
        final String roomControllerName = "A";
        final String itemName = "Item";
        HVACRoomController roomController = new HVACRoomController(roomControllerName, itemName);
        assertThat(roomController.getActiveOperatingMode(), equalTo(OperatingMode.Undefined));
    }

    @Test
    public void whenRoomControllerIsCreatedWithMissingTimeProgramAndOperatingModeIsUpdatedThenTheOperatingModeIsStillUndefined() {
        final String roomControllerName = "A";
        final String itemName = "Item";

        HVACRoomController roomController = new HVACRoomController(roomControllerName, itemName);
        roomController.updateOperatingMode(LocalTime.parse("07:00"));

        assertThat(roomController.getActiveOperatingMode(), equalTo(OperatingMode.Undefined));
    }

    @Test
    public void whenRoomControllerIsCreatedAndOperatingModeIsUpdatedThenTheOperatingModeIsNotUndefinedAnymore() {
        TimeProgramBuilder builder = new TimeProgramBuilder();

        final String roomControllerName = "A";
        final String itemName = "Item";
        final TimeProgram timeProgram = builder.defaultProgram();

        HVACRoomController roomController = new HVACRoomController(roomControllerName, itemName);
        roomController.applyTimeProgram(timeProgram);
        roomController.updateOperatingMode(LocalTime.parse("07:00"));

        assertThat(roomController.getActiveOperatingMode(), not(equalTo(OperatingMode.Undefined)));
    }
}
