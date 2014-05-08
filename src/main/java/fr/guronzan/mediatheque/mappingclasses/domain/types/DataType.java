package fr.guronzan.mediatheque.mappingclasses.domain.types;

import fr.guronzan.mediatheque.mappingclasses.dao.BookDao;
import fr.guronzan.mediatheque.mappingclasses.dao.CDDao;
import fr.guronzan.mediatheque.mappingclasses.dao.GenericDao;
import fr.guronzan.mediatheque.mappingclasses.dao.MovieDao;
import fr.guronzan.mediatheque.mappingclasses.domain.DomainObject;

public enum DataType {
    MOVIE("Movie", MovieDao.class, "reFillMovieList"), MUSIC("Music",
            CDDao.class, "reFillCDList"), BOOK("Book", BookDao.class,
            "reFillBookList");

    private final String value;
    private Class<? extends GenericDao<? extends DomainObject, Integer>> dao;
    private final String reFillMethodName;

    private DataType(
            final String value,
            final Class<? extends GenericDao<? extends DomainObject, Integer>> dao,
            final String reFillMethodName) {
        this.value = value;
        this.dao = dao;
        this.reFillMethodName = reFillMethodName;
    }

    public String getValue() {
        return this.value;
    }

    public Class<? extends GenericDao<?, Integer>> getDao() {
        return this.dao;
    }

    public String getRefillMethodName() {
        return this.reFillMethodName;
    }
}
