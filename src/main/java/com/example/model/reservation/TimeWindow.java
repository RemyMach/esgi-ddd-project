package com.example.model.reservation;

import java.time.LocalDateTime;
import java.util.Objects;

public class TimeWindow {
    private final LocalDateTime start;
    private final LocalDateTime end;

    private TimeWindow(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public static TimeWindow of(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        return new TimeWindow(start, end);
    }



    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeWindow that = (TimeWindow) o;
        return Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
