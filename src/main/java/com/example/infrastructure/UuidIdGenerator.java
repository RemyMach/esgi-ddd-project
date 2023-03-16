package com.example.infrastructure;

import com.example.use_case.common.IdGenerator;

public class UuidIdGenerator  implements IdGenerator {
    @Override
    public String generate() {
        return java.util.UUID.randomUUID().toString();
    }
}
