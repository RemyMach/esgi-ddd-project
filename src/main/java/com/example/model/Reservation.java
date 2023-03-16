package com.example.model;

import com.example.use_case.exceptions.ReservationAtLeastOneHourBeforeException;
import com.example.use_case.exceptions.ReservationInPastException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    public void checkIfIsValid() throws ReservationInPastException, ReservationAtLeastOneHourBeforeException {
        // la date ne doit pas être dans le passé
        if (this.getTimeWindow().getStart().isBefore(LocalDateTime.now())) {
            throw new ReservationInPastException("La date de début de la réservation ne doit pas être dans le passé");
        }

        // la réservation doit-être fait au moins une heure avant la date de début
        if (this.getTimeWindow().getStart().isBefore(LocalDateTime.now().plus(1, ChronoUnit.HOURS))) {
            throw new ReservationAtLeastOneHourBeforeException("La réservation doit-être faite au moins une heure avant la date de début");
        }
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
