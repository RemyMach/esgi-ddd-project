package com.example.model.reservation;

import com.example.model.room.RoomId;
import com.example.use_case.exceptions.UnavailableRoomException;

public class CheckRoomTimeWindowAvailability {

    private final Reservations reservations;

    public CheckRoomTimeWindowAvailability(Reservations reservations) {
        this.reservations = reservations;
    }

    public void check(RoomId roomId, TimeWindow timeWindow) throws UnavailableRoomException {
        boolean isRoomAvailable = this.reservations.read()
                .stream()
                .noneMatch(roomMatch -> roomMatch.isOverlapping(timeWindow.getStart(), timeWindow.getEnd()));
        if (!isRoomAvailable) {
            throw new UnavailableRoomException("l'espace de travail n'est pas disponible à ce créneau");
        }
    }
}
