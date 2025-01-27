package com.project.id.project.application.services.pdf.handlers;

import com.project.id.project.core.Id;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.EntityGraph;

@Entity
@Table(name = "pdf_table")
public class Pdf {
    @EmbeddedId
    private Id id;
    @Column(name = "resource_id")
    private String resourceId;
    @Column(name = "s3_file_link")
    private String s3Link;

    public Pdf() {
        this.id = new Id();
    }

    public Pdf(String resourceId, String s3Link) {
        this.resourceId = resourceId;
        this.s3Link = s3Link;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getS3Link() {
        return s3Link;
    }

    public void setS3Link(String s3Link) {
        this.s3Link = s3Link;
    }
}