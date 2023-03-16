package com.example.use_case;

import com.example.model.Reservation;
import com.example.model.Reservations;
import com.example.model.Rooms;
import com.example.use_case.exceptions.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CreateReservationCommand {
    private final Reservations reservations;
    private final Rooms rooms;

    public CreateReservationCommand(
        Reservations reservations,
        Rooms rooms
    ) {
        this.reservations = reservations;
        this.rooms = rooms;
    }

    public void execute(CreateReservation createReservation) throws
            InvalidProspectAvailabilityException,
            ReservationInPastException,
            ReservationAtLeastOneHourBeforeException,
            UnavailableRoomException,
            NotEnoughCapacityException {

        // la salle doit exister
        if (!this.rooms.exists(createReservation.roomId())) {
            throw new UnavailableRoomException("La salle n'existe pas");
        }

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
        boolean isRoomAvailable = this.reservations.read()
                .stream()
                .noneMatch(room -> room.isOverlapping(createReservation.startedAt(), createReservation.endedAt()));
        if (!isRoomAvailable) {
            throw new UnavailableRoomException("Le site n'est pas disponible à ce créneau");
        }

        // le nombre de personne de la réservation doit-être inferieur ou égal à la capcité max sur le créneau de la room
        if (createReservation.numberOfPeople() > this.rooms.getById(createReservation.roomId()).getCapacity()) {
            throw new NotEnoughCapacityException("Le site n'a pas la capacité d'acceuillir le nombre de personne demandé");
        }

        // il faut que la réservation se fasse dans les horraires d'ouverture du site
    }
}
