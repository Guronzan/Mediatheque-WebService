package fr.guronzan.mediatheque.mappingclasses.domain.encapsulatedTypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import fr.guronzan.mediatheque.mappingclasses.domain.Book;

@XmlRootElement
public class ListBooks implements Iterable<Book>, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -8335775611619365424L;
    private List<Book> books = null;

    public ListBooks() {
    }

    public ListBooks(final ArrayList<Book> Books) {
        this.books = Books;
    }

    public List<Book> getBooks() {
        return this.books;
    }

    public void setBooks(final List<Book> Books) {
        this.books = Books;
    }

    @Override
    public Iterator<Book> iterator() {
        return this.books.iterator();
    }
}
