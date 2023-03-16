package com.example.model;

public interface Rooms {
    Room getById(String id);
    boolean exists(String id);
}
