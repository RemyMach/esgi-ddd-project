package com.example.use_case.exceptions;

public class NotEnoughCapacityException extends Exception {
    public NotEnoughCapacityException(String message) {
        super(message);
    }
}
