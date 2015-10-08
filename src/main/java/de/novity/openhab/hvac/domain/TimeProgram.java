package de.novity.openhab.hvac.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimeProgram {
    private static final Logger logger = LoggerFactory.getLogger(TimeProgram.class);

    private final String id;
    private List<SwitchCycle> cycles;

    public TimeProgram(String id) {
        if ((id == null) || (id.isEmpty())){
            throw new IllegalArgumentException("Id must not be null or empty");
        }

        this.id = id;
        this.cycles = new ArrayList<SwitchCycle>();

        logger.info("Time program '{}' created", id);
    }

    public String getId() {
        return id;
    }

    public void add(SwitchCycle cycle) {
        if (cycle == null) {
            throw new NullPointerException("Switch cycle must not be null");
        }

        cycles.add(cycle);

        logger.info("Cycle '{}' added to time program '{}'", cycle, id);
    }

    public void addAll(List<SwitchCycle> cycles) {
        if (cycles == null) {
            throw new NullPointerException("List of switch cycles must not be null");
        }

        for (SwitchCycle cycle : cycles) {
            add(cycle);
        }
    }

    public boolean contains(SwitchCycle switchCycle) {
        return cycles.contains(switchCycle);
    }

    public List<SwitchCycle> getCycles() {
        return Collections.unmodifiableList(cycles);
    }

    public int size() {
        return cycles.size();
    }

    public SwitchCycle getEarliestSwitchCycle() {
        return cycles.get(0);
    }

    public SwitchCycle getLatestSwitchCycle() {
        return cycles.get(cycles.size() - 1);
    }
}
