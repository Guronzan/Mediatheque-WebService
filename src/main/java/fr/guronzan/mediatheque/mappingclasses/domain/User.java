package fr.guronzan.mediatheque.mappingclasses.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = "USER_ID") })
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class User implements DomainObject {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "USER_ID", unique = true, nullable = false)
    private int userId;

    @Column(name = "NAME", nullable = false, length = 20)
    private String name;

    @Column(name = "FORNAME", nullable = false, length = 20)
    private String forName;

    @Column(name = "PASSWORD", nullable = false, length = 80)
    private String password;

    @Column(name = "NICK_NAME", nullable = false, length = 20, unique = true)
    private String nickName;

    @Column(name = "REGISTRATION_DATE", nullable = false)
    private Date registrationDate;

    @Lob
    @Column(name = "AVATAR", nullable = true, columnDefinition = "mediumblob")
    private byte[] avatar;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "movie_user", joinColumns = { @JoinColumn(name = "USER_ID", nullable = false, updatable = true) }, inverseJoinColumns = { @JoinColumn(name = "MOVIE_ID", nullable = false, updatable = true) })
    @OrderColumn(name = "MOVIE_COL")
    private List<Movie> movies = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "book_user", joinColumns = { @JoinColumn(name = "USER_ID", nullable = false, updatable = true) }, inverseJoinColumns = { @JoinColumn(name = "BOOK_ID", nullable = false, updatable = true) })
    @OrderColumn(name = "BOOK_COL")
    private List<Book> books = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "cd_user", joinColumns = { @JoinColumn(name = "USER_ID", nullable = false, updatable = true) }, inverseJoinColumns = { @JoinColumn(name = "CD_ID", nullable = false, updatable = true) })
    @OrderColumn(name = "CD_COL")
    private List<CD> cds = new ArrayList<>();

    /**
     *
     * Le hash du password est réalisé par l'appellant explicitement, au moment
     * de la construction de l'objet User, afin que Hibernate ne réalise pas un
     * hash a chaque fois que l'on appelle l'objet à partir de la base
     */
    public User(final String name, final String forName, final String nickName,
            final String password, final Date registrationDate) {
        this.name = name;
        this.forName = forName;
        this.nickName = nickName;
        this.password = password;
        this.registrationDate = registrationDate;
    }

    public void addMovie(final Movie movie) {
        this.movies.add(movie);
    }

    public void addBook(final Book book) {
        this.books.add(book);
    }

    public void addCD(final CD cd) {
        this.cds.add(cd);
    }

    public void setAvatar(final byte[] avatar) {
        if (avatar != null) {
            this.avatar = Arrays.copyOf(avatar, avatar.length);
        } else {
            this.avatar = null;
        }
    }

    public boolean checkPassword(final String password) {
        return this.password.equals(password);
    }

    public void addAvatar(final File inputFile) throws IOException {
        if (inputFile != null) {
            final byte[] bFile = new byte[(int) inputFile.length()];
            try (FileInputStream fileInputStream = new FileInputStream(
                    inputFile)) {
                fileInputStream.read(bFile);
            }
            setAvatar(bFile);
        }
    }

    @Override
    public String getLblExpression() {
        return this.nickName;
    }
}