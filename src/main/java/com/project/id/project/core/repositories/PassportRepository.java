package com.project.id.project.core.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.Passport;

public interface PassportRepository {

    void addPassport(Passport passport);

    Passport getPassport(Id id);

    void updatePassport(Passport passport);

    void deletePassport(Id id);
}
