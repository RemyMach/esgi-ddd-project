package com.example.use_case;

import com.example.infrastructure.ReservationsInMemory;
import com.example.model.Prospect;
import com.example.model.Reservation;
import com.example.model.ReservationId;
import com.example.model.Reservations;
import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import java.time.LocalDateTime;

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
                LocalDateTime.of(2021, 1, 1, 9, 30),
                LocalDateTime.of(2021, 1, 1, 10, 30),
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
                LocalDateTime.of(2021, 1, 1, 10, 0),
                LocalDateTime.of(2021, 1, 1, 11, 0),
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
                LocalDateTime.of(2021, 1, 1, 13, 30),
                LocalDateTime.of(2021, 1, 1, 14, 30),
                "1",
                "1",
                2,
                new String[] {""},
                ""
        );

        assertThrows(InvalidProspectAvailabilityException.class , () -> this.createReservationCommand.execute(createReservation));
    }
}
