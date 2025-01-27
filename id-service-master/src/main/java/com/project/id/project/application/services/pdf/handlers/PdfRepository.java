package com.project.id.project.application.services.pdf.handlers;

import com.project.id.project.core.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PdfRepository extends JpaRepository<Pdf, Id> {
    public Optional<Pdf> findByResourceId(String id);
}
