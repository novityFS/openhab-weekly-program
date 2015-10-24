package de.novity.openhab.hvac.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

public class CalendarWeekSchedule extends CalendarSchedule {
    private Set<DayOfWeek> validDaysOfWeek;

    public CalendarWeekSchedule(Set<DayOfWeek> validDaysOfWeek) {
        super(TypeOfDay.DayOfWeek);

        if ((validDaysOfWeek == null) || (validDaysOfWeek.isEmpty())) {
            throw new IllegalArgumentException("Valid days of week must not be null or empty");
        }

        this.validDaysOfWeek = validDaysOfWeek;
    }

    public Set<DayOfWeek> getValidDaysOfWeek() {
        return Collections.unmodifiableSet(validDaysOfWeek);
    }

    @Override
    public boolean matchesDate(LocalDate date) {
        return validDaysOfWeek.contains(date.getDayOfWeek());
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

        CalendarWeekSchedule rhs = (CalendarWeekSchedule) obj;

        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(validDaysOfWeek, rhs.validDaysOfWeek)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 39)
                .appendSuper(87)
                .append(validDaysOfWeek)
                .toHashCode();
    }
}
