package infrastructure;

import com.example.model.reservation.*;
import com.example.model.room.RoomId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReservationsInMemory implements Reservations {
    final List<Reservation> reservations = new ArrayList<>(Arrays.asList(
            new Reservation(new ReservationId("1"), TimeWindow.of(LocalDateTime.of(2024, 1, 1, 10, 0), LocalDateTime.of(2024, 1, 1, 11, 0)), new RoomId("1"), new ProspectId("1"), 2, ""),
            new Reservation(new ReservationId("2"), TimeWindow.of(LocalDateTime.of(2024, 1, 1, 11, 0), LocalDateTime.of(2024, 1, 1, 12, 0)), new RoomId("1"), new ProspectId("1"), 3, ""),
            new Reservation(new ReservationId("3"), TimeWindow.of(LocalDateTime.of(2024, 1, 1, 12, 0), LocalDateTime.of(2024, 1, 1, 13, 0)), new RoomId("1"), new ProspectId("1"), 1, ""),
            new Reservation(new ReservationId("4"), TimeWindow.of(LocalDateTime.of(2024, 1, 1, 13, 0), LocalDateTime.of(2024, 1, 1, 14, 0)), new RoomId("1"), new ProspectId("1"), 2, "je suis une description"),
            new Reservation(new ReservationId("7"), TimeWindow.of(LocalDateTime.of(2024, 1, 1, 16, 0), LocalDateTime.of(2024, 1, 1, 17, 0)), new RoomId("2"), new ProspectId("1"), 1, "desc"),
            new Reservation(new ReservationId("8"), TimeWindow.of(LocalDateTime.of(2024, 1, 1, 17, 0), LocalDateTime.of(2024, 1, 1, 18, 0)), new RoomId("2"), new ProspectId("1"), 1, "desc"),
            new Reservation(new ReservationId("9"), TimeWindow.of(LocalDateTime.of(2024, 1, 1, 18, 0), LocalDateTime.of(2024, 1, 1, 19, 0)), new RoomId("2"), new ProspectId("1"), 1, "desc")
    ));

    @Override
    public Reservation create(Reservation reservation) {
        this.reservations.add(reservation);
        return reservation;
    }

    @Override
    public List<Reservation> read() {
        return this.reservations;
    }

    @Override
    public List<Reservation> getReservationsByProspectForADate(ProspectId prospectId, LocalDate date) {
        List<Reservation> reservations = new ArrayList<>();
        for (Reservation reservation : this.reservations) {
            if (reservation.getProspectId().equals(prospectId) && reservation.getTimeWindow().getStart().toLocalDate().equals(date)) {
                reservations.add(reservation);
            }
        }
        return reservations;
    }


}
