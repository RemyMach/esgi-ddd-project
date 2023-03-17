package com.example.use_case;

import com.example.model.reservation.*;
import com.example.model.room.Rooms;
import com.example.use_case.common.IdGenerator;
import com.example.use_case.common.ReservationRoomPayment;
import com.example.use_case.common.ReservationValidMailSender;
import com.example.use_case.exceptions.*;
import infrastructure.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class CreateReservationTest {
    Reservations reservations;
    Rooms rooms;
    ProspectDao prospectDao;
    CreateReservationCommand createReservationCommand;
    IdGenerator idGenerator;
    ReservationRoomPayment reservationRoomPayment;

    ReservationValidMailSender reservationValidMailSender;

    @Before()
    public void setUp() {
        this.reservations = new ReservationsInMemory();
        this.rooms = new RoomsInMemory();
        this.prospectDao = new ProspectDaoInMemory();
        this.idGenerator = new IdGeneratorNotRandom();
        this.reservationRoomPayment = new ReservationRoomPaymentInMemory();
        this.reservationValidMailSender = new ReservationValidMailSenderFake();
        this.createReservationCommand = new CreateReservationCommand(this.reservations, this.rooms, this.prospectDao, this.idGenerator, this.reservationRoomPayment, this.reservationValidMailSender);
    }

    @Test()
    public void AProspectCantDoAReservationThatStartBeforeAndEndBeforeAnExistingReservation() {
        CreateReservation createReservation = new CreateReservation(
                LocalDateTime.of(2024, 1, 1, 9, 30),
                LocalDateTime.of(2024, 1, 1, 10, 30),
                "1",
                "1",
                2,
                new String[] {""},
                ""
        );

        assertThrows(InvalidProspectAvailabilityException.class , () -> this.createReservationCommand.execute(createReservation));
    }

    @Test()
    public void AProspectCantDoAReservationThatStartAtTheSameTimeAndEndAtTheSameTimeThatAnExistingReservation() {
        CreateReservation createReservation = new CreateReservation(
                LocalDateTime.of(2024, 1, 1, 10, 0),
                LocalDateTime.of(2024, 1, 1, 11, 0),
                "1",
                "1",
                2,
                new String[] {""},
                ""
        );

        assertThrows(InvalidProspectAvailabilityException.class , () -> this.createReservationCommand.execute(createReservation));
    }

    @Test()
    public void AProspectCantDoAReservationThatStartAfterAndEndAfterAnExistingReservation() {
        CreateReservation createReservation = new CreateReservation(
                LocalDateTime.of(2024, 1, 1, 13, 30),
                LocalDateTime.of(2024, 1, 1, 14, 30),
                "1",
                "1",
                2,
                new String[] {""},
                ""
        );

        assertThrows(InvalidProspectAvailabilityException.class , () -> this.createReservationCommand.execute(createReservation));
    }

    @Test()
    public void AProspectCantDoAReservationInPast() {

        LocalDateTime startedDate = LocalDateTime.now().minus(1, java.time.temporal.ChronoUnit.DAYS).minus(1, java.time.temporal.ChronoUnit.HOURS);
        LocalDateTime endedAt = LocalDateTime.now().minus(1, ChronoUnit.DAYS);


        CreateReservation createReservation = new CreateReservation(
                startedDate,
                endedAt,
                "1",
                "1",
                2,
                new String[] {""},
                ""
        );

        assertThrows(ReservationInPastException.class , () -> this.createReservationCommand.execute(createReservation));
    }

    @Test()
    public void AProspectCantDoAReservationLessThanOneHourBeforeStarting() {

        LocalDateTime startedDate = LocalDateTime.now().plus(50, ChronoUnit.MINUTES);
        LocalDateTime endedAt = LocalDateTime.now().plus(50, ChronoUnit.MINUTES).plus(1, ChronoUnit.HOURS);


        CreateReservation createReservation = new CreateReservation(
                startedDate,
                endedAt,
                "1",
                "1",
                2,
                new String[] {""},
                ""
        );

        assertThrows(ReservationAtLeastOneHourBeforeException.class , () -> this.createReservationCommand.execute(createReservation));
    }

    @Test()
    public void ASiteCantHaveTwoReservationsAtTheSameTime() {
        CreateReservation reservation = new CreateReservation(
                LocalDateTime.of(2024, 1, 1, 10, 0),
                LocalDateTime.of(2024, 1, 1, 11, 0),
                "2",
                "2",
                2,
                new String[] {""},
                ""
        );

        assertThrows(UnavailableRoomException.class , () -> this.createReservationCommand.execute(reservation));
    }

    @Test()
    public void TheRoomCapacityShouldBeSuperiorOrEqualToTheNumberOfGuestsInTheReservation() {
        LocalDateTime startingTime = LocalDateTime.of(2025, 1, 1, 9, 30);
        LocalDateTime endingTime = LocalDateTime.of(2025, 1, 1, 10, 30);

        CreateReservation reservation = new CreateReservation(
                startingTime,
                endingTime,
                "1",
                "1",
                5_000,
                new String[] { "" },
                ""
        );

        assertThrows(NotEnoughCapacityException.class , () -> this.createReservationCommand.execute(reservation));
    }

    @Test()
    public void TheProspectShouldExist() {
        LocalDateTime startingTime = LocalDateTime.of(2025, 1, 1, 9, 30);
        LocalDateTime endingTime = LocalDateTime.of(2025, 1, 1, 10, 30);

        CreateReservation reservation = new CreateReservation(
                startingTime,
                endingTime,
                "1",
                "-1",
                2,
                new String[] { "" },
                ""
        );

        assertThrows(ProspectNotFoundException.class , () -> this.createReservationCommand.execute(reservation));
    }

    @Test()
    public void TheRoomShouldExist() {
        LocalDateTime startingTime = LocalDateTime.of(2025, 1, 1, 9, 30);
        LocalDateTime endingTime = LocalDateTime.of(2025, 1, 1, 10, 30);

        CreateReservation reservation = new CreateReservation(
                startingTime,
                endingTime,
                "-1",
                "1",
                2,
                new String[] { "" },
                ""
        );

        assertThrows(RoomNotFoundException.class , () -> this.createReservationCommand.execute(reservation));
    }

    @Test()
    public void TheReservationShouldBeCreated() throws UnavailableRoomException, InvalidProspectAvailabilityException, NotEnoughCapacityException, RoomNotFoundException, ReservationAtLeastOneHourBeforeException, ProspectNotFoundException, ReservationInPastException {
        LocalDateTime startingTime = LocalDateTime.of(2025, 1, 1, 9, 30);
        LocalDateTime endingTime = LocalDateTime.of(2025, 1, 1, 10, 30);

        CreateReservation reservation = new CreateReservation(
                startingTime,
                endingTime,
                "1",
                "1",
                1,
                new String[] { "" },
                ""
        );

        Reservation reservationCreated = this.createReservationCommand.execute(reservation);

        assertTrue(reservationCreated.getTimeWindow().equals(new TimeWindow(startingTime, endingTime)));

        //assertThrows(RoomNotFoundException.class , () -> this.createReservationCommand.execute(reservation));
    }
}
