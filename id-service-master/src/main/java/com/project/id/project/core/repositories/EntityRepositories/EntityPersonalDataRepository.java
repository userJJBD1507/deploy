package com.project.id.project.core.repositories.EntityRepositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.personal.PersonalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface EntityPersonalDataRepository extends JpaRepository<PersonalData, Id> {
    Optional<PersonalData> findByInvocation(String username);
    @Modifying
    @Transactional
    @Query("delete PersonalData w where w.id=:id")
    void delete(@Param("id") Id id);
}
