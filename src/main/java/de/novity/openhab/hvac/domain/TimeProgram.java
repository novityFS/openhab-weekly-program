package de.novity.openhab.hvac.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class TimeProgram {
    private final CalendarSchedule calendarSchedule;
    private final TimeSchedule timeSchedule;

    public TimeProgram(CalendarSchedule calendarSchedule, TimeSchedule timeSchedule) {
        if (calendarSchedule == null) {
            throw new NullPointerException("Calendar day must not be null");
        }

        if (timeSchedule == null) {
            throw new NullPointerException("Time schedule must not be null");
        }

        this.calendarSchedule = calendarSchedule;
        this.timeSchedule = timeSchedule;
    }

    public CalendarSchedule getCalendarSchedule() {
        return calendarSchedule;
    }

    public TimeSchedule getTimeSchedule() {
        return timeSchedule;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (obj.getClass() != getClass()) {
            return false;
        }

        TimeProgram rhs = (TimeProgram) obj;

        return new EqualsBuilder()
                .append(calendarSchedule, rhs.calendarSchedule)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(19, 23)
                .append(calendarSchedule)
                .toHashCode();
    }
}
