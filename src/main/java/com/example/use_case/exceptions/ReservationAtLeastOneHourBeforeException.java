package com.example.use_case.exceptions;

public class ReservationAtLeastOneHourBeforeException extends Exception {
    public ReservationAtLeastOneHourBeforeException(String message) {
        super(message);
    }
}
