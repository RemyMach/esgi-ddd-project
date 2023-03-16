package com.example.infrastructure;

import com.example.model.*;

import java.util.Arrays;
import java.util.List;

public class ProspectDaoInMemory implements ProspectDao {
    final List<ProspectId> prospectIds = Arrays.asList(
            new ProspectId("1"),
            new ProspectId("2")
    );

    @Override
    public boolean exists(ProspectId id) {
        return prospectIds.stream().anyMatch(prospect -> prospect.equals(id));
    }
}
