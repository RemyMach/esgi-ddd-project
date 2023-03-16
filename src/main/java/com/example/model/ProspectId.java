package com.example.model;

import java.util.Objects;

public class ProspectId {
    private final String value;

    public ProspectId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProspectId roomId = (ProspectId) o;
        return Objects.equals(value, roomId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
