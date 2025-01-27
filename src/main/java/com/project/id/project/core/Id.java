package com.project.id.project.core;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public record Id(UUID id) implements Serializable {
    public Id() {
        this(UUID.randomUUID());
    }
    public Id(UUID id) {
        this.id = id;
    }
}
