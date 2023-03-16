package com.example.model;

import java.time.LocalDateTime;

public class Reservation {
    private final ReservationId reservationId;
    private final TimeWindow timeWindow;
    private final RoomId roomId;
    private final ProspectId prospectId;
    private final int numberOfPeople;
    private final String description;

    public Reservation(ReservationId reservationId, TimeWindow timeWindow, RoomId roomId, ProspectId prospectId, int numberOfPeople, String description) {
        this.reservationId = reservationId;
        this.timeWindow = timeWindow;
        this.roomId = roomId;
        this.prospectId = prospectId;
        this.numberOfPeople = numberOfPeople;
        this.description = description;
    }

    public ReservationId getReservationId() {
        return this.reservationId;
    }

    public TimeWindow getTimeWindow() {
        return this.timeWindow;
    }

    public RoomId getRoomId() {
        return this.roomId;
    }

    public ProspectId getProspectId() {
        return this.prospectId;
    }

    public int getNumberOfPeople() {
        return this.numberOfPeople;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isOverlapping(LocalDateTime startedAt, LocalDateTime endedAt) {
        return this.timeWindow.getStart().isBefore(endedAt) && this.timeWindow.getEnd().isAfter(startedAt);
    }
}
