package fr.guronzan.mediatheque.mappingclasses.dao;

import java.util.ArrayList;

import fr.guronzan.mediatheque.mappingclasses.domain.Movie;

public interface MovieDao extends GenericDao<Movie, Integer> {

    ArrayList<Movie> getMoviesByDirector(final String directorName);

    Movie getMovieByTitle(final String title);

    Movie getMovieByTitleAndSeason(final String title, final Integer seasonId);

    void removeAllMovies();

    boolean contains(final String title);

    boolean contains(final String title, final Integer season);
}