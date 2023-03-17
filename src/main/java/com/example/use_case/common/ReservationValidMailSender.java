package com.example.use_case.common;

import com.example.model.reservation.ProspectId;
import com.example.model.reservation.Reservation;
import com.example.model.room.RoomId;

public interface ReservationValidMailSender {
    void send(RoomId roomId, Reservation reservation);
}
