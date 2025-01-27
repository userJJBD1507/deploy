package com.project.id.project.core.personal;

import com.project.id.project.core.Id;
import com.project.id.project.core.Personal;
import com.project.id.project.core.overall.Addresses;
import com.project.id.project.core.overall.Documents;
import com.project.id.project.core.emails.entities.AdditionalEmail;
import com.project.id.project.core.emails.entities.PersonalEmail;
import com.project.id.project.core.phones.entities.AdditionalPhoneNumber;
import com.project.id.project.core.phones.entities.PhoneNumber;
import com.project.id.project.core.utils.Gender;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "personal_data_table")
public class PersonalData extends Personal {
    public PersonalData() {
    }

    public PersonalData(Id id, String avatar,
                        String invocation,
                        String name,
                        String surname,
                        Gender gender,
                        String birthdate,
                        String locality,
                        String timeZone) {
        super(id, avatar, invocation, name, surname, gender, birthdate, locality, timeZone);
    }
}
