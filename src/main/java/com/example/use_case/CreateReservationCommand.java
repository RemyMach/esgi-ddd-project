package com.example.use_case;

import com.example.model.reservation.*;
import com.example.model.room.RoomExistingValidation;
import com.example.model.room.RoomId;
import com.example.model.room.Rooms;
import com.example.use_case.common.IdGenerator;
import com.example.use_case.exceptions.*;

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

        final RoomId roomId = new RoomId(createReservation.roomId());
        final ProspectId prospectId = new ProspectId(createReservation.prospectId());

        // la salle doit exister
        final RoomExistingValidation roomExistingValidation = new RoomExistingValidation(this.rooms);
        roomExistingValidation.check(roomId);

        // le prospect doit exister
        final ProspectExistingValidation prospectExistingValidation = new ProspectExistingValidation(this.prospectDao);
        prospectExistingValidation.check(prospectId);

        final Reservation reservation = new Reservation(
                new ReservationId(idGenerator.generate()),
                new TimeWindow(createReservation.startedAt(), createReservation.endedAt()),
                roomId,
                prospectId,
                createReservation.numberOfPeople(),
                createReservation.reservationDescription()
        );
        reservation.checkIfIsValid();

        // un prospect ne peux réserver que si il a pas de résa à ce créneau
        CheckProspectAvailability checkProspectAvailability = new CheckProspectAvailability(this.reservations);
        checkProspectAvailability.check(new TimeWindow(createReservation.startedAt(), createReservation.endedAt()), prospectId);

        // la room doit-être dispo
        CheckRoomTimeWindowAvailability checkRoomTimeWindowAvailability = new CheckRoomTimeWindowAvailability(this.reservations);
        checkRoomTimeWindowAvailability.check(roomId, new TimeWindow(createReservation.startedAt(), createReservation.endedAt()));

        // le nombre de personne de la réservation doit-être inferieur ou égal à la capcité max sur le créneau de la room
        reservation.checkReservationFitInRoomCapacity(this.rooms.getById(roomId).getCapacity());
    }
}
