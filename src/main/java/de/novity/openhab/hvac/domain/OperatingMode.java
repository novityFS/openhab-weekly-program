package de.novity.openhab.hvac.domain;

/**
 * Defines the operating mode as defined for standard HVAC (heating, ventilation and air condition) systems.
 */
public enum OperatingMode {
    /**
     * The automatic mode. For MDT components this is equal to <code>Standby</code>
     */
    Auto(0x00),

    /**
     * The comfort mode. In this mode the HVAC system holds the nominal temperature. This mode is typically used
     * when at home.
     */
    Comfort(0x01),

    /**
     * The standby mode. In this mode the HVAC system reduces the comfort temperature to save energy. This mode
     * is typically used, when the house is empty but should heat up in short time when returning.
     */
    Standby(0x02),

    /**
     * The economy mode. In this mode the HVAC system reduces the comfort temperature to a lower degree than
     * in standby mode to save more energy. This mode is typically used by night.
     */
    Economy(0x03),

    /**
     * The building protection mode. In this mode the HVAC system shuts down but protects the building from freezing.
     */
    BuildingProtection(0x04);

    private final int value;

    private OperatingMode(final int value) {
        this.value = value;
    }
}
