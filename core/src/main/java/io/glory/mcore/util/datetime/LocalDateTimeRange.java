package io.glory.mcore.util.datetime;

import java.time.Duration;
import java.time.LocalDateTime;

public class LocalDateTimeRange {

    private final LocalDateTime start;
    private final LocalDateTime end;

    private LocalDateTimeRange(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException(
                    "Start date must be before end date, start: " + start + " , end: " + end);
        }
        this.start = start;
        this.end = end;
    }

    /**
     * Returns a {@code LocalDateTimeRange} with the specified start and end dates.
     *
     * @param start inclusive start date
     * @param end   inclusive end date
     * @return a {@code LocalDateTimeRange} with the specified start and end dates
     */
    public static LocalDateTimeRange of(LocalDateTime start, LocalDateTime end) {
        return new LocalDateTimeRange(start, end);
    }

    /**
     * Returns true if the specified date is within the range.
     * <p>
     * The start date and end date are included.
     */
    public boolean contains(LocalDateTime dateTime) {
        return !dateTime.isBefore(start) && !dateTime.isAfter(end);
    }

    /**
     * Obtains a {@code Duration} consisting of the number of years, months, and days between two dates.
     * <p>
     * The start date inclusive and end date exclusive.
     */
    public Duration difference() {
        return Duration.between(start, end);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

}
