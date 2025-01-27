package com.project.id.project.core;

import com.project.id.project.application.services.enc.EncryptionConverter;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class Phone {
    @EmbeddedId
    @Column(name = "id")
    private Id id;
    @Column(name = "phone_number")
    @Convert(converter = EncryptionConverter.class)
    private String phoneNumber;

    public Phone() {
        this.id = new Id();
    }

    public Phone(Id id, String phoneNumber) {
        this.id = id;
        this.phoneNumber = phoneNumber;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
