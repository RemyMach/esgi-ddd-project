package com.example.model.reservation;

import com.example.use_case.exceptions.ProspectNotFoundException;

public class ProspectExistingValidation {

    final ProspectDao prospectDao;

    public ProspectExistingValidation(ProspectDao prospectDao) {
        this.prospectDao = prospectDao;
    }

    public void check(ProspectId id) throws ProspectNotFoundException {
        if (!this.prospectDao.exists(id)) {
            throw new ProspectNotFoundException("Prospect not found");
        }
    }
}
