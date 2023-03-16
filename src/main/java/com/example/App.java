package com.example;

public class App
{
    public static void main( String[] args ) {
        /*Reservations reservations = new ReservationsInMemory();
        Rooms rooms = new RoomsInMemory();
        ProspectDao prospectDao = new ProspectDaoInMemory();

        CreateReservationCommand createReservationCommand = new CreateReservationCommand(reservations, rooms, prospectDao);
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
        }*/
    }

    public String getMessage() {
        return "Hello World!";
    }
}
