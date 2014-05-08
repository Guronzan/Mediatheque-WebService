package fr.guronzan.mediatheque.mappingclasses.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;

import fr.guronzan.mediatheque.mappingclasses.SpringTests;
import fr.guronzan.mediatheque.mappingclasses.dao.UserDao;
import fr.guronzan.mediatheque.mappingclasses.domain.User;
import fr.guronzan.mediatheque.utils.DigestUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class UserDaoImplTest extends SpringTests {

    private static final String NAME = "name";
    private static final String FOR_NAME = "forName";
    private static final String NICK = "nick";
    private static final String NICK2 = "nick2";
    private static final String PASSWORD = "password";

    @Resource
    private UserDao userDao;

    /**
     * Clean DB before each Test
     * 
     */
    @Before
    public void cleanDB() {
        this.userDao.removeAllUsers();
    }

    /**
     * Creates a new user
     * 
     * @return id inserted
     */
    private Integer addNewUser() {
        final User user = new User(NAME, FOR_NAME, NICK,
                DigestUtils.hashPassword(PASSWORD), new Date());
        return this.userDao.create(user);
    }

    /**
     * Creates a new user, returns its ID
     * 
     * @return id inserted
     */
    private Integer addNewUser2() {
        final User user = new User(NAME, FOR_NAME, NICK2,
                DigestUtils.hashPassword(PASSWORD), new Date());
        return this.userDao.create(user);
    }

    @Test
    public void testGetUserById() {
        final Integer newUserID = addNewUser();
        final User userById = this.userDao.get(newUserID);

        assertThat(userById, is(notNullValue()));

        final User nullUser = this.userDao.get(-1);
        assertThat(nullUser, is(nullValue()));
    }

    @Test(expected = ConstraintViolationException.class)
    public void testAddDuplicate() {
        addNewUser();
        addNewUser();
    }

    @Test
    public void testGetUsers() {
        final List<User> emptyList = this.userDao.getAll();
        assertThat(emptyList, is(notNullValue()));
        assertTrue(emptyList.isEmpty());

        addNewUser();
        final List<User> oneElementList = this.userDao.getAll();
        assertThat(oneElementList, is(notNullValue()));
        assertThat(oneElementList.size(), is(1));

        addNewUser2();
        final List<User> twoElementList = this.userDao.getAll();
        assertThat(twoElementList, is(notNullValue()));
        assertThat(twoElementList.size(), is(2));
    }

    @Test
    public void testGetUserByFullName() {
        addNewUser();
        final User userByFullName = this.userDao.getUserByFullName(NAME,
                FOR_NAME);
        assertThat(userByFullName, is(notNullValue()));
        assertThat(userByFullName.getName(), is(NAME));
        assertThat(userByFullName.getForName(), is(FOR_NAME));
        assertThat(userByFullName.getForName(), is(not(NAME)));
        assertThat(userByFullName.getNickName(), is(NICK));

        final User notFoundUser = this.userDao
                .getUserByFullName(FOR_NAME, NAME);
        assertThat(notFoundUser, is(nullValue()));
    }

    @Test
    public void testGetUserByNickName() {
        addNewUser();
        final User userByNickName = this.userDao.getUserByNickName(NICK);
        assertThat(userByNickName, is(notNullValue()));
        assertThat(userByNickName.getName(), is(NAME));
        assertThat(userByNickName.getNickName(), is(NICK));

        final User notFoundUser = this.userDao.getUserByNickName(NICK2);
        assertThat(notFoundUser, is(nullValue()));

        addNewUser2();
        final User user2 = this.userDao.getUserByNickName(NICK2);
        assertThat(user2, is(notNullValue()));
        assertThat(user2.getName(), is(NAME));
        assertThat(user2.getNickName(), is(NICK2));
    }

    @Test
    public void testCheckPassword() {
        addNewUser();
        final User userByNickName = this.userDao.getUserByNickName(NICK);
        assertThat(userByNickName.getPassword(),
                is(DigestUtils.hashPassword(PASSWORD)));

        final boolean checkPassword = userByNickName.checkPassword(String
                .valueOf(DigestUtils.hashPassword(PASSWORD)));
        assertTrue(checkPassword);
    }

    @Test
    public void testcontains() {
        assertFalse(this.userDao.contains(NICK));

        addNewUser();
        assertTrue(this.userDao.contains(NICK));

        assertFalse(this.userDao.contains(NICK2));
        addNewUser2();
        assertTrue(this.userDao.contains(NICK));
        assertTrue(this.userDao.contains(NICK2));
    }

}
