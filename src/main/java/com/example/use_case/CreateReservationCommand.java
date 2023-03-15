package com.example.use_case;

public class CreateReservationCommand {
    public void execute(CreateReservation createReservation) {
        System.out.println("CreateReservation");
        // un prospect ne peux réserver que si il a pas de résa à ce créneau
        // la date ne doit pas être dans le passé
        // la réservation doit-être fait au moins une heure avant la date de début

        // la room doit-être dispo
        // le nombre de personne de la réservation doit-être inferieur ou égal à la capcité max sur le créneau de la room

        // le créneau du temps de réseervation doit respécter les quotas possible de la room 1h, 2h, 3h
        // il faut que la réservation se fasse dans les horraires d'ouverture du site
    }
}
