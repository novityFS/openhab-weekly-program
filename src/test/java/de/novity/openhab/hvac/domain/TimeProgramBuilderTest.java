package de.novity.openhab.hvac.domain;

import org.testng.annotations.Test;

import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TimeProgramBuilderTest {
    @Test
    public void whenTheDefaultTimeProgramIsCreatedThenTimeProgramContainsOneSwitchCycle() {
        TimeProgramBuilder builder = new TimeProgramBuilder();

        final TimeProgram defaultProgram = builder.defaultProgram();
        assertThat(defaultProgram.size(), equalTo(1));
    }

    @Test
    public void whenTheDefaultTimeProgramIsCreatedThenTimeProgramContainsSwitchCycleToAutomaticAtMidnight() {
        TimeProgramBuilder builder = new TimeProgramBuilder();
        final SwitchCycle switchCycle = new SwitchCycle(LocalTime.parse("00:00"), OperatingMode.Auto);

        final TimeProgram defaultProgram = builder.defaultProgram();
        assertThat(defaultProgram.contains(switchCycle), equalTo(true));
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class
    )
    public void whenTheBuilderIsUsedWithoutANameThenAnExceptionIsThrown() {
        TimeProgramBuilder builder = new TimeProgramBuilder();

        TimeProgram timeProgram = builder
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("00:00"), OperatingMode.Auto))
                .build();

        assertThat(timeProgram.size(), equalTo(1));
    }

    @Test
    public void whenTheBuilderAddsASwitchCycleToTheTimeProgramThenTheTimeProgramContainsOneSwitchCycle() {
        TimeProgramBuilder builder = new TimeProgramBuilder();

        TimeProgram timeProgram = builder
                .withName("Default")
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("00:00"), OperatingMode.Auto))
                .build();

        assertThat(timeProgram.size(), equalTo(1));
    }

    @Test
    public void whenTheBuilderAddsASwitchCycleToTheTimeProgramThenTheTimeProgramContainsThatSwitchCycle() {
        TimeProgramBuilder builder = new TimeProgramBuilder();
        final SwitchCycle switchCycle = new SwitchCycle(LocalTime.parse("00:00"), OperatingMode.Auto);

        TimeProgram timeProgram = builder
                .withName("Default")
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("00:00"), OperatingMode.Auto))
                .build();

        assertThat(timeProgram.contains(switchCycle), equalTo(true));
    }
}
