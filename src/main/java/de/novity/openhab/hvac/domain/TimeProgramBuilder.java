package de.novity.openhab.hvac.domain;

public interface TimeProgramBuilder {
    TimeProgram defaultProgram();

    TimeProgramBuilder addSwitchCycle(SwitchCycle switchCycle);
    TimeProgram build();
}
