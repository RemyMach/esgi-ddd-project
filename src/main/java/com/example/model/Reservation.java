package com.example.model;

import java.time.LocalDateTime;

public class Reservation {
    private final ReservationId reservationId;
    private final LocalDateTime startedAt;
    private final LocalDateTime endedAt;
    private final RoomId roomId;
    private final ProspectId prospectId;
    private final int numberOfPeople;
    private final String description;

    public Reservation(ReservationId reservationId, LocalDateTime startedAt, LocalDateTime endedAt, RoomId roomId, ProspectId prospectId, int numberOfPeople, String description) {
        this.reservationId = reservationId;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.roomId = roomId;
        this.prospectId = prospectId;
        this.numberOfPeople = numberOfPeople;
        this.description = description;
    }

    public ReservationId getReservationId() {
        return reservationId;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public RoomId getRoomId() {
        return roomId;
    }

    public ProspectId getProspectId() {
        return prospectId;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public String getDescription() {
        return description;
    }

    public boolean isOverlapping(LocalDateTime startedAt, LocalDateTime endedAt) {
        return this.startedAt.isBefore(endedAt) && this.endedAt.isAfter(startedAt);
    }
}
