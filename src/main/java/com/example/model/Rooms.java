package com.example.model;

public interface Rooms {
    Room getById(RoomId id);
    boolean exists(RoomId id);
}
