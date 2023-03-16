package com.example.use_case.exceptions;

public class ReservationInPastException extends Exception {

    public ReservationInPastException(String message) {
        super(message);
    }
}
