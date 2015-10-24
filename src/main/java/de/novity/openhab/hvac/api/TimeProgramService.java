package de.novity.openhab.hvac.api;

public interface TimeProgramService {
    public void defineTimeSchedule(String id);
    public void addSwitchCycle(String id, SwitchCycleData cycle);
}
