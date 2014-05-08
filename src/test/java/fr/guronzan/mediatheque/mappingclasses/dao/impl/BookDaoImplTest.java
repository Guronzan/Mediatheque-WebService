package fr.guronzan.mediatheque.mappingclasses.dao.impl;

import javax.annotation.Resource;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;

import fr.guronzan.mediatheque.mappingclasses.SpringTests;
import fr.guronzan.mediatheque.mappingclasses.dao.BookDao;
import fr.guronzan.mediatheque.mappingclasses.domain.Book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BookDaoImplTest extends SpringTests {

    private static final String TITLE = "title";
    private static final String TITLE2 = "title2";
    private static final String AUTHOR = "author";
    private static final String AUTHOR2 = "author2";
    private static final String EDITOR = "editor";
    private static final String EDITOR2 = "editor2";

    @Resource
    private BookDao bookDao;

    @Before
    public void cleanDB() {
        this.bookDao.removeAllBooks();
    }

    private Integer addNewBook() {
        final Book book = new Book(TITLE);
        book.setAuthorName(AUTHOR);
        book.setEditor(EDITOR);
        return this.bookDao.create(book);
    }

    private Integer addNewBook2() {
        final Book book = new Book(TITLE2);
        book.setAuthorName(AUTHOR);
        book.setEditor(EDITOR);
        book.setTome(2);
        return this.bookDao.create(book);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testAddDuplicate() {
        final Book book = new Book(TITLE);
        book.setAuthorName(AUTHOR);
        book.setEditor(EDITOR);
        book.setTome(1);
        this.bookDao.create(book);
        this.bookDao.create(book);
    }

    @Test
    public void testContains() {
        // Find book with no tome defined
        assertFalse(this.bookDao.contains(TITLE, -1));
        addNewBook();
        assertTrue(this.bookDao.contains(TITLE, -1));

        // Find book with tome defined
        assertFalse(this.bookDao.contains(TITLE2, -1));
        addNewBook2();
        assertTrue(this.bookDao.contains(TITLE2, 2));
        assertFalse(this.bookDao.contains(TITLE2, 1));
    }

    @Test
    public final void testGetBookByTitle() {
        assertNull(this.bookDao.getBookByTitle(TITLE));
        addNewBook();
        assertNotNull(this.bookDao.getBookByTitle(TITLE));
        assertNull(this.bookDao.getBookByTitle(TITLE2));
    }

    @Test
    public final void testGetBooksByAuthor() {
        assertTrue(this.bookDao.getBooksByAuthor(AUTHOR).isEmpty());
        addNewBook();
        assertFalse(this.bookDao.getBooksByAuthor(AUTHOR).isEmpty());
        assertTrue(this.bookDao.getBooksByAuthor(AUTHOR2).isEmpty());
    }

    @Test
    public final void testGetBookByEditor() {
        assertTrue(this.bookDao.getBooksByEditor(EDITOR).isEmpty());
        addNewBook();
        assertFalse(this.bookDao.getBooksByEditor(EDITOR).isEmpty());
        assertTrue(this.bookDao.getBooksByEditor(EDITOR2).isEmpty());
    }
}
