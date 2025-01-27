package com.project.id.project.core.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.CompulsoryMedicalInsurance;

public interface CompulsoryMedicalInsuranceRepository {

    void addCompulsoryMedicalInsurance(CompulsoryMedicalInsurance compulsoryMedicalInsurance);

    CompulsoryMedicalInsurance getCompulsoryMedicalInsurance(Id id);

    void updateCompulsoryMedicalInsurance(CompulsoryMedicalInsurance compulsoryMedicalInsurance);

    void deleteCompulsoryMedicalInsurance(Id id);
}
