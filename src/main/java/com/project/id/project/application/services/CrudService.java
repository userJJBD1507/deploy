package com.project.id.project.application.services;

import com.project.id.project.core.Id;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {
    void create(T entity);
    Optional<T> read(Id id);
    void update(Id id, T entity);
    void delete(Id id);
}
