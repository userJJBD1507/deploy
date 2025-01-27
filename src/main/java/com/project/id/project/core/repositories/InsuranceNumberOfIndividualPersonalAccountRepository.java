package com.project.id.project.core.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.InsuranceNumberOfIndividualPersonalAccount;

public interface InsuranceNumberOfIndividualPersonalAccountRepository {

    void addInsuranceNumberOfIndividualPersonalAccount(InsuranceNumberOfIndividualPersonalAccount insuranceNumber);

    InsuranceNumberOfIndividualPersonalAccount getInsuranceNumberOfIndividualPersonalAccount(Id id);

    void updateInsuranceNumberOfIndividualPersonalAccount(InsuranceNumberOfIndividualPersonalAccount insuranceNumber);

    void deleteInsuranceNumberOfIndividualPersonalAccount(Id id);
}
