package com.project.id.project.application.DTOs.personal;

import java.util.List;

public class IdsDTO {
    private String personalId;
    private String birthCertificateId;
    private String compulsoryMedInsuranceId;
    private String drivingLicenseId;
    private String foreignPassportId;
    private String insuranceNumberOfPersonalAccId;
    private String passportId;
    private String taxPayerIdentNumberId;
    private String voluntaryHealthInsuranceId;

    private String addressId;
    private List<String> additionalAddressId;
    private String workAddressId;
    private String homeAddressId;

    private String personalEmailId;
    private List<String> additionalEmailId;
    private List<String> additionalPhoneNumberId;
    private String phoneNumberId;
    public IdsDTO(String personalId, String birthCertificateId, String compulsoryMedInsuranceId, String drivingLicenseId,
                  String foreignPassportId, String insuranceNumberOfPersonalAccId, String passportId,
                  String taxPayerIdentNumberId, String voluntaryHealthInsuranceId, String addressId,
                  List<String> additionalAddressId, String workAddressId, String homeAddressId,
                  String personalEmailId, List<String> additionalEmailId, List<String> additionalPhoneNumberId, String phoneNumberId) {
        this.personalId = personalId;
        this.birthCertificateId = birthCertificateId;
        this.compulsoryMedInsuranceId = compulsoryMedInsuranceId;
        this.drivingLicenseId = drivingLicenseId;
        this.foreignPassportId = foreignPassportId;
        this.insuranceNumberOfPersonalAccId = insuranceNumberOfPersonalAccId;
        this.passportId = passportId;
        this.taxPayerIdentNumberId = taxPayerIdentNumberId;
        this.voluntaryHealthInsuranceId = voluntaryHealthInsuranceId;
        this.addressId = addressId;
        this.additionalAddressId = additionalAddressId;
        this.workAddressId = workAddressId;
        this.homeAddressId = homeAddressId;
        this.personalEmailId = personalEmailId;
        this.additionalEmailId = additionalEmailId;
        this.additionalPhoneNumberId = additionalPhoneNumberId;
        this.phoneNumberId = phoneNumberId;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getBirthCertificateId() {
        return birthCertificateId;
    }

    public void setBirthCertificateId(String birthCertificateId) {
        this.birthCertificateId = birthCertificateId;
    }

    public String getCompulsoryMedInsuranceId() {
        return compulsoryMedInsuranceId;
    }

    public void setCompulsoryMedInsuranceId(String compulsoryMedInsuranceId) {
        this.compulsoryMedInsuranceId = compulsoryMedInsuranceId;
    }

    public String getDrivingLicenseId() {
        return drivingLicenseId;
    }

    public void setDrivingLicenseId(String drivingLicenseId) {
        this.drivingLicenseId = drivingLicenseId;
    }

    public String getForeignPassportId() {
        return foreignPassportId;
    }

    public void setForeignPassportId(String foreignPassportId) {
        this.foreignPassportId = foreignPassportId;
    }

    public String getInsuranceNumberOfPersonalAccId() {
        return insuranceNumberOfPersonalAccId;
    }

    public void setInsuranceNumberOfPersonalAccId(String insuranceNumberOfPersonalAccId) {
        this.insuranceNumberOfPersonalAccId = insuranceNumberOfPersonalAccId;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public String getTaxPayerIdentNumberId() {
        return taxPayerIdentNumberId;
    }

    public void setTaxPayerIdentNumberId(String taxPayerIdentNumberId) {
        this.taxPayerIdentNumberId = taxPayerIdentNumberId;
    }

    public String getVoluntaryHealthInsuranceId() {
        return voluntaryHealthInsuranceId;
    }

    public void setVoluntaryHealthInsuranceId(String voluntaryHealthInsuranceId) {
        this.voluntaryHealthInsuranceId = voluntaryHealthInsuranceId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public List<String> getAdditionalAddressId() {
        return additionalAddressId;
    }

    public void setAdditionalAddressId(List<String> additionalAddressId) {
        this.additionalAddressId = additionalAddressId;
    }

    public String getWorkAddressId() {
        return workAddressId;
    }

    public void setWorkAddressId(String workAddressId) {
        this.workAddressId = workAddressId;
    }

    public String getHomeAddressId() {
        return homeAddressId;
    }

    public void setHomeAddressId(String homeAddressId) {
        this.homeAddressId = homeAddressId;
    }

    public String getPersonalEmailId() {
        return personalEmailId;
    }

    public void setPersonalEmailId(String personalEmailId) {
        this.personalEmailId = personalEmailId;
    }

    public List<String> getAdditionalEmailId() {
        return additionalEmailId;
    }

    public void setAdditionalEmailId(List<String> additionalEmailId) {
        this.additionalEmailId = additionalEmailId;
    }

    public List<String> getAdditionalPhoneNumberId() {
        return additionalPhoneNumberId;
    }

    public void setAdditionalPhoneNumberId(List<String> additionalPhoneNumberId) {
        this.additionalPhoneNumberId = additionalPhoneNumberId;
    }

    public String getPhoneNumberId() {
        return phoneNumberId;
    }

    public void setPhoneNumberId(String phoneNumberId) {
        this.phoneNumberId = phoneNumberId;
    }
    
}
