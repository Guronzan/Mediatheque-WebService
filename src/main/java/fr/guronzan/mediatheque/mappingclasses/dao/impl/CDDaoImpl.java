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
import fr.guronzan.mediatheque.mappingclasses.domain.CD;

@Repository("cdDao")
@Scope("singleton")
@SuppressWarnings("unchecked")
public class CDDaoImpl extends GenericDaoImpl<CD, Integer> implements CDDao {

    @Autowired
    public CDDaoImpl(
            @Qualifier("sessionFactory") final SessionFactory sessionFactory) {
        super(sessionFactory, CD.class);
    }

    @Override
    public CD getCdByTitle(final String title) {
        final StringBuffer hql = new StringBuffer("select cd from CD cd ");
        hql.append(" where cd.title=:title ");
        final Query query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createQuery(hql.toString());

        query.setString("title", title);
        return (CD) query.uniqueResult();
    }

    @Override
    public ArrayList<CD> getCdsByAuthor(final String name) {
        final StringBuffer hql = new StringBuffer("select cd from CD cd ");
        hql.append(" where cd.authorName=:name ");
        final Query query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createQuery(hql.toString());

        query.setString("name", name);
        final ArrayList<CD> list = (ArrayList<CD>) query.list();
        if (list.isEmpty()) {
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public void removeAllCDs() {
        final List<CD> allCDs = getAll();
        for (final CD cd : allCDs) {
            delete(cd);
        }
    }

    @Override
    public boolean contains(final String title) {
        return getCdByTitle(title) != null;
    }
}