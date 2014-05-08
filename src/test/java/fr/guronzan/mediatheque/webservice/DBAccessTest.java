package fr.guronzan.mediatheque.webservice;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

import fr.guronzan.mediatheque.mappingclasses.SpringTests;
import fr.guronzan.mediatheque.mappingclasses.domain.Book;
import fr.guronzan.mediatheque.mappingclasses.domain.User;
import fr.guronzan.mediatheque.utils.DigestUtils;

public class DBAccessTest extends SpringTests {

    private static final String NAME = "name";
    private static final String FOR_NAME = "forName";
    private static final String OTHER_FOR_NAME = "otherForName";
    private static final String NICK = "nick";
    private static final String OTHER_NAME = "other name";
    private static final String OTHER_NICK = "other nick";
    private static final String PASSWORD = "password";

    private static final String TITLE = "title";
    private static final String TITLE2 = "title2";
    private static final String AUTHOR = "author";
    private static final String AUTHOR2 = "author2";
    private static final String EDITOR = "editor";
    private static final String EDITOR2 = "editor2";

    @Resource
    private DBAccess dbAccess;

    @Before
    public void setUp() {
        this.dbAccess.cleanDB();
    }

    private int addUser() {
        final User user = new User(NAME, FOR_NAME, NICK,
                DigestUtils.hashPassword(PASSWORD), new Date());
        return this.dbAccess.addUser(user);
    }

    private int addOtherUser() {
        final User user = new User(OTHER_NAME, OTHER_FOR_NAME, OTHER_NICK,
                DigestUtils.hashPassword(PASSWORD), new Date());
        return this.dbAccess.addUser(user);
    }

    private Integer addNewBook() {
        final Book book = new Book(TITLE);
        book.setAuthorName(AUTHOR);
        book.setEditor(EDITOR);
        return this.dbAccess.addBook(book);
    }

    private Integer addNewBook2() {
        final Book book = new Book(TITLE2);
        book.setAuthorName(AUTHOR);
        book.setEditor(EDITOR);
        book.setTome(2);
        return this.dbAccess.addBook(book);
    }

    @Test
    public void testAddUser() {
        addUser();

        final User user = this.dbAccess.getUserFromNickName(NICK);
        assertThat(user.getName(), is(NAME));
        assertThat(user.getForName(), is(FOR_NAME));
        assertThat(user.getNickName(), is(NICK));
        assertTrue(user.checkPassword(DigestUtils.hashPassword(PASSWORD)));
    }

    @Test
    public void testUpdateUser() {
        addUser();
        final User user = this.dbAccess.getUserFromNickName(NICK);
        user.setForName(OTHER_FOR_NAME);
        this.dbAccess.updateUserFromUser(user);
        assertNull(this.dbAccess.getUserFromFullName(NAME, FOR_NAME));
        assertNotNull(this.dbAccess.getUserFromFullName(NAME, OTHER_FOR_NAME));

    }

    @Test
    public void testDeleteUser() {
        final int userID = addUser();
        assertTrue(userID > -1);
        final User userFromNickName = this.dbAccess.getUserFromNickName(NICK);
        assertNotNull(userFromNickName);

        this.dbAccess.deleteUser(userFromNickName);
        final User deletedUser = this.dbAccess.getUserFromNickName(NAME);
        assertNull(deletedUser);
    }

    @Test
    public void testGetUserFromID() {
        final int userID = addUser();
        final User userFromID = this.dbAccess.getUserFromID(userID);
        assertNotNull(userFromID);
    }

    @Test
    public void testGetUserFromFullName() {
        addUser();
        final User userFromFullName = this.dbAccess.getUserFromFullName(NAME,
                FOR_NAME);
        assertNotNull(userFromFullName);
        assertThat(userFromFullName.getName(), is(NAME));
        assertThat(userFromFullName.getForName(), is(FOR_NAME));
        assertThat(userFromFullName.getNickName(), is(NICK));
    }

    @Test
    public void testGetUserFromNickName() {
        addUser();
        final User userFromNick = this.dbAccess.getUserFromNickName(NICK);
        assertNotNull(userFromNick);
        assertThat(userFromNick.getName(), is(NAME));
        assertThat(userFromNick.getForName(), is(FOR_NAME));
        assertThat(userFromNick.getNickName(), is(NICK));
    }

    @Test
    public void testGetAllUsers() {
        final int addUser = addUser();
        final int addOtherUser = addOtherUser();

        final Collection<User> allUsers = this.dbAccess.getAllUsers();
        assertThat(allUsers.size(), is(2));
        for (final User user : allUsers) {
            assertThat(user.getNickName(), anyOf(is(NICK), is(OTHER_NICK)));
            assertThat(user.getUserId(), anyOf(is(addUser), is(addOtherUser)));
        }
    }

    @Test
    public void testCheckPasswordFromID() {
        final int addUser = addUser();
        final boolean checkPasswordFromID = this.dbAccess.checkPasswordFromID(
                addUser, DigestUtils.hashPassword(PASSWORD));
        assertTrue(checkPasswordFromID);
    }

    @Test
    public void testCheckPasswordFromFullName() {
        addUser();
        final boolean checkPasswordFromFullName = this.dbAccess
                .checkPasswordFromFullName(NAME, FOR_NAME,
                        DigestUtils.hashPassword(PASSWORD));
        assertTrue(checkPasswordFromFullName);
    }

    @Test
    public void testContainsUser() {
        final boolean userNotFound = this.dbAccess.containsUser(NICK);
        assertFalse(userNotFound);
        addUser();
        final boolean containsUser = this.dbAccess.containsUser(NICK);
        assertTrue(containsUser);
    }

    @Test
    public void testContainsBook() {
        final boolean bookNotFound = this.dbAccess.containsBook(TITLE, null);
        assertFalse(bookNotFound);
        addNewBook();
        final boolean containsBook = this.dbAccess.containsBook(TITLE, null);
        assertTrue(containsBook);
    }

    @Test
    public void testAddBook() {
        final boolean bookNotFound = this.dbAccess.containsBook(TITLE, null);
        assertFalse(bookNotFound);
        addNewBook();
        final boolean containsBook = this.dbAccess.containsBook(TITLE, null);
        assertTrue(containsBook);
    }

    // TODO
    // @Test
    // public void testAddMovie() {
    // throw new RuntimeException("not yet implemented");
    // }
    //
    // @Test
    // public void testContainsMovie() {
    // throw new RuntimeException("not yet implemented");
    // }
    //
    // @Test
    // public void testContainsCD() {
    // throw new RuntimeException("not yet implemented");
    // }
    //
    // @Test
    // public void testAddCD() {
    // throw new RuntimeException("not yet implemented");
    // }

    @Test
    public void testHashPassword() {
        final String validHashed = String
                .valueOf(org.springframework.util.DigestUtils
                        .md5DigestAsHex(PASSWORD.getBytes()));
        final String hashPassword = DigestUtils.hashPassword(PASSWORD);
        assertThat(validHashed, is(hashPassword));
    }

}
