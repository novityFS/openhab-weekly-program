package de.novity.openhab.hvac.domain;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TimeProgramCalculatorTest {
    private TimeProgramCalculator timeProgramCalculator;

    @BeforeTest
    public void setup() {
        timeProgramCalculator = new TimeProgramCalculator();
    }

    @Test
    public void whenTimeIsBeforeEarliestSwitchCylcleThenTheLastKnownValueIsReturned() {
        final OperatingMode expectedOperatingMode = OperatingMode.Standby;

        TimeProgram timeProgram = new TimeProgramBuilder()
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("08:30"), OperatingMode.Comfort))
                .build();

        final OperatingMode nextOperatingMode = timeProgramCalculator.determineOperationgModeByTime(
                LocalTime.parse("07:00"),
                timeProgram,
                expectedOperatingMode);

        assertThat(nextOperatingMode, equalTo(expectedOperatingMode));
    }

    @Test
    public void whenTimeIsEqualToASwitchCylcleThenOperatingModeOfThatSwitchCycleIsReturned() {
        final OperatingMode lastKnownOperatingMode = OperatingMode.Standby;
        final OperatingMode expectedOperatingMode = OperatingMode.Comfort;

        TimeProgram timeProgram = new TimeProgramBuilder()
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("08:30"), expectedOperatingMode))
                .build();

        final OperatingMode nextOperatingMode = timeProgramCalculator.determineOperationgModeByTime(
                LocalTime.parse("08:30"),
                timeProgram,
                lastKnownOperatingMode);

        assertThat(nextOperatingMode, equalTo(expectedOperatingMode));
    }

    @Test
    public void whenTimeIsBetweenFirstAndSecondSwitchCylcleThenTheOperatingModeOfFirstSwitchCycleIsReturned() {
        final OperatingMode lastKnownOperatingMode = OperatingMode.Standby;
        final OperatingMode expectedOperatingMode = OperatingMode.Comfort;

        TimeProgram timeProgram = new TimeProgramBuilder()
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("08:30"), expectedOperatingMode))
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("09:30"), OperatingMode.Auto))
                .build();

        final OperatingMode nextOperatingMode = timeProgramCalculator.determineOperationgModeByTime(
                LocalTime.parse("09:00"),
                timeProgram,
                lastKnownOperatingMode);

        assertThat(nextOperatingMode, equalTo(expectedOperatingMode));
    }

    @Test
    public void whenTimeIsAfterLatestSwitchCylcleThenTheOperatingModeOfLatestSwitchCycleIsReturned() {
        final OperatingMode lastKnownOperatingMode = OperatingMode.BuildingProtection;
        final OperatingMode expectedOperatingMode = OperatingMode.Auto;

        TimeProgram timeProgram = new TimeProgramBuilder()
                .addSwitchCycle(new SwitchCycle(LocalTime.parse("08:30"), expectedOperatingMode))
                .build();

        final OperatingMode nextOperatingMode = timeProgramCalculator.determineOperationgModeByTime(
                LocalTime.parse("09:00"),
                timeProgram,
                lastKnownOperatingMode);

        assertThat(nextOperatingMode, equalTo(expectedOperatingMode));
    }
}
