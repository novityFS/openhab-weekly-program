package de.novity.openhab.hvac.domain;

import java.time.LocalTime;

public class SwitchCycle implements Comparable<SwitchCycle> {
    private final LocalTime pointInTime;
    private final OperatingMode operatingMode;

    public SwitchCycle(LocalTime pointInTime, OperatingMode operatingMode) {
        if ((pointInTime == null) || (operatingMode == null)) {
            throw new IllegalArgumentException("Point in time or operating mode must not be null");
        }

        this.pointInTime = pointInTime;
        this.operatingMode = operatingMode;
    }

    public LocalTime getPointInTime() {
        return pointInTime;
    }

    public OperatingMode getOperatingMode() {
        return operatingMode;
    }

    public int compareTo(SwitchCycle that) {
        int timeComparison = pointInTime.compareTo(that.pointInTime);

        if (timeComparison == 0) {
            timeComparison = operatingMode.compareTo(that.operatingMode);
        }

        return timeComparison;
    }

    public boolean isAfter(LocalTime time) {
        return pointInTime.isAfter(time);
    }

    public boolean isBefore(LocalTime time) {
        return pointInTime.isBefore(time) || pointInTime.compareTo(time) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SwitchCycle that = (SwitchCycle) o;

        return pointInTime.equals(that.pointInTime) && operatingMode == that.operatingMode;
    }

    @Override
    public int hashCode() {
        int result = pointInTime.hashCode();
        result = 31 * result + operatingMode.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return pointInTime + ", " + operatingMode;
    }
}
