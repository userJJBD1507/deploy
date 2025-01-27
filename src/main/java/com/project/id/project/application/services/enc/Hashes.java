package com.project.id.project.application.services.enc;

import com.project.id.project.core.Id;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "hashes_table")
public class Hashes {
    @EmbeddedId
    @Column(name = "id")
    private Id id;
    @Column(name = "username")
    private String username;
    @Column(name = "hash")
    private String hash;
    
    public Hashes() {
        this.id = new Id();
    }
    public Hashes(String username, String hash) {
        this.username = username;
        this.hash = hash;
    }
    public Id getId() {
        return id;
    }
    public void setId(Id id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getHash() {
        return hash;
    }
    public void setHash(String hash) {
        this.hash = hash;
    }
    
}
