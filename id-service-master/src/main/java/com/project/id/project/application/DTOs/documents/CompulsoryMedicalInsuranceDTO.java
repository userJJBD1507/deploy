package com.project.id.project.application.DTOs.documents;

import com.project.id.project.core.Id;
import com.project.id.project.core.utils.Gender;

public class CompulsoryMedicalInsuranceDTO {
    private String photo;
    private String surname;
    private String name;
    private String patronymic;
    private String birthdate;
    private Gender gender;
    private String seriesAndNumber;
    private String seriesAndFormNumber;

    public CompulsoryMedicalInsuranceDTO(
                                         String photo,
                                         String surname,
                                         String name,
                                         String patronymic,
                                         String birthdate,
                                         Gender gender,
                                         String seriesAndNumber,
                                         String seriesAndFormNumber) {
        this.photo = photo;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthdate = birthdate;
        this.gender = gender;
        this.seriesAndNumber = seriesAndNumber;
        this.seriesAndFormNumber = seriesAndFormNumber;
    }

    public CompulsoryMedicalInsuranceDTO() {
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

    public String getSeriesAndNumber() {
        return seriesAndNumber;
    }

    public void setSeriesAndNumber(String seriesAndNumber) {
        this.seriesAndNumber = seriesAndNumber;
    }

    public String getSeriesAndFormNumber() {
        return seriesAndFormNumber;
    }

    public void setSeriesAndFormNumber(String seriesAndFormNumber) {
        this.seriesAndFormNumber = seriesAndFormNumber;
    }
}
