package de.novity.openhab.hvac.application;

import de.novity.openhab.hvac.api.SwitchCycleData;
import de.novity.openhab.hvac.domain.OperatingMode;
import de.novity.openhab.hvac.domain.SwitchCycle;

public class SwitchCycleMapper {
    public static SwitchCycleData mapFromEntity(SwitchCycle switchCycle) {
        SwitchCycleData switchCycleData = new SwitchCycleData();
        switchCycleData.switchTime = switchCycle.getPointInTime();
        switchCycleData.operatingMode = switchCycle.getOperatingMode().getValue();

        return switchCycleData;
    }

    public static SwitchCycle mapFromData(SwitchCycleData switchCycleData) {
        SwitchCycle switchCycle = new SwitchCycle(
                switchCycleData.switchTime,
                OperatingMode.findByValue(switchCycleData.operatingMode)
        );

        return switchCycle;
    }
}
