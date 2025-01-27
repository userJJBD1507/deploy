package com.project.id.project.core;

import com.project.id.project.application.services.enc.EncryptionConverter;
import com.project.id.project.core.utils.Gender;
import jakarta.persistence.*;

@MappedSuperclass
public abstract class Document{
    @EmbeddedId
    @Column(name = "id")
    private Id id;
    @Column(name = "photo")
    @Convert(converter = EncryptionConverter.class)
    private String photo;
    @Column(name = "surname")
    @Convert(converter = EncryptionConverter.class)
    private String surname;
    @Column(name = "name")
    @Convert(converter = EncryptionConverter.class)
    private String name;
    @Column(name = "patronymic")
    @Convert(converter = EncryptionConverter.class)
    private String patronymic;
    @Column(name = "birthdate")
    @Convert(converter = EncryptionConverter.class)
    private String birthdate;
    @Column(name = "gender")
    private Gender gender;

    public Document() {
        this.id = new Id();
    }

    public Document(Id id, String photo,
                    String surname,
                    String name,
                    String patronymic,
                    String birthdate,
                    Gender gender) {
        this.id = id;
        this.photo = photo;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthdate = birthdate;
        this.gender = gender;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", photo='" + photo + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", gender=" + gender +
                '}';
    }
}
