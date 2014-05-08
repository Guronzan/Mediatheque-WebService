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
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "book", uniqueConstraints = {
        @UniqueConstraint(columnNames = "BOOK_ID"),
        @UniqueConstraint(columnNames = { "TITLE", "TOME" }) })
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Book implements DomainObject {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "BOOK_ID", unique = true, nullable = false)
    private int bookId;

    @Column(name = "TITLE", nullable = false, length = 40)
    private String title;

    @Column(name = "AUTHOR_NAME", nullable = false, length = 30)
    private String authorName;

    @Column(name = "RELEASE_DATE", nullable = false, length = 20)
    private Date releaseDate = new Date();

    @Column(name = "EDITOR", nullable = false, length = 20)
    private String editor;

    @Column(name = "TOME", nullable = true, length = 20)
    private Integer tome = null;

    @Lob
    @Column(name = "PICTURE", nullable = true, columnDefinition = "mediumblob")
    private byte[] picture;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "books")
    private final List<User> owners = new ArrayList<>();

    public Book(final String title) {
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
        sb.append(this.title).append(" - ").append(this.authorName);
        if (this.tome != null) {
            sb.append(" - ").append(this.tome);
        }
        return sb.toString();
    }
}
