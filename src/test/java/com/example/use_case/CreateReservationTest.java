package com.example.use_case;

import com.example.infrastructure.ReservationsInMemory;
import com.example.infrastructure.RoomsInMemory;
import com.example.model.Prospect;
import com.example.model.Reservations;
import com.example.model.Rooms;
import com.example.use_case.exceptions.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertThrows;

public class CreateReservationTest {
    Prospect prospect;
    Reservations reservations;
    Rooms rooms;
    CreateReservationCommand createReservationCommand;

    @Before()
    public void setUp() {
        this.prospect = new Prospect("1");
        this.reservations = new ReservationsInMemory();
        this.rooms = new RoomsInMemory();
        this.createReservationCommand = new CreateReservationCommand(this.reservations, this.rooms);
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
                5_000, // sheesh
                new String[] { "" },
                ""
        );

        assertThrows(NotEnoughCapacityException.class , () -> this.createReservationCommand.execute(reservation));
    }
}
