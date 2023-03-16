package com.example.infrastructure;

import com.example.model.Room;
import com.example.model.RoomId;
import com.example.model.Rooms;

import java.util.Arrays;
import java.util.List;

public class RoomsInMemory implements Rooms {
    final List<Room> rooms = Arrays.asList(
            new Room(new RoomId("1"), 1),
            new Room(new RoomId("2"), 2)
    );

    @Override
    public Room getById(String id) {
        return this.rooms.stream().filter(room -> room.getId().value.equals(id)).findFirst().orElse(null);
    }

    @Override
    public boolean exists(String id) {
        return this.rooms.stream().anyMatch(room -> room.getId().value.equals(id));
    }
}
