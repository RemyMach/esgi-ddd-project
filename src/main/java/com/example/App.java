package com.example;

import com.example.infrastructure.ReservationsInMemory;
import com.example.model.Reservations;
import com.example.use_case.CreateReservation;
import com.example.use_case.CreateReservationCommand;

import java.time.LocalDateTime;

public class App
{
    public static void main( String[] args ) {
        Reservations reservations = new ReservationsInMemory();
        CreateReservationCommand createReservationCommand = new CreateReservationCommand(reservations);
        CreateReservation createReservation = new CreateReservation(
            LocalDateTime.of(2021, 1, 1, 10, 0),
                LocalDateTime.of(2021, 1, 1, 11, 0),
            "1",
            "1",
            2,
            new String[] {"pomme@gmail.com"},
            "ça à l'air d'être un super espace de travail"
        );
        try {
            createReservationCommand.execute(createReservation);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getMessage() {
        return "Hello World!";
    }
}
