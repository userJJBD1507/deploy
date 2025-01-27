package com.project.id.project.application.DTOs.documents;

import com.project.id.project.core.Id;
import com.project.id.project.core.utils.Gender;

public class TaxPayerIdentificationNumberDTO {
    private String photo;
    private String surname;
    private String name;
    private String patronymic;
    private String birthdate;
    private Gender gender;
    private String taxpayerIdentificationNumber;
    private String issueDate;
    private String birthPlace;
    private String effectiveUntil;
    private String issuingAuthority;

    public TaxPayerIdentificationNumberDTO() {
    }

    public TaxPayerIdentificationNumberDTO(
                                           String photo,
                                           String surname,
                                           String name,
                                           String patronymic,
                                           String birthdate, Gender gender,
                                           String taxpayerIdentificationNumber,
                                           String issueDate, String birthPlace,
                                           String effectiveUntil, String issuingAuthority) {
        this.photo = photo;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthdate = birthdate;
        this.gender = gender;
        this.taxpayerIdentificationNumber = taxpayerIdentificationNumber;
        this.issueDate = issueDate;
        this.birthPlace = birthPlace;
        this.effectiveUntil = effectiveUntil;
        this.issuingAuthority = issuingAuthority;
    }



    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getTaxpayerIdentificationNumber() {
        return taxpayerIdentificationNumber;
    }

    public void setTaxpayerIdentificationNumber(String taxpayerIdentificationNumber) {
        this.taxpayerIdentificationNumber = taxpayerIdentificationNumber;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getEffectiveUntil() {
        return effectiveUntil;
    }

    public void setEffectiveUntil(String effectiveUntil) {
        this.effectiveUntil = effectiveUntil;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }
}
