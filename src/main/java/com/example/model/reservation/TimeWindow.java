package com.example.model.reservation;

import com.example.use_case.exceptions.TimeWindowIllegalEndDateException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class TimeWindow {
    private final LocalDateTime start;
    private final LocalDateTime end;

    private TimeWindow(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    public static TimeWindow of(LocalDateTime start, LocalDateTime end) throws TimeWindowIllegalEndDateException {
        if (start.isAfter(end)) {
            throw new TimeWindowIllegalEndDateException("Start time must be before end time");
        }
        return new TimeWindow(start, end);
    }

    public boolean overlaps(List<TimeWindow> others) {
        boolean timeWindowIsOverlapping = others.stream().anyMatch(timeWindowFromLoop -> {
            if (timeWindowFromLoop.getStart().isAfter(this.getEnd())
                    || timeWindowFromLoop.getEnd().isBefore(this.getStart())
                    || timeWindowFromLoop.getStart().equals(this.getEnd())
                    || timeWindowFromLoop.getEnd().equals(this.getStart())) {
                return false;
            }
            return true;
        });

        return timeWindowIsOverlapping;
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
