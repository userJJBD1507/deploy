package com.project.id.project.core.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.TaxPayerIdentificationNumber;

public interface TaxPayerIdentificationNumberRepository {

    void addTaxPayerIdentificationNumber(TaxPayerIdentificationNumber taxPayerIdentificationNumber);

    TaxPayerIdentificationNumber getTaxPayerIdentificationNumber(Id id);

    void updateTaxPayerIdentificationNumber(TaxPayerIdentificationNumber taxPayerIdentificationNumber);

    void deleteTaxPayerIdentificationNumber(Id id);
}
