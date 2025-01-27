package com.project.id.project.application.DTOs.documents;

import com.project.id.project.core.Id;
import com.project.id.project.core.utils.Gender;

public class InsuranceNumberOfIndividualPersonalAccountDTO {
    private String photo;
    private String surname;
    private String name;
    private String patronymic;
    private String birthdate;
    private Gender gender;
    private String registrationDate;
    private String birthPlace;
    private String InsuranceNumberOfIndividualPersonalAccount;

    public InsuranceNumberOfIndividualPersonalAccountDTO() {
    }

    public InsuranceNumberOfIndividualPersonalAccountDTO(Id id,
                                                         String photo,
                                                         String surname,
                                                         String name, String patronymic,
                                                         String birthdate, Gender gender,
                                                         String registrationDate,
                                                         String birthPlace,
                                                         String insuranceNumberOfIndividualPersonalAccount) {
        this.photo = photo;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthdate = birthdate;
        this.gender = gender;
        this.registrationDate = registrationDate;
        this.birthPlace = birthPlace;
        InsuranceNumberOfIndividualPersonalAccount = insuranceNumberOfIndividualPersonalAccount;
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
}
