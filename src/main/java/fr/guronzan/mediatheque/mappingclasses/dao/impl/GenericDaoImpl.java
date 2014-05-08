package fr.guronzan.mediatheque.mappingclasses.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import fr.guronzan.mediatheque.mappingclasses.dao.GenericDao;
import fr.guronzan.mediatheque.mappingclasses.domain.DomainObject;

@Transactional
/**
 *
 * @author Guillaume
 *
 * @param <T>
 * @param <K> K : PrimaryKey
 */
public abstract class GenericDaoImpl<T extends DomainObject, K extends Serializable>
extends HibernateDaoSupport implements GenericDao<T, K> {

    private final Class<T> type;

    public GenericDaoImpl(final SessionFactory sessionFactory,
            final Class<T> type) {
        super.setSessionFactory(sessionFactory);
        this.type = type;
    }

    @Override
    @SuppressWarnings("unchecked")
    public K create(final T o) {
        return (K) getHibernateTemplate().getSessionFactory()
                .getCurrentSession().save(o);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(final K id) {
        T value = (T) getHibernateTemplate().getSessionFactory()
                .getCurrentSession().get(this.type, id);
        if (value == null) {
            return null;
        }

        if (value instanceof HibernateProxy) {
            Hibernate.initialize(value);
            value = (T) ((HibernateProxy) value).getHibernateLazyInitializer()
                    .getImplementation();
        }
        return value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<T> getAll() {
        return (ArrayList<T>) getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createCriteria(this.type).list();
    }

    @Override
    public void saveOrUpdate(final T o) {
        getHibernateTemplate().getSessionFactory().getCurrentSession()
                .saveOrUpdate(o);
    }

    @Override
    public void update(final T o) {
        getHibernateTemplate().getSessionFactory().getCurrentSession()
                .update(o);
    }

    @Override
    public void delete(final T o) {
        getHibernateTemplate().getSessionFactory().getCurrentSession()
                .delete(o);
    }
}
