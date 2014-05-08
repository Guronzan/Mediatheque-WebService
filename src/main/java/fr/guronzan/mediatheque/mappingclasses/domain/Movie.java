package fr.guronzan.mediatheque.mappingclasses.domain;

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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import fr.guronzan.mediatheque.mappingclasses.domain.types.VideoType;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "movie", uniqueConstraints = {
        @UniqueConstraint(columnNames = "MOVIE_ID"),
        @UniqueConstraint(columnNames = { "TITLE", "SEASON" }) })
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Movie implements DomainObject {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "MOVIE_ID", unique = true, nullable = false)
    private int movieId;

    @Column(name = "TITLE", unique = true, nullable = false, length = 40)
    private String title;

    @Column(name = "DIRECTOR_NAME", nullable = true, length = 30)
    private String directorName;

    @Column(name = "RELEASE_DATE", nullable = false, length = 20)
    private Date releaseDate = new Date();

    @Column(name = "OWNED_DVD", nullable = false)
    private boolean ownedDVD = false;

    @Column(name = "SEASON", nullable = true)
    private Integer season;

    @Column(name = "VIDEO_KIND", nullable = false)
    private VideoType type;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "movies")
    private List<User> owners = new ArrayList<>();

    @Lob
    @Column(name = "PICTURE", nullable = true, columnDefinition = "mediumblob")
    private byte[] picture;

    public Movie(final String title, final String director,
            final Date releaseDate) {
        this.title = title;
        this.directorName = director;
        this.releaseDate = releaseDate;
        this.ownedDVD = false;
    }

    public Movie(final String title) {
        this.title = title;
    }

    public void setPicture(final byte[] picture) {
        if (picture != null) {
            this.picture = Arrays.copyOf(picture, picture.length);
        } else {
            this.picture = null;
        }
    }

    public void addPicture(final File inputFile) throws IOException {
        if (inputFile != null) {
            final byte[] bFile = new byte[(int) inputFile.length()];
            try (FileInputStream fileInputStream = new FileInputStream(
                    inputFile)) {
                fileInputStream.read(bFile);
            }
            setPicture(bFile);
        }
    }

    @Override
    public String getLblExpression() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.title);
        if (this.season != null) {
            sb.append(" - ").append(this.season);
        }
        sb.append(" - ").append(this.directorName);
        return sb.toString();
    }
}
