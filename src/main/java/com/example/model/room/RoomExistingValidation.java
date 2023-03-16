package com.example.model.room;

import com.example.use_case.exceptions.RoomNotFoundException;

public class RoomExistingValidation {
    private final Rooms rooms;
    public RoomExistingValidation(Rooms rooms) {
        this.rooms = rooms;
    }

    public void check(RoomId id) throws RoomNotFoundException {
        if (!this.rooms.exists(id)) {
            throw new RoomNotFoundException("Room not found");
        }
    }
}
