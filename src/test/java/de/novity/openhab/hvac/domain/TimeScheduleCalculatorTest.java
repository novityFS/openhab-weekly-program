package de.novity.openhab.hvac.domain;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TimeScheduleCalculatorTest {
    private TimeProgramCalculator timeProgramCalculator;

    @BeforeTest
    public void setup() {
        timeProgramCalculator = new TimeProgramCalculator();
    }

    @Test
    public void whenTimeIsBeforeEarliestSwitchCycleThenTheLastKnownValueIsReturned() {
        final OperatingMode expectedOperatingMode = OperatingMode.Standby;

        TimeSchedule timeSchedule = new TimeScheduleBuilder()
                .withName("Default")
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("08:30"), OperatingMode.Comfort))
                .build();

        Set<DayOfWeek> validDaysOfWeek = new HashSet<>();
        validDaysOfWeek.add(DayOfWeek.MONDAY);
        CalendarSchedule calendarWeekSchedule = new CalendarWeekSchedule(validDaysOfWeek);

        Set<TimeProgram> timePrograms = new HashSet<>();
        timePrograms.add(new TimeProgram(calendarWeekSchedule, timeSchedule));

        final OperatingMode nextOperatingMode = timeProgramCalculator.determineOperationModeByDateAndTime(
                LocalDateTime.parse("2015-01-05T07:00"),
                timePrograms,
                expectedOperatingMode);

        assertThat(nextOperatingMode, equalTo(expectedOperatingMode));
    }

    @Test
    public void whenTimeIsEqualToASwitchCycleThenOperatingModeOfThatSwitchCycleIsReturned() {
        final OperatingMode lastKnownOperatingMode = OperatingMode.Standby;
        final OperatingMode expectedOperatingMode = OperatingMode.Comfort;

        TimeSchedule timeSchedule = new TimeScheduleBuilder()
                .withName("Default")
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("08:30"), expectedOperatingMode))
                .build();

        Set<DayOfWeek> validDaysOfWeek = new HashSet<>();
        validDaysOfWeek.add(DayOfWeek.MONDAY);
        CalendarSchedule calendarWeekSchedule = new CalendarWeekSchedule(validDaysOfWeek);

        Set<TimeProgram> timePrograms = new HashSet<>();
        timePrograms.add(new TimeProgram(calendarWeekSchedule, timeSchedule));

        final OperatingMode nextOperatingMode = timeProgramCalculator.determineOperationModeByDateAndTime(
                LocalDateTime.parse("2015-01-05T08:30"),
                timePrograms,
                expectedOperatingMode);

        assertThat(nextOperatingMode, equalTo(expectedOperatingMode));
    }

    @Test
    public void whenTimeIsBetweenFirstAndSecondSwitchCycleThenTheOperatingModeOfFirstSwitchCycleIsReturned() {
        final OperatingMode lastKnownOperatingMode = OperatingMode.Standby;
        final OperatingMode expectedOperatingMode = OperatingMode.Comfort;

        TimeSchedule timeSchedule = new TimeScheduleBuilder()
                .withName("Default")
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("08:30"), expectedOperatingMode))
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("09:30"), OperatingMode.Auto))
                .build();

        Set<DayOfWeek> validDaysOfWeek = new HashSet<>();
        validDaysOfWeek.add(DayOfWeek.MONDAY);
        CalendarSchedule calendarWeekSchedule = new CalendarWeekSchedule(validDaysOfWeek);

        Set<TimeProgram> timePrograms = new HashSet<>();
        timePrograms.add(new TimeProgram(calendarWeekSchedule, timeSchedule));

        final OperatingMode nextOperatingMode = timeProgramCalculator.determineOperationModeByDateAndTime(
                LocalDateTime.parse("2015-01-05T09:00"),
                timePrograms,
                expectedOperatingMode);

        assertThat(nextOperatingMode, equalTo(expectedOperatingMode));
    }

    @Test
    public void whenTimeIsAfterLatestSwitchCycleThenTheOperatingModeOfLatestSwitchCycleIsReturned() {
        final OperatingMode lastKnownOperatingMode = OperatingMode.BuildingProtection;
        final OperatingMode expectedOperatingMode = OperatingMode.Auto;

        TimeSchedule timeSchedule = new TimeScheduleBuilder()
                .withName("Default")
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("08:30"), expectedOperatingMode))
                .build();

        Set<DayOfWeek> validDaysOfWeek = new HashSet<>();
        validDaysOfWeek.add(DayOfWeek.MONDAY);
        CalendarSchedule calendarWeekSchedule = new CalendarWeekSchedule(validDaysOfWeek);

        Set<TimeProgram> timePrograms = new HashSet<>();
        timePrograms.add(new TimeProgram(calendarWeekSchedule, timeSchedule));

        final OperatingMode nextOperatingMode = timeProgramCalculator.determineOperationModeByDateAndTime(
                LocalDateTime.parse("2015-01-05T09:00"),
                timePrograms,
                expectedOperatingMode);

        assertThat(nextOperatingMode, equalTo(expectedOperatingMode));
    }

    @Test
    public void whenAWeekendProgramIsDefinedThenThatProgramIsTakenIntoAccount() {
        final OperatingMode lastKnownOperatingMode = OperatingMode.BuildingProtection;
        final OperatingMode expectedOperatingMode = OperatingMode.Comfort;

        final TimeSchedule defaultTimeSchedule = new TimeScheduleBuilder()
                .withName("Default")
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("06:30"), OperatingMode.Comfort))
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("10:30"), OperatingMode.Standby))
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("16:30"), OperatingMode.Comfort))
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("21:30"), OperatingMode.Economy))
                .build();

        final Set<DayOfWeek> workingDays = new HashSet<>();
        workingDays.add(DayOfWeek.MONDAY);
        workingDays.add(DayOfWeek.TUESDAY);
        workingDays.add(DayOfWeek.WEDNESDAY);
        workingDays.add(DayOfWeek.THURSDAY);
        workingDays.add(DayOfWeek.FRIDAY);
        CalendarSchedule workingDaySchedule = new CalendarWeekSchedule(workingDays);

        final TimeSchedule weekendTimeSchedule = new TimeScheduleBuilder()
                .withName("Weekend")
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("08:30"), OperatingMode.Comfort))
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("21:30"), OperatingMode.Economy))
                .build();


        final Set<DayOfWeek> weekendDays = new HashSet<>();
        weekendDays.add(DayOfWeek.SATURDAY);
        weekendDays.add(DayOfWeek.SUNDAY);
        CalendarSchedule weekendDaySchedule = new CalendarWeekSchedule(weekendDays);

        Set<TimeProgram> timePrograms = new HashSet<>();
        timePrograms.add(new TimeProgram(workingDaySchedule, defaultTimeSchedule));
        timePrograms.add(new TimeProgram(weekendDaySchedule, weekendTimeSchedule));

        final OperatingMode nextOperatingMode = timeProgramCalculator.determineOperationModeByDateAndTime(
                LocalDateTime.parse("2015-01-04T19:36"),
                timePrograms,
                expectedOperatingMode);

        assertThat(nextOperatingMode, equalTo(expectedOperatingMode));
    }
}
