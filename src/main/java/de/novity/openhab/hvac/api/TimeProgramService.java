package de.novity.openhab.hvac.api;

public interface TimeProgramService {
    public void defineTimeProgram(String id);
    public void addSwitchCycle(String id, SwitchCycleData cycle);
}
