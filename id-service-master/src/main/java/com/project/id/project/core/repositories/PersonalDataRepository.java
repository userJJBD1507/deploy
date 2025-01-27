package com.project.id.project.core.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.personal.PersonalData;

public interface PersonalDataRepository {

    void addPersonalData(PersonalData personalData);

    PersonalData getPersonalData(Id id);

    void deletePersonalData(Id id);
}
