package com.project.id.project.core.repositories.EntityRepositories;

import com.project.id.project.core.Id;
import com.project.id.project.core.addresses.entities.AdditionalAddress;
import com.project.id.project.core.addresses.entities.HomeAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EntityHomeAddressRepository extends JpaRepository<HomeAddress, Id> {
    @Modifying
    @Transactional
    @Query("delete HomeAddress w where w.id=:id")
    void delete(@Param("id") Id id);
}
