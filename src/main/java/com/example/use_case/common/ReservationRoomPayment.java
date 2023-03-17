package com.example.use_case.common;

import com.example.model.reservation.ProspectId;
import com.example.model.reservation.TimeWindow;
import com.example.model.room.RoomId;

public interface ReservationRoomPayment {
    void pay(ProspectId prospectId, RoomId roomId, TimeWindow timeWindow);
}
