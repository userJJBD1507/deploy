package com.project.id.project.core.repositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.emails.entities.AdditionalEmail;

public interface AdditionalEmailRepository {

    void addAdditionalEmail(AdditionalEmail additionalEmail);

    AdditionalEmail getAdditionalEmail(Id id);

    void updateAdditionalEmail(AdditionalEmail additionalEmail);

    void deleteAdditionalEmail(Id id);
}
