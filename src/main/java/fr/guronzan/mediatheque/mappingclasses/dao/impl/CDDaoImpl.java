package fr.guronzan.mediatheque.mappingclasses.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import fr.guronzan.mediatheque.mappingclasses.dao.CDDao;
import fr.guronzan.mediatheque.mappingclasses.domain.Cd;

@Repository("cdDao")
@Scope("singleton")
@SuppressWarnings("unchecked")
public class CDDaoImpl extends GenericDaoImpl<Cd, Integer> implements CDDao {

    @Autowired
    public CDDaoImpl(
            @Qualifier("sessionFactory") final SessionFactory sessionFactory) {
        super(sessionFactory, Cd.class);
    }

    @Override
    public Cd getCdByTitle(final String title) {
        final StringBuffer hql = new StringBuffer("select cd from Cd cd ");
        hql.append(" where cd.title=:title ");
        final Query query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createQuery(hql.toString());

        query.setString("title", title);
        return (Cd) query.uniqueResult();
    }

    @Override
    public ArrayList<Cd> getCdsByAuthor(final String name) {
        final StringBuffer hql = new StringBuffer("select cd from Cd cd ");
        hql.append(" where cd.authorName=:name ");
        final Query query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createQuery(hql.toString());

        query.setString("name", name);
        final ArrayList<Cd> list = (ArrayList<Cd>) query.list();
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public void removeAllCDs() {
        final List<Cd> allCDs = getAll();
        for (final Cd cd : allCDs) {
            delete(cd);
        }
    }

    @Override
    public boolean contains(final String title) {
        return getCdByTitle(title) != null;
    }
}