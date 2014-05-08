package fr.guronzan.mediatheque.mappingclasses.domain.encapsulatedTypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import fr.guronzan.mediatheque.mappingclasses.domain.Movie;

@XmlRootElement
public class ListMovies implements Iterable<Movie>, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -6292716161857452749L;
    private List<Movie> movies = null;

    public ListMovies() {
    }

    public ListMovies(final ArrayList<Movie> Movies) {
        this.movies = Movies;
    }

    public List<Movie> getMovies() {
        return this.movies;
    }

    public void setMovies(final List<Movie> Movies) {
        this.movies = Movies;
    }

    @Override
    public Iterator<Movie> iterator() {
        return this.movies.iterator();
    }
}
