package com.example.model;

public class Prospect {
    private final ProspectId id;

    public Prospect(ProspectId id) {
        this.id = id;
    }

    public ProspectId getId() {
        return id;
    }
}
