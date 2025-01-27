package com.project.id.project.core.emails.entities;

import com.project.id.project.core.Email;
import com.project.id.project.core.Id;
import com.project.id.project.core.personal.PersonalData;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Table(name = "personal_email_table")
@Entity
public class PersonalEmail extends Email {
    public PersonalEmail() {
    }

    public PersonalEmail(Id id, String email) {
        super(id, email);
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
        return "PersonalEmail{" +
                "personalData=" + personalData +
                '}';
    }
}
