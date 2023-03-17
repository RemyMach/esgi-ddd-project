package com.example.use_case;

import com.example.model.reservation.*;
import com.example.model.room.RoomExistingValidation;
import com.example.model.room.RoomId;
import com.example.model.room.Rooms;
import com.example.use_case.common.IdGenerator;
import com.example.use_case.common.ReservationRoomPayment;
import com.example.use_case.common.ReservationValidMailSender;
import com.example.use_case.exceptions.*;

import java.util.List;

public class CreateReservationCommand {
    private final Reservations reservations;
    private final Rooms rooms;
    private final ProspectDao prospectDao;
    private final IdGenerator idGenerator;
    private final ReservationRoomPayment reservationRoomPayment;

    private final ReservationValidMailSender reservationValidMailSender;

    public CreateReservationCommand(
        Reservations reservations,
        Rooms rooms,
        ProspectDao prospectDao,
        IdGenerator idGenerator,
        ReservationRoomPayment reservationRoomPayment,
        ReservationValidMailSender reservationValidMailSender
    ) {
        this.reservations = reservations;
        this.rooms = rooms;
        this.prospectDao = prospectDao;
        this.idGenerator = idGenerator;
        this.reservationRoomPayment = reservationRoomPayment;
        this.reservationValidMailSender = reservationValidMailSender;
    }

    public Reservation execute(CreateReservation createReservation) throws
            InvalidProspectAvailabilityException,
            ReservationInPastException,
            ReservationAtLeastOneHourBeforeException,
            UnavailableRoomException,
            NotEnoughCapacityException,
            ProspectNotFoundException, RoomNotFoundException, TimeWindowIllegalEndDateException {

        final RoomId roomId = new RoomId(createReservation.roomId());
        final ProspectId prospectId = new ProspectId(createReservation.prospectId());

        final RoomExistingValidation roomExistingValidation = new RoomExistingValidation(this.rooms);
        roomExistingValidation.check(roomId);

        final ProspectExistingValidation prospectExistingValidation = new ProspectExistingValidation(this.prospectDao);
        prospectExistingValidation.check(prospectId);
        final TimeWindow timeWindowCandidate = TimeWindow.of(createReservation.startedAt(), createReservation.endedAt());
        final Reservation reservation = Reservation.of(
                new ReservationId(idGenerator.generate()),
                timeWindowCandidate,
                roomId,
                prospectId,
                createReservation.numberOfPeople(),
                createReservation.reservationDescription()
        );

        List<Reservation> reservationsAlreadyBookedForTheProspect = this.reservations.getReservationsByProspectForADate(prospectId, timeWindowCandidate.getStart().toLocalDate());
        List<TimeWindow> timeWindowsAlreadyBookedForTheProspect = reservationsAlreadyBookedForTheProspect.stream().map(Reservation::getTimeWindow).toList();
        if(timeWindowCandidate.overlaps(timeWindowsAlreadyBookedForTheProspect)) {
            throw new InvalidProspectAvailabilityException("A prospect reservation overlaps with this reservation");
        }

        List<Reservation> reservationsForARoom = this.reservations.getReservationByRoomId(roomId);
        List<TimeWindow> timeWindowsAlreadyBookedForTheRoom = reservationsForARoom.stream().map(Reservation::getTimeWindow).toList();
        if(timeWindowCandidate.overlaps(timeWindowsAlreadyBookedForTheRoom)) {
            throw new UnavailableRoomException("The room already has a reservation which overlaps with this reservation");
        }

        reservation.checkReservationFitInRoomCapacity(this.rooms.getById(roomId).getCapacity());

        this.reservationRoomPayment.pay(prospectId, roomId, reservation.getTimeWindow());
        this.reservations.create(reservation);
        this.reservationValidMailSender.send(reservation);

        return reservation;
    }
}
