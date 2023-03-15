package com.example;

import com.example.use_case.CreateReservation;
import com.example.use_case.CreateReservationCommand;

import java.time.LocalDateTime;

public class App
{
    public static void main( String[] args )
    {
        CreateReservationCommand createReservationCommand = new CreateReservationCommand();
        CreateReservation createReservation = new CreateReservation(
            LocalDateTime.of(2021, 1, 1, 10, 0),
                LocalDateTime.of(2021, 1, 1, 11, 0),
            "1",
            "1",
            2,
            new String[] {"pomme@gmail.com"},
            "ça à l'air d'être un super espace de travail"
        );
        createReservationCommand.execute(createReservation);
    }

    public String getMessage() {
        return "Hello World!";
    }
}
