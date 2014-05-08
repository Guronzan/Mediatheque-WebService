package fr.guronzan.mediatheque.webservice;

import java.util.LinkedList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.springframework.stereotype.Repository;

import fr.guronzan.mediatheque.DBAccessApplicationContext;
import fr.guronzan.mediatheque.mappingclasses.dao.BookDao;
import fr.guronzan.mediatheque.mappingclasses.dao.CDDao;
import fr.guronzan.mediatheque.mappingclasses.dao.MovieDao;
import fr.guronzan.mediatheque.mappingclasses.dao.UserDao;
import fr.guronzan.mediatheque.mappingclasses.domain.Book;
import fr.guronzan.mediatheque.mappingclasses.domain.Cd;
import fr.guronzan.mediatheque.mappingclasses.domain.DomainObject;
import fr.guronzan.mediatheque.mappingclasses.domain.Movie;
import fr.guronzan.mediatheque.mappingclasses.domain.User;
import fr.guronzan.mediatheque.mappingclasses.domain.encapsulatedTypes.DomainObjects;
import fr.guronzan.mediatheque.mappingclasses.domain.encapsulatedTypes.ListUsers;
import fr.guronzan.mediatheque.mappingclasses.domain.types.DataType;

@Repository("DbAccess")
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class DBAccess {

    private static final UserDao USER_DAO = DBAccessApplicationContext
            .getBean(UserDao.class);

    private static final MovieDao MOVIE_DAO = DBAccessApplicationContext
            .getBean(MovieDao.class);

    private static final CDDao CD_DAO = DBAccessApplicationContext
            .getBean(CDDao.class);

    private static final BookDao BOOK_DAO = DBAccessApplicationContext
            .getBean(BookDao.class);

    @WebMethod(operationName = "addUser")
    public Integer addUser(final User user) {
        return USER_DAO.create(user);
    }

    @WebMethod(operationName = "updateUserFromUser")
    public void updateUserFromUser(final User user) {
        USER_DAO.saveOrUpdate(user);
    }

    @WebMethod(operationName = "updateUserFromData")
    public void updateUserFromData(final String currentUser,
            final String selectedElement, final DataType dataType) {
        final User user = getUserFromNickName(currentUser);
        switch (dataType) {
        case BOOK:
            final Book book = BOOK_DAO.getBookByTitle(selectedElement
                    .split(" - ")[0]);
            assert book != null;
            user.addBook(book);
            break;
        case MOVIE:
            final String[] split = selectedElement.split(" - ");
            Movie movie;
            if (split.length == 2) {
                movie = MOVIE_DAO.getMovieByTitle(split[0]);
            } else {
                movie = MOVIE_DAO.getMovieByTitleAndSeason(split[0],
                        Integer.parseInt(split[1]));
            }
            assert movie != null;
            user.addMovie(movie);
            break;
        case MUSIC:
            final Cd cd = CD_DAO.getCdByTitle(selectedElement.split(" - ")[0]);
            assert cd != null;
            user.addCD(cd);
            break;
        default:
            throw new IllegalArgumentException("Unknow state : "
                    + dataType.getValue());
        }
        updateUserFromUser(user);
    }

    @WebMethod(operationName = "deleteUser")
    public void deleteUser(final User user) {
        USER_DAO.delete(user);
    }

    @WebMethod(operationName = "getUserFromID")
    public User getUserFromID(final int id) {
        return USER_DAO.get(id);
    }

    @WebMethod(operationName = "getUserFromFullName")
    public User getUserFromFullName(final String name, final String forName) {
        return USER_DAO.getUserByFullName(name, forName);
    }

    @WebMethod(operationName = "getUserFromNickName")
    public User getUserFromNickName(final String nickName) {
        return USER_DAO.getUserByNickName(nickName);
    }

    @WebMethod(operationName = "getAllUsers")
    public ListUsers getAllUsers() {
        return new ListUsers(USER_DAO.getUsers());
    }

    @WebMethod(operationName = "checkPasswordFromID")
    public boolean checkPasswordFromID(final int userId, final String password) {
        final User user = USER_DAO.get(userId);
        return user.checkPassword(password);
    }

    @WebMethod(operationName = "checkPasswordFromFullName")
    public boolean checkPasswordFromFullName(final String name,
            final String forName, final String password) {
        final User user = USER_DAO.getUserByFullName(name, forName);
        if (user != null) {
            return user.checkPassword(password);
        }
        return false;
    }

    @WebMethod(operationName = "containsUser")
    public boolean containsUser(final String nickName) {
        return USER_DAO.contains(nickName);
    }

    @WebMethod(operationName = "containsBook")
    public boolean containsBook(final String title, final Integer tomeValue) {
        return BOOK_DAO.contains(title, tomeValue);
    }

    @WebMethod(operationName = "addBook")
    public Integer addBook(final Book book) {
        return BOOK_DAO.create(book);
    }

    @WebMethod(operationName = "addMovie")
    public Integer addMovie(final Movie movie) {
        return MOVIE_DAO.create(movie);
    }

    @WebMethod(operationName = "containsMovie")
    public boolean containsMovie(final String title, final Integer season) {
        return MOVIE_DAO.contains(title, season);
    }

    @WebMethod(operationName = "containsCD")
    public boolean containsCD(final String title) {
        return CD_DAO.contains(title);
    }

    @WebMethod(operationName = "addCD")
    public Integer addCD(final Cd cd) {
        return CD_DAO.create(cd);
    }

    @WebMethod(operationName = "cleanDB")
    public void cleanDB() {
        BOOK_DAO.removeAllBooks();
        CD_DAO.removeAllCDs();
        MOVIE_DAO.removeAllMovies();
        USER_DAO.removeAllUsers();
    }

    @WebMethod(operationName = "getAllNotOwned")
    public LinkedList<? extends DomainObject> getAllNotOwned(
            final DataType dataType, final String currentUserNick) {
        switch (dataType) {
        case BOOK: {
            final List<Book> all = BOOK_DAO.getAll();
            final LinkedList<Book> books = new LinkedList<>();
            for (final Book book : all) {
                for (final User owner : book.getOwners()) {
                    if (owner.getNickName().equals(currentUserNick)) {
                        continue;
                    }
                    books.add(book);
                }
            }
            return books;
        }
        case MOVIE: {
            final List<Movie> all = MOVIE_DAO.getAll();
            final LinkedList<Movie> movies = new LinkedList<>();
            for (final Movie movie : all) {
                for (final User owner : movie.getOwners()) {
                    if (owner.getNickName().equals(currentUserNick)) {
                        continue;
                    }
                    movies.add(movie);
                }
            }
            return movies;
        }
        case MUSIC: {
            final List<Cd> all = CD_DAO.getAll();
            final LinkedList<Cd> cds = new LinkedList<>();
            for (final Cd cd : all) {
                for (final User owner : cd.getOwners()) {
                    if (owner.getNickName().equals(currentUserNick)) {
                        continue;
                    }
                    cds.add(cd);
                }
            }
            return cds;
        }
        default:
            throw new IllegalArgumentException("Unknow state : "
                    + dataType.getValue());
        }
    }

    @WebMethod(operationName = "getAll")
    public DomainObjects getAll(final DataType dataType) {
        switch (dataType) {
        case BOOK:
            return new DomainObjects(BOOK_DAO.getAll());
        case MOVIE:
            return new DomainObjects(MOVIE_DAO.getAll());
        case MUSIC:
            return new DomainObjects(CD_DAO.getAll());
        default:
            throw new IllegalArgumentException("Unknow state : "
                    + dataType.getValue());
        }
    }
}
