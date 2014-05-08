package fr.guronzan.mediatheque.mappingclasses.dao;

import java.util.ArrayList;

import fr.guronzan.mediatheque.mappingclasses.domain.Cd;

public interface CDDao extends GenericDao<Cd, Integer> {

    Cd getCdByTitle(final String title);

    ArrayList<Cd> getCdsByAuthor(final String name);

    void removeAllCDs();

    boolean contains(final String title);

}