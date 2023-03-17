package infrastructure;

import com.example.model.reservation.Reservation;
import com.example.model.room.RoomId;
import com.example.use_case.common.ReservationValidMailSender;

public class ReservationValidMailSenderFake implements ReservationValidMailSender {
    @Override
    public void send(RoomId roomId, Reservation reservation) {
    }
}
