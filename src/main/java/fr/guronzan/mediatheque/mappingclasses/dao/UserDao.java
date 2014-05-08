package fr.guronzan.mediatheque.mappingclasses.dao;

import java.util.ArrayList;

import fr.guronzan.mediatheque.mappingclasses.domain.User;

public interface UserDao extends GenericDao<User, Integer> {
    User getUserByFullName(final String name, final String forName);

    ArrayList<User> getUsers();

    User getUserByNickName(final String nickName);

    User checkPassword(final String nickName, final String encryptedPassword);

    boolean contains(final String nickName);

    void removeAllUsers();

}