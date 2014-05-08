package fr.guronzan.mediatheque.mappingclasses.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import fr.guronzan.mediatheque.mappingclasses.dao.UserDao;
import fr.guronzan.mediatheque.mappingclasses.domain.User;

@Repository("userDao")
@Scope("singleton")
public class UserDaoImpl extends GenericDaoImpl<User, Integer> implements
        UserDao {

    @Autowired
    public UserDaoImpl(
            @Qualifier("sessionFactory") final SessionFactory sessionFactory) {
        super(sessionFactory, User.class);
    }

    @Override
    public ArrayList<User> getUsers() {
        return getAll();
    }

    @Override
    public User getUserByFullName(final String name, final String forName) {
        final StringBuffer hql = new StringBuffer("select user from User user ");
        hql.append(" where user.name=:name ");
        hql.append(" and user.forName=:forName ");
        final Query query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createQuery(hql.toString());

        query.setString("name", name);
        query.setString("forName", forName);
        return (User) query.uniqueResult();
    }

    @Override
    public User getUserByNickName(final String nickName) {
        final StringBuffer hql = new StringBuffer("select user from User user ");
        hql.append(" where user.nickName=:nickName ");
        final Query query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createQuery(hql.toString());

        query.setString("nickName", nickName);
        return (User) query.uniqueResult();
    }

    @Override
    public User checkPassword(final String nickName,
            final String encryptedPassword) {
        final User userByNickName = getUserByNickName(nickName);
        if (userByNickName == null) {
            return null;
        }
        if (userByNickName.checkPassword(encryptedPassword)) {
            return userByNickName;
        }
        return null;
    }

    @Override
    public boolean contains(final String nickName) {
        return getUserByNickName(nickName) != null;
    }

    @Override
    public void removeAllUsers() {
        final List<User> allUsers = getAll();
        for (final User user : allUsers) {
            delete(user);
        }
    }

}