package io.glory.mcore.util.datetime;

import java.time.Duration;
import java.time.LocalTime;

public class LocalTimeRange {

    private final LocalTime start;
    private final LocalTime end;

    private LocalTimeRange(LocalTime start, LocalTime end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException(
                    "Start time must be before end time, start: " + start + " , end: " + end);
        }
        this.start = start;
        this.end = end;
    }

    /**
     * Returns a {@code LocalTimeRange} with the specified start and end times.
     *
     * @param start inclusive start time
     * @param end   inclusive end time
     * @return a {@code LocalTimeRange} with the specified start and end times
     */
    public static LocalTimeRange of(LocalTime start, LocalTime end) {
        return new LocalTimeRange(start, end);
    }

    /**
     * Returns true if the specified time is within the range.
     * <p>
     * The start time and end time are included.
     */
    public boolean contains(LocalTime time) {
        return !time.isBefore(start) && !time.isAfter(end);
    }

    /**
     * Obtains a {@code Duration} consisting of the number of hours, minutes, and seconds between two times.
     * <p>
     * The start time inclusive and end time exclusive.
     */
    public Duration difference() {
        return Duration.between(start, end);
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

}
