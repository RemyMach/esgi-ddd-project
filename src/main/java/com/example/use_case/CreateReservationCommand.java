package com.example.use_case;

import com.example.model.Reservation;
import com.example.model.Reservations;

import java.util.List;

public class CreateReservationCommand {
    private final Reservations reservations;

    public CreateReservationCommand(
        Reservations reservations
    ) {
        this.reservations = reservations;
    }

    public void execute(CreateReservation createReservation) throws InvalidProspectAvailabilityException {
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


        // la date ne doit pas être dans le passé
        // la réservation doit-être fait au moins une heure avant la date de début

        // la room doit-être dispo
        // le nombre de personne de la réservation doit-être inferieur ou égal à la capcité max sur le créneau de la room

        // il faut que la réservation se fasse dans les horraires d'ouverture du site
    }
}
