package com.example.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface Reservations {
    Reservation create(Reservation reservation);
    List<Reservation> getReservationsByProspectForADate(String prospectId, LocalDate date);
}
