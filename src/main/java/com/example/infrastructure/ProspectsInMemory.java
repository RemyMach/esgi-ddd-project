package com.example.infrastructure;

import com.example.model.*;

import java.util.Arrays;
import java.util.List;

public class ProspectsInMemory implements Prospects {
    final List<Prospect> prospects = Arrays.asList(
            new Prospect(new ProspectId("1")),
            new Prospect(new ProspectId("2"))
    );

    @Override
    public boolean exists(ProspectId id) {
        return prospects.stream().anyMatch(prospect -> prospect.getId().equals(id));
    }
}
