package com.mjc.school.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T extends BaseEntity<K>, K> {

    List<T> readAll();

    Optional<T> readById(K id);

    T create(T entity);

    T update(T entity);

    boolean deleteById(K id);

    boolean existById(K id);
}
