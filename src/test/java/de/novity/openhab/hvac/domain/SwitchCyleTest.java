package de.novity.openhab.hvac.domain;

import org.testng.annotations.Test;

import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SwitchCyleTest {
    @Test(
            expectedExceptions = IllegalArgumentException.class
    )
    public final void whenAllArgumentsAreInvalidAndASwitchCycleIsConstructedThenAnExceptionIsTheResult() {
        new SwitchCycle(null, null);
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class
    )
    public final void whenAnInvalidPointInTimeIsGivenAndASwitchCycleIsCreatedThenAnExceptionIsTheResult() {
        new SwitchCycle(null, OperatingMode.Auto);
    }

    @Test(
            expectedExceptions = IllegalArgumentException.class
    )
    public final void whenAnInvalidOperatingModeIsGivenAndASwitchCycleIsCreatedThenAnExceptionIsTheResult() {
        new SwitchCycle(LocalTime.now(), null);
    }

    @Test
    public final void whenASwitchCycleIsCreatedWithAValidPointInTimeThenTheResultOfTheGetterIsThatSameArgument() {
        final LocalTime now = LocalTime.now();

        SwitchCycle switchCycle = new SwitchCycle(now, OperatingMode.Auto);
        assertThat(switchCycle.getPointInTime(), equalTo(now));
    }

    @Test
    public final void whenASwitchCycleIsCreatedWithAValidOpratingModeThenTheResultOfTheGetterIsThatSameArgument() {
        final OperatingMode operatingModeAuto = OperatingMode.Auto;

        SwitchCycle switchCycle = new SwitchCycle(LocalTime.now(), operatingModeAuto);
        assertThat(switchCycle.getOperatingMode(), equalTo(operatingModeAuto));
    }

    @Test
    public final void whenTwoSwitchCyclesAreCreatedWithTheSameArgumentsThenTheyShouldBeEqual() {
        final SwitchCycle switchCycle1 = new SwitchCycle(LocalTime.parse("00:00"), OperatingMode.Auto);
        final SwitchCycle switchCycle2 = new SwitchCycle(LocalTime.parse("00:00"), OperatingMode.Auto);

        assertThat(switchCycle1, equalTo(switchCycle2));
    }

    @Test
    public final void whenTwoSwitchCyclesAreCreatedWithTheSameArgumentsThenTheirHashCodesShouldBeTheSame() {
        final long switchCycle1Hash = new SwitchCycle(LocalTime.parse("00:00"), OperatingMode.Auto).hashCode();
        final long switchCycle2Hash = new SwitchCycle(LocalTime.parse("00:00"), OperatingMode.Auto).hashCode();

        assertThat(switchCycle1Hash, equalTo(switchCycle2Hash));
    }
}
