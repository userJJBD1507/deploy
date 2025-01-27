package com.project.id.project.core.repositories.EntityRepositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.documents.entities.VoluntaryHealthInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EntityVoluntaryHealthInsuranceRepository extends JpaRepository<VoluntaryHealthInsurance, Id> {
    @Modifying
    @Transactional
    @Query("delete VoluntaryHealthInsurance w where w.id=:id")
    void delete(@Param("id") Id id);
}
