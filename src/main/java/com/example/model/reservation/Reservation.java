package com.example.model.reservation;

import com.example.model.room.RoomId;
import com.example.use_case.exceptions.NotEnoughCapacityException;
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

    private Reservation(ReservationId reservationId, TimeWindow timeWindow, RoomId roomId, ProspectId prospectId, int numberOfPeople, String description) {
        this.reservationId = reservationId;
        this.timeWindow = timeWindow;
        this.roomId = roomId;
        this.prospectId = prospectId;
        this.numberOfPeople = numberOfPeople;
        this.description = description;
    }

    public static Reservation of(ReservationId reservationId, TimeWindow timeWindow, RoomId roomId, ProspectId prospectId, int numberOfPeople, String description) throws ReservationInPastException, ReservationAtLeastOneHourBeforeException {
        if (timeWindow.getStart().isBefore(LocalDateTime.now())) {
            throw new ReservationInPastException("The reservation cannot be in the past");
        }

        if (timeWindow.getStart().isBefore(LocalDateTime.now().plus(1, ChronoUnit.HOURS))) {
            throw new ReservationAtLeastOneHourBeforeException("The reservation must be made at least one hour before the start date");
        }
        return new Reservation(reservationId, timeWindow, roomId, prospectId, numberOfPeople, description);
    }


    public void checkReservationFitInRoomCapacity(int roomCapacity) throws NotEnoughCapacityException {
        if (this.getNumberOfPeople() > roomCapacity) {
            throw new NotEnoughCapacityException("The room does not have enough capacity");
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
