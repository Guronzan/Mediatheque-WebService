package fr.guronzan.mediatheque.mappingclasses.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import fr.guronzan.mediatheque.mappingclasses.dao.BookDao;
import fr.guronzan.mediatheque.mappingclasses.domain.Book;

@Repository("bookDao")
@Scope("singleton")
@SuppressWarnings("unchecked")
public class BookDaoImpl extends GenericDaoImpl<Book, Integer> implements
BookDao {

    @Autowired
    public BookDaoImpl(
            @Qualifier("sessionFactory") final SessionFactory sessionFactory) {
        super(sessionFactory, Book.class);
    }

    @Override
    public Book getBookByTitle(final String title) {
        final StringBuffer hql = new StringBuffer("select book from Book book ");
        hql.append(" where book.title=:title ");
        final Query query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createQuery(hql.toString());

        query.setString("title", title);
        return (Book) query.uniqueResult();
    }

    @Override
    public ArrayList<Book> getBooksByAuthor(final String authorName) {
        final StringBuffer hql = new StringBuffer("select book from Book book");
        hql.append(" where book.authorName=:name ");
        final Query query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createQuery(hql.toString());

        query.setString("name", authorName);
        final ArrayList<Book> books = (ArrayList<Book>) query.list();
        if (books.isEmpty()) {
            return new ArrayList<>();
        }
        return books;
    }

    @Override
    public void removeAllBooks() {
        final List<Book> fullList = getAll();
        for (final Book book : fullList) {
            delete(book);
        }
    }

    @Override
    public boolean contains(final String title, final int tome) {
        final Book book = getBookByTitle(title);
        if (book == null) {
            return false;
        }
        if (tome == -1) {
            return true;
        }

        return book.getTome() == tome;

    }

    @Override
    public ArrayList<Book> getBooksByEditor(final String editor) {
        final StringBuffer hql = new StringBuffer("select book from Book book");
        hql.append(" where book.editor=:name ");
        final Query query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createQuery(hql.toString());

        query.setString("name", editor);
        final ArrayList<Book> books = (ArrayList<Book>) query.list();
        if (books.isEmpty()) {
            return new ArrayList<>();
        }
        return books;
    }
}
