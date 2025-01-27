package com.project.id.project.application.services.pdf.handlers;

import com.project.id.project.core.Id;

import java.util.Optional;

public interface CrudUpdated<T, ID> {
    void create(String username, T entity);
    Optional<T> read(Id id);
    void update(Id id, T entity, Id idOfSuper);
    void delete(Id id);
}
