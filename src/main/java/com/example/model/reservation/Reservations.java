package com.example.model.reservation;

import com.example.model.room.RoomId;

import java.time.LocalDate;
import java.util.List;

public interface Reservations {
    Reservation create(Reservation reservation);
    List<Reservation> read();
    List<Reservation> getReservationsByProspectForADate(ProspectId prospectId, LocalDate date);
    List<Reservation> getReservationByRoomId(RoomId roomId);
}
