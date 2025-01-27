package com.project.id.project.application.services.Security;

import com.project.id.project.core.Id;

import java.util.Optional;

public interface NewCrudSer<T, ID> {
    void create(String username, T entity);
    Optional<T> read(Id id);
    void update(Id id, T entity);
    void delete(Id id);
}
