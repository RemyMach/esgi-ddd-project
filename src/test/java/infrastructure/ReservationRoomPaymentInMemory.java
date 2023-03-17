package infrastructure;

import com.example.model.reservation.ProspectId;
import com.example.model.reservation.TimeWindow;
import com.example.model.room.RoomId;
import com.example.use_case.common.ReservationRoomPayment;

public class ReservationRoomPaymentInMemory implements ReservationRoomPayment {

    @Override
    public void pay(ProspectId prospectId, RoomId roomId, TimeWindow timeWindow) {

    }
}
