package de.novity.openhab.hvac.domain;

import de.novity.openhab.hvac.api.OperatingModeChangedListener;
import mockit.Mocked;
import mockit.StrictExpectations;
import org.testng.annotations.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class HVACRoomControllerTest {
    @Mocked
    OperatingModeChangedListener listener;

    @Test
    public void whenNewRoomControllerIsCreatedWithItemNameThenTheRoomControllerHasThatItemName() {
        final String itemName = "Item";
        HVACRoomController roomController = new HVACRoomController(itemName, listener);
        assertThat(roomController.getItemName(), equalTo(itemName));
    }

    @Test
    public void whenNewRoomControllerIsCreatedThenTheOperatingModeIsUndefined() {
        final String itemName = "Item";
        HVACRoomController roomController = new HVACRoomController(itemName, listener);
        assertThat(roomController.getActiveOperatingMode(), equalTo(OperatingMode.Undefined));
    }

    @Test
    public void whenRoomControllerIsCreatedWithMissingTimeProgramAndOperatingModeIsUpdatedThenTheOperatingModeIsStillUndefined() {
        final String itemName = "Item";
        HVACRoomController roomController = new HVACRoomController(itemName, listener);
        roomController.updateOperatingMode(LocalDateTime.parse("2015-01-05T07:00"));

        assertThat(roomController.getActiveOperatingMode(), equalTo(OperatingMode.Undefined));
    }

    @Test
    public void whenRoomControllerIsCreatedAndOperatingModeIsUpdatedThenTheOperatingModeChangedListenerIsNotified() {
        final TimeScheduleBuilder builder = new TimeScheduleBuilder();
        final String itemName = "Item";
        final TimeSchedule timeSchedule = builder.defaultSchedule();

        final Set<DayOfWeek> validDaysOfWeek = new HashSet<>();
        validDaysOfWeek.add(DayOfWeek.MONDAY);
        final CalendarSchedule calendarWeekSchedule = new CalendarWeekSchedule(validDaysOfWeek);

        final TimeProgram timeProgram = new TimeProgram(calendarWeekSchedule, timeSchedule);

        new StrictExpectations() {
            {
                listener.operatingModeChanged(withEqual(itemName), withEqual(OperatingMode.Undefined), withEqual(OperatingMode.Auto));
            }
        };

        HVACRoomController roomController = new HVACRoomController(itemName, listener);
        roomController.addTimeProgram(timeProgram);
        roomController.updateOperatingMode(LocalDateTime.parse("2015-01-05T07:00"));
    }

    @Test
    public void whenRoomControllerIsCreatedAndOperatingModeIsUpdatedThenTheOperatingModeIsNotUndefinedAnymore() {
        final TimeScheduleBuilder builder = new TimeScheduleBuilder();
        final String itemName = "Item";
        final TimeSchedule timeSchedule = builder.defaultSchedule();

        final Set<DayOfWeek> validDaysOfWeek = new HashSet<>();
        validDaysOfWeek.add(DayOfWeek.MONDAY);
        final CalendarSchedule calendarWeekSchedule = new CalendarWeekSchedule(validDaysOfWeek);

        final TimeProgram timeProgram = new TimeProgram(calendarWeekSchedule, timeSchedule);

        HVACRoomController roomController = new HVACRoomController(itemName, listener);
        roomController.addTimeProgram(timeProgram);
        roomController.updateOperatingMode(LocalDateTime.parse("2015-01-05T07:00"));

        assertThat(roomController.getActiveOperatingMode(), not(equalTo(OperatingMode.Undefined)));
    }
}
