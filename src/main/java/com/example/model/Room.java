package com.example.model;

public class Room {
    private final RoomId id;
    private final int capacity;

    public Room(RoomId id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    public RoomId getId() {
        return this.id;
    }

    public int getCapacity() {
        return this.capacity;
    }
}
