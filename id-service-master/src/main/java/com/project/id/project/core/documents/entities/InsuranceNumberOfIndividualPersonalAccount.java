package com.project.id.project.core.documents.entities;

import com.project.id.project.application.services.enc.EncryptionConverter;
import com.project.id.project.core.Document;
import com.project.id.project.core.overall.Documents;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "insurance_number_of_individual_personal_account")
public class InsuranceNumberOfIndividualPersonalAccount extends Document {
    @Column(name = "registration_date")
    @Convert(converter = EncryptionConverter.class)
    private String registrationDate;
    @Column(name = "birthplace")
    @Convert(converter = EncryptionConverter.class)
    private String birthPlace;
    @Column(name = "value_insurance_number_of_individual_personal_account")
    @Convert(converter = EncryptionConverter.class)
    private String InsuranceNumberOfIndividualPersonalAccount;
    @OneToOne(mappedBy = "insuranceNumberOfIndividualPersonalAccount")
    private Documents documents;
    @Version
    private Long version;
    public Documents getDocuments() {
        return documents;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

    public InsuranceNumberOfIndividualPersonalAccount() {
    }

    public InsuranceNumberOfIndividualPersonalAccount(String registrationDate,
                                                      String birthPlace,
                                                      String insuranceNumberOfIndividualPersonalAccount) {
        this.registrationDate = registrationDate;
        this.birthPlace = birthPlace;
        InsuranceNumberOfIndividualPersonalAccount = insuranceNumberOfIndividualPersonalAccount;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getInsuranceNumberOfIndividualPersonalAccount() {
        return InsuranceNumberOfIndividualPersonalAccount;
    }

    public void setInsuranceNumberOfIndividualPersonalAccount(String insuranceNumberOfIndividualPersonalAccount) {
        InsuranceNumberOfIndividualPersonalAccount = insuranceNumberOfIndividualPersonalAccount;
    }

    @Override
    public String toString() {
        return "InsuranceNumberOfIndividualPersonalAccount{" +
                "registrationDate='" + registrationDate + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                ", InsuranceNumberOfIndividualPersonalAccount='" + InsuranceNumberOfIndividualPersonalAccount + '\'' +
                '}';
    }
}
