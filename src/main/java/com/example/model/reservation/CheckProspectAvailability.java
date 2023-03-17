package com.example.model.reservation;

import com.example.use_case.exceptions.InvalidProspectAvailabilityException;

import java.util.List;

public class CheckProspectAvailability {

    private final Reservations reservations;

    public CheckProspectAvailability(Reservations reservations) {
        this.reservations = reservations;
    }

    public void check(TimeWindow timeWindow, ProspectId prospectId) throws InvalidProspectAvailabilityException {
        List<Reservation> reservationsAlreadyBookedForTheUser = this.reservations.getReservationsByProspectForADate(prospectId, timeWindow.getStart().toLocalDate());
        boolean userHasAlreadyAReservation = reservationsAlreadyBookedForTheUser.stream().anyMatch(reservationFind -> {
            if (reservationFind.getTimeWindow().getStart().isAfter(timeWindow.getEnd())
                    || reservationFind.getTimeWindow().getEnd().isBefore(timeWindow.getStart())
                    || reservationFind.getTimeWindow().getStart().equals(timeWindow.getEnd())
                    || reservationFind.getTimeWindow().getEnd().equals(timeWindow.getStart())) {
                return false;
            }
            return true;
        });

        if( userHasAlreadyAReservation) {
            throw new InvalidProspectAvailabilityException("The prospect has already a reservation for this time window");
        }
    }
}
