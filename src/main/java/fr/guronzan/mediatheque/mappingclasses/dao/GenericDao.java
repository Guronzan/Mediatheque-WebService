package fr.guronzan.mediatheque.mappingclasses.dao;

import java.io.Serializable;
import java.util.ArrayList;

import fr.guronzan.mediatheque.mappingclasses.domain.DomainObject;

public interface GenericDao<T extends DomainObject, K extends Serializable> {
    K create(final T persistentObject);

    T get(final K id);

    ArrayList<T> getAll();

    void update(final T persistentObject);

    void saveOrUpdate(final T persistentObject);

    void delete(final T persistentObject);

}