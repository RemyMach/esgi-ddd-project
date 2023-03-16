package com.example.use_case.exceptions;

public class RoomNotFoundException extends Exception {
    public RoomNotFoundException(String message) {
        super(message);
    }
}