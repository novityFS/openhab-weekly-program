package de.novity.openhab.hvac.domain;

import org.testng.annotations.Test;

import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TimeScheduleBuilderTest {
    @Test
    public void whenTheDefaultTimeProgramIsCreatedThenTimeProgramContainsOneSwitchCycle() {
        TimeScheduleBuilder builder = new TimeScheduleBuilder();

        final TimeSchedule defaultProgram = builder.defaultSchedule();
        assertThat(defaultProgram.size(), equalTo(1));
    }

    @Test
    public void whenTheDefaultTimeProgramIsCreatedThenTimeProgramContainsSwitchCycleToAutomaticAtMidnight() {
        TimeScheduleBuilder builder = new TimeScheduleBuilder();
        final SwitchCycle switchCycle = new SwitchCycle(LocalTime.parse("00:00"), OperatingMode.Auto);

        final TimeSchedule defaultProgram = builder.defaultSchedule();
        assertThat(defaultProgram.contains(switchCycle), equalTo(true));
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class
    )
    public void whenTheBuilderIsUsedWithoutANameThenAnExceptionIsThrown() {
        TimeScheduleBuilder builder = new TimeScheduleBuilder();

        TimeSchedule timeSchedule = builder
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("00:00"), OperatingMode.Auto))
                .build();

        assertThat(timeSchedule.size(), equalTo(1));
    }

    @Test
    public void whenTheBuilderAddsASwitchCycleToTheTimeProgramThenTheTimeProgramContainsOneSwitchCycle() {
        TimeScheduleBuilder builder = new TimeScheduleBuilder();

        TimeSchedule timeSchedule = builder
                .withName("Default")
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("00:00"), OperatingMode.Auto))
                .build();

        assertThat(timeSchedule.size(), equalTo(1));
    }

    @Test
    public void whenTheBuilderAddsASwitchCycleToTheTimeProgramThenTheTimeProgramContainsThatSwitchCycle() {
        TimeScheduleBuilder builder = new TimeScheduleBuilder();
        final SwitchCycle switchCycle = new SwitchCycle(LocalTime.parse("00:00"), OperatingMode.Auto);

        TimeSchedule timeSchedule = builder
                .withName("Default")
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("00:00"), OperatingMode.Auto))
                .build();

        assertThat(timeSchedule.contains(switchCycle), equalTo(true));
    }
}
