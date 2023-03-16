package com.example.model;

import java.time.LocalDate;
import java.util.List;

public interface Reservations {
    Reservation create(Reservation reservation);
    Reservation read(String id);
    List<Reservation> read();
    List<Reservation> getReservationsByProspectForADate(ProspectId prospectId, LocalDate date);
}
