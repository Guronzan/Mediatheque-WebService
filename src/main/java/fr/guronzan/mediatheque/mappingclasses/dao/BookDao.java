package fr.guronzan.mediatheque.mappingclasses.dao;

import java.util.ArrayList;

import fr.guronzan.mediatheque.mappingclasses.domain.Book;

public interface BookDao extends GenericDao<Book, Integer> {

    Book getBookByTitle(final String name);

    ArrayList<Book> getBooksByAuthor(final String name);

    void removeAllBooks();

    boolean contains(final String title, final Integer tome);

    ArrayList<Book> getBooksByEditor(final String editor);

}