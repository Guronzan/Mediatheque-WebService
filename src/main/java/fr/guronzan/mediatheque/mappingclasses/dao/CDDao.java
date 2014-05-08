package fr.guronzan.mediatheque.mappingclasses.dao;

import java.util.ArrayList;

import fr.guronzan.mediatheque.mappingclasses.domain.CD;

public interface CDDao extends GenericDao<CD, Integer> {

    CD getCdByTitle(final String title);

    ArrayList<CD> getCdsByAuthor(final String name);

    void removeAllCDs();

    boolean contains(final String title);

}