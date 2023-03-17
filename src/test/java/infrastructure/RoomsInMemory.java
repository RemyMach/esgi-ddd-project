package infrastructure;

import com.example.model.room.Room;
import com.example.model.room.RoomId;
import com.example.model.room.Rooms;

import java.util.Arrays;
import java.util.List;

public class RoomsInMemory implements Rooms {
    final List<Room> rooms = Arrays.asList(
            new Room(new RoomId("1"), 1),
            new Room(new RoomId("2"), 2)
    );

    @Override
    public Room getById(RoomId id) {
        return this.rooms.stream().filter(room -> room.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public boolean exists(RoomId id) {
        return this.rooms.stream().anyMatch(room -> room.getId().equals(id));
    }
}
