package com.project.id.project.core;

import com.project.id.project.core.emails.entities.AdditionalEmail;
import com.project.id.project.core.emails.entities.PersonalEmail;
import com.project.id.project.core.overall.Addresses;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.phones.entities.AdditionalPhoneNumber;
import com.project.id.project.core.phones.entities.PhoneNumber;
import com.project.id.project.core.utils.Gender;
import jakarta.persistence.*;

import java.util.List;

@MappedSuperclass
public abstract class Personal {
    @EmbeddedId
    @Column(name = "id")
    private Id id;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "invocation")
    private String invocation;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "birthdate")
    private String birthdate;
    @Column(name = "locality")
    private String locality;
    @Column(name = "time_zone")
    private String timeZone;
    @OneToMany(mappedBy = "personalData")
    private List<AdditionalEmail> additionalEmailList;
    @OneToMany(mappedBy = "personalData")
    private List<AdditionalPhoneNumber> additionalPhoneNumberList;
    @OneToOne(mappedBy = "personalData")
    private PhoneNumber phoneNumber;
    @OneToOne(mappedBy = "personalData")
    private PersonalEmail personalEmail;
    @OneToOne(mappedBy = "personalData", cascade = CascadeType.ALL)
    private Documents documents;
    @OneToOne(mappedBy = "personalData", cascade = CascadeType.ALL)
    private Addresses addresses;

    public Addresses getAddresses() {
        return addresses;
    }

    public void setAddresses(Addresses addresses) {
        this.addresses = addresses;
    }

    public Documents getDocuments() {
        return documents;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

    public PersonalEmail getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(PersonalEmail personalEmail) {
        this.personalEmail = personalEmail;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<AdditionalPhoneNumber> getAdditionalPhoneNumberList() {
        return additionalPhoneNumberList;
    }

    public void setAdditionalPhoneNumberList(List<AdditionalPhoneNumber> additionalPhoneNumberList) {
        this.additionalPhoneNumberList = additionalPhoneNumberList;
    }

    public List<AdditionalEmail> getAdditionalEmailList() {
        return additionalEmailList;
    }

    public void setAdditionalEmailList(List<AdditionalEmail> additionalEmailList) {
        this.additionalEmailList = additionalEmailList;
    }

    public Personal() {
        this.id = new Id();
    }

    public Personal(Id id,
                        String avatar,
                        String invocation,
                        String name,
                        String surname,
                        Gender gender,
                        String birthdate,
                        String locality,
                        String timeZone) {
        this.id = id;
        this.avatar = avatar;
        this.invocation = invocation;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birthdate = birthdate;
        this.locality = locality;
        this.timeZone = timeZone;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getInvocation() {
        return invocation;
    }

    public void setInvocation(String invocation) {
        this.invocation = invocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
