package com.project.id.project.core.phones.entities;

import com.project.id.project.core.Id;
import com.project.id.project.core.Phone;
import com.project.id.project.core.personal.PersonalData;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "phone_numbers_table")
public class PhoneNumber extends Phone {
    public PhoneNumber() {
    }

    public PhoneNumber(Id id, String phoneNumber) {
        super(id, phoneNumber);
    }
    @OneToOne
    @JoinColumn(name = "personal_data_id")
    private PersonalData personalData;
    @Version
    private Long version;
    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "personalData=" + personalData +
                '}';
    }
}
