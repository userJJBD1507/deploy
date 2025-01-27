package com.project.id.project.core.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.emails.entities.PersonalEmail;

public interface PersonalEmailRepository {

    void addPersonalEmail(PersonalEmail personalEmail);

    PersonalEmail getPersonalEmail(Id id);

    void updatePersonalEmail(PersonalEmail personalEmail);

    void deletePersonalEmail(Id id);
}
