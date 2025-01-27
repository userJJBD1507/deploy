package com.project.id.project.core.emails.entities;

import com.project.id.project.core.Email;
import com.project.id.project.core.Id;
import com.project.id.project.core.personal.PersonalData;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Table(name = "additional_emails_table")
@Entity
public class AdditionalEmail extends Email {
    public AdditionalEmail() {
    }

    public AdditionalEmail(Id id, String email) {
        super(id, email);
    }
    @ManyToOne
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
        return "AdditionalEmail{" +
                "personalData=" + personalData +
                '}';
    }
}
