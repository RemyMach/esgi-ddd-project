package com.example.use_case;

import com.example.model.*;
import com.example.use_case.common.IdGenerator;
import com.example.use_case.exceptions.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CreateReservationCommand {
    private final Reservations reservations;
    private final Rooms rooms;
    private final ProspectDao prospectDao;

    private final IdGenerator idGenerator;

    public CreateReservationCommand(
        Reservations reservations,
        Rooms rooms,
        ProspectDao prospectDao,
        IdGenerator idGenerator
    ) {
        this.reservations = reservations;
        this.rooms = rooms;
        this.prospectDao = prospectDao;
        this.idGenerator = idGenerator;
    }

    public void execute(CreateReservation createReservation) throws
            InvalidProspectAvailabilityException,
            ReservationInPastException,
            ReservationAtLeastOneHourBeforeException,
            UnavailableRoomException,
            NotEnoughCapacityException,
            ProspectNotFoundException, RoomNotFoundException {

        final Room room = new Room(new RoomId(createReservation.roomId()), createReservation.numberOfPeople());
        final ProspectId prospectId = new ProspectId(createReservation.prospectId());

        // la salle doit exister
        final RoomExistingValidation roomExistingValidation = new RoomExistingValidation(this.rooms);
        roomExistingValidation.check(room.getId());

        // le prospect doit exister
        final ProspectExistingValidation prospectExistingValidation = new ProspectExistingValidation(this.prospectDao);
        prospectExistingValidation.check(prospectId);

        final Reservation reservation = new Reservation(
                new ReservationId(idGenerator.generate()),
                new TimeWindow(createReservation.startedAt(), createReservation.endedAt()),
                room.getId(),
                prospectId,
                createReservation.numberOfPeople(),
                createReservation.reservationDescription()
        );
        reservation.checkIfIsValid();

        // un prospect ne peux réserver que si il a pas de résa à ce créneau
        List<Reservation> reservationsAlreadyBookedForTheUser = this.reservations.getReservationsByProspectForADate(prospectId, createReservation.startedAt().toLocalDate());
        boolean userHasAlreadyAReservation = reservationsAlreadyBookedForTheUser.stream().anyMatch(reservationFind -> {
            if (reservationFind.getTimeWindow().getStart().isAfter(createReservation.endedAt())
                    || reservationFind.getTimeWindow().getEnd().isBefore(createReservation.startedAt())
                    || reservationFind.getTimeWindow().getStart().equals(createReservation.endedAt())
                    || reservationFind.getTimeWindow().getEnd().equals(createReservation.startedAt())) {
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
                .noneMatch(r -> r.isOverlapping(createReservation.startedAt(), createReservation.endedAt()));
        if (!isRoomAvailable) {
            throw new UnavailableRoomException("l'espace de travail n'est pas disponible à ce créneau");
        }

        // le nombre de personne de la réservation doit-être inferieur ou égal à la capcité max sur le créneau de la room
        if (createReservation.numberOfPeople() > this.rooms.getById(room.getId()).getCapacity()) {
            throw new NotEnoughCapacityException("l'espace de travail n'a pas la capacité d'acceuillir le nombre de personne demandé");
        }
    }
}
