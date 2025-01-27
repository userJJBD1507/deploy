package com.project.id.project.core;

import com.project.id.project.application.services.enc.EncryptionConverter;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class Email {
    @EmbeddedId
    @Column(name = "id")
    private Id id;
    @Column(name = "email")
    @Convert(converter = EncryptionConverter.class)
    private String email;

    public Email() {
        this.id = new Id();
    }

    public Email(Id id, String email) {
        this.id = id;
        this.email = email;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
