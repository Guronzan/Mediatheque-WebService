package fr.guronzan.mediatheque.mappingclasses.domain;

import java.io.Serializable;

public abstract class DomainObject implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1379553240451967865L;

    public abstract String getLblExpression();
}
