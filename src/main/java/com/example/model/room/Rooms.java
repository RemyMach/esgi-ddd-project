package com.example.model.room;

public interface Rooms {
    Room getById(RoomId id);
    boolean exists(RoomId id);
}
