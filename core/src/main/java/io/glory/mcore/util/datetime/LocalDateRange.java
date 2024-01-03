package io.glory.mcore.util.datetime;

import java.time.LocalDate;
import java.time.Period;

public class LocalDateRange {

    private final LocalDate start;
    private final LocalDate end;

    private LocalDateRange(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException(
                    "Start date must be before end date, start: " + start + " , end: " + end);
        }
        this.start = start;
        this.end = end;
    }

    /**
     * Returns a {@code LocalDateRange} with the specified start and end dates.
     *
     * @param start inclusive start date
     * @param end   inclusive end date
     * @return a {@code LocalDateRange} with the specified start and end dates
     */
    public static LocalDateRange of(LocalDate start, LocalDate end) {
        return new LocalDateRange(start, end);
    }

    /**
     * Returns true if the specified date is within the range.
     * <p>
     * The start date and end date are included.
     */
    public boolean contains(LocalDate date) {
        return !date.isBefore(start) && !date.isAfter(end);
    }

    /**
     * Obtains a {@code Period} consisting of the number of years, months, and days between two dates.
     * <p>
     * The start date inclusive and end date excluded.
     */
    public Period difference() {
        return difference(false);
    }

    /**
     * Obtains a {@code Period} consisting of the number of years, months, and days between two dates.
     *
     * @param inclusiveEnd true if the end date is included
     */
    public Period difference(boolean inclusiveEnd) {
        if (!inclusiveEnd) {
            return Period.between(start, end);
        }

        LocalDate endDate = end.plusDays(1);
        return Period.between(start, endDate);
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

}
