package com.project.id.project.core.overall;

import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.*;
import com.project.id.project.core.personal.PersonalData;
import jakarta.persistence.*;

@Entity
@Table(name = "all_documents_table")
public class Documents {
    @EmbeddedId
    @Column(name = "id")
    private Id id;
    @OneToOne
    @JoinColumn(name = "passport_id")
    private Passport passport;
    @OneToOne
    @JoinColumn(name = "foreign_passport_id")
    private ForeignPassport foreignPassport;
    @OneToOne
    @JoinColumn(name = "birth_certificate_id")
    private BirthCertificate birthCertificate;
    @OneToOne
    @JoinColumn(name = "voluntary_health_insurance_id")
    private VoluntaryHealthInsurance voluntaryHealthInsurance;
    @OneToOne
    @JoinColumn(name = "compulsory_med_insurance_id")
    private CompulsoryMedicalInsurance compulsoryMedicalInsurance;
    @OneToOne
    @JoinColumn(name = "tax_payer_ident_number_id")
    private TaxPayerIdentificationNumber taxPayerIdentificationNumber;
    @OneToOne
    @JoinColumn(name = "insurance_number_of_personal_acc_id")
    private InsuranceNumberOfIndividualPersonalAccount insuranceNumberOfIndividualPersonalAccount;
    @OneToOne
    @JoinColumn(name = "driving_license_id")
    private DrivingLicense drivingLicense;
    @OneToOne
    @JoinColumn(name = "personal_data_id")
    private PersonalData personalData;

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public Documents() {
        this.id = new Id();
    }

    public Documents(Passport passport) {
        this.passport = passport;
    }

    public Documents(ForeignPassport foreignPassport) {
        this.foreignPassport = foreignPassport;
    }

    public Documents(BirthCertificate birthCertificate) {
        this.birthCertificate = birthCertificate;
    }

    public Documents(VoluntaryHealthInsurance voluntaryHealthInsurance) {
        this.voluntaryHealthInsurance = voluntaryHealthInsurance;
    }

    public Documents(CompulsoryMedicalInsurance compulsoryMedicalInsurance) {
        this.compulsoryMedicalInsurance = compulsoryMedicalInsurance;
    }

    public Documents(TaxPayerIdentificationNumber taxPayerIdentificationNumber) {
        this.taxPayerIdentificationNumber = taxPayerIdentificationNumber;
    }

    public Documents(InsuranceNumberOfIndividualPersonalAccount insuranceNumberOfIndividualPersonalAccount) {
        this.insuranceNumberOfIndividualPersonalAccount = insuranceNumberOfIndividualPersonalAccount;
    }

    public Documents(DrivingLicense drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public ForeignPassport getForeignPassport() {
        return foreignPassport;
    }

    public void setForeignPassport(ForeignPassport foreignPassport) {
        this.foreignPassport = foreignPassport;
    }

    public BirthCertificate getBirthCertificate() {
        return birthCertificate;
    }

    public void setBirthCertificate(BirthCertificate birthCertificate) {
        this.birthCertificate = birthCertificate;
    }

    public VoluntaryHealthInsurance getVoluntaryHealthInsurance() {
        return voluntaryHealthInsurance;
    }

    public void setVoluntaryHealthInsurance(VoluntaryHealthInsurance voluntaryHealthInsurance) {
        this.voluntaryHealthInsurance = voluntaryHealthInsurance;
    }

    public CompulsoryMedicalInsurance getCompulsoryMedicalInsurance() {
        return compulsoryMedicalInsurance;
    }

    public void setCompulsoryMedicalInsurance(CompulsoryMedicalInsurance compulsoryMedicalInsurance) {
        this.compulsoryMedicalInsurance = compulsoryMedicalInsurance;
    }

    public TaxPayerIdentificationNumber getTaxPayerIdentificationNumber() {
        return taxPayerIdentificationNumber;
    }

    public void setTaxPayerIdentificationNumber(TaxPayerIdentificationNumber taxPayerIdentificationNumber) {
        this.taxPayerIdentificationNumber = taxPayerIdentificationNumber;
    }

    public InsuranceNumberOfIndividualPersonalAccount getInsuranceNumberOfIndividualPersonalAccount() {
        return insuranceNumberOfIndividualPersonalAccount;
    }

    public void setInsuranceNumberOfIndividualPersonalAccount(InsuranceNumberOfIndividualPersonalAccount insuranceNumberOfIndividualPersonalAccount) {
        this.insuranceNumberOfIndividualPersonalAccount = insuranceNumberOfIndividualPersonalAccount;
    }

    public DrivingLicense getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(DrivingLicense drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    @Override
    public String toString() {
        return "Documents{" +
                "id=" + id +
                ", passport=" + passport +
                ", foreignPassport=" + foreignPassport +
                ", birthCertificate=" + birthCertificate +
                ", voluntaryHealthInsurance=" + voluntaryHealthInsurance +
                ", compulsoryMedicalInsurance=" + compulsoryMedicalInsurance +
                ", taxPayerIdentificationNumber=" + taxPayerIdentificationNumber +
                ", insuranceNumberOfIndividualPersonalAccount=" + insuranceNumberOfIndividualPersonalAccount +
                ", drivingLicense=" + drivingLicense +
                ", personalData=" + personalData +
                '}';
    }
}
