package com.project.id.project.core.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.VoluntaryHealthInsurance;

public interface VoluntaryHealthInsuranceRepository {

    void addVoluntaryHealthInsurance(VoluntaryHealthInsurance voluntaryHealthInsurance);

    VoluntaryHealthInsurance getVoluntaryHealthInsurance(Id id);

    void updateVoluntaryHealthInsurance(VoluntaryHealthInsurance voluntaryHealthInsurance);

    void deleteVoluntaryHealthInsurance(Id id);
}
