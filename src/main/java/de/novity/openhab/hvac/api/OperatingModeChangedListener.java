package de.novity.openhab.hvac.api;

import de.novity.openhab.hvac.domain.OperatingMode;

public interface OperatingModeChangedListener {
    void operatingModeChanged(String itemName, OperatingMode oldMode, OperatingMode newMode);
}
