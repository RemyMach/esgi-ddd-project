package com.example.use_case;

import com.example.model.Reservation;
import com.example.model.Reservations;
import com.example.use_case.exceptions.InvalidProspectAvailabilityException;
import com.example.use_case.exceptions.ReservationInPastException;
import com.example.use_case.exceptions.ReservationAtLeastOneHourBeforeException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CreateReservationCommand {
    private final Reservations reservations;

    public CreateReservationCommand(
        Reservations reservations
    ) {
        this.reservations = reservations;
    }

    public void execute(CreateReservation createReservation) throws InvalidProspectAvailabilityException, ReservationInPastException, ReservationAtLeastOneHourBeforeException {
        // la date ne doit pas être dans le passé
        if (createReservation.startedAt().isBefore(LocalDateTime.now())) {
            throw new ReservationInPastException("La date de début de la réservation ne doit pas être dans le passé");
        }

        // la réservation doit-être fait au moins une heure avant la date de début
        if (createReservation.startedAt().isBefore(LocalDateTime.now().plus(1, ChronoUnit.HOURS))) {
            throw new ReservationAtLeastOneHourBeforeException("La réservation doit-être faite au moins une heure avant la date de début");
        }
        // un prospect ne peux réserver que si il a pas de résa à ce créneau
        List<Reservation> reservationsAlreadyBookedForTheUser = this.reservations.getReservationsByProspectForADate(createReservation.prospectId(), createReservation.startedAt().toLocalDate());
        boolean userHasAlreadyAReservation = reservationsAlreadyBookedForTheUser.stream().anyMatch(reservation -> {
            if (reservation.getStartedAt().isAfter(createReservation.endedAt())
                    || reservation.getEndedAt().isBefore(createReservation.startedAt())
                    || reservation.getStartedAt().equals(createReservation.endedAt())
                    || reservation.getEndedAt().equals(createReservation.startedAt())) {
                return false;
            }
            return true;
        });

        if (userHasAlreadyAReservation) {
            throw new InvalidProspectAvailabilityException("Le prospect a déjà une réservation à ce créneau");
        }


        // la room doit-être dispo
        // le nombre de personne de la réservation doit-être inferieur ou égal à la capcité max sur le créneau de la room

        // il faut que la réservation se fasse dans les horraires d'ouverture du site
    }
}
