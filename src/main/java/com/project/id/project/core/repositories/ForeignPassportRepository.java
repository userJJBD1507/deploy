package com.project.id.project.core.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.ForeignPassport;

public interface ForeignPassportRepository {

    void addForeignPassport(ForeignPassport foreignPassport);

    ForeignPassport getForeignPassport(Id id);

    void updateForeignPassport(ForeignPassport foreignPassport);

    void deleteForeignPassport(Id id);
}
