package com.github.funnygopher.parti.dao;

/**
 * Created by Kyle on 11/21/2015.
 */
public interface IDAO<E extends IEntity> {
    E create(E entity);
    E get(Long id);
    E update(E entity);
    void delete(Long id);
}
