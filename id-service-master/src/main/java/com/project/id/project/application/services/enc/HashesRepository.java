package com.project.id.project.application.services.enc;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.id.project.core.Id;
import java.util.List;


@Repository
public interface HashesRepository extends JpaRepository<Hashes, Id>{
    @Query("SELECT h FROM Hashes h WHERE h.username = :username")
    Hashes getHashWithUname(@Param("username") String username);

}
