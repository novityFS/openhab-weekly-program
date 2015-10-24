package de.novity.openhab.hvac.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDate;

public abstract class CalendarSchedule {
    protected final TypeOfDay typeOfDay;

    public CalendarSchedule(TypeOfDay typeOfDay) {
        if (typeOfDay == null) {
            throw new NullPointerException("Type of day must not be null");
        }

        this.typeOfDay = typeOfDay;
    }

    public abstract boolean matchesDate(LocalDate date);

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

        CalendarSchedule rhs = (CalendarSchedule) obj;

        return new EqualsBuilder()
                .append(typeOfDay, rhs.typeOfDay)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 15)
                .append(typeOfDay)
                .toHashCode();
    }
}
