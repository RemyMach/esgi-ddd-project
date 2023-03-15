package com.example.model;

import java.time.LocalDateTime;

public class Reservation {
    private final ReservationId reservationId;
    private final LocalDateTime startedAt;
    private final LocalDateTime endedAt;
    private final String roomId;
    private final String prospectId;
    private final int numberOfPeople;
    private final String descritpion;

    public Reservation(ReservationId reservationId, LocalDateTime startedAt, LocalDateTime endedAt, String roomId, String prospectId, int numberOfPeople, String descritpion) {
        this.reservationId = reservationId;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.roomId = roomId;
        this.prospectId = prospectId;
        this.numberOfPeople = numberOfPeople;
        this.descritpion = descritpion;
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

    public String getRoomId() {
        return roomId;
    }

    public String getProspectId() {
        return prospectId;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public String getDescritpion() {
        return descritpion;
    }
}
