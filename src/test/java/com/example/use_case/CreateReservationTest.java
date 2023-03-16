package com.example.use_case;

import com.example.infrastructure.ReservationsInMemory;
import com.example.model.Prospect;
import com.example.model.Reservations;
import com.example.use_case.exceptions.InvalidProspectAvailabilityException;
import com.example.use_case.exceptions.ReservationAtLeastOneHourBeforeException;
import com.example.use_case.exceptions.ReservationInPastException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class CreateReservationTest {
    Prospect prospect;
    Reservations reservations;
    CreateReservationCommand createReservationCommand;

    @Before()
    public void setUp() {
        this.prospect = new Prospect("1");
        this.reservations = new ReservationsInMemory();
        this.createReservationCommand = new CreateReservationCommand(this.reservations);
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
}
