package fr.guronzan.mediatheque.mappingclasses.dao.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import fr.guronzan.mediatheque.mappingclasses.dao.MovieDao;
import fr.guronzan.mediatheque.mappingclasses.domain.Movie;

@Repository("movieDao")
@Scope("singleton")
@SuppressWarnings("unchecked")
public class MovieDaoImpl extends GenericDaoImpl<Movie, Integer> implements
MovieDao {

    @Autowired
    public MovieDaoImpl(
            @Qualifier("sessionFactory") final SessionFactory sessionFactory) {
        super(sessionFactory, Movie.class);
    }

    @Override
    public Movie getMovieByTitle(final String title) {
        final StringBuffer hql = new StringBuffer(
                "select movie from Movie movie ");
        hql.append(" where movie.title=:title ");
        final Query query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createQuery(hql.toString());

        query.setString("title", title);
        return (Movie) query.uniqueResult();
    }

    @Override
    public Movie getMovieByTitleAndSeason(final String title, final int seasonId) {
        final StringBuffer hql = new StringBuffer(
                "select movie from Movie movie ");
        hql.append(" where movie.title=:title ");
        hql.append(" and movie.season=:season");
        final Query query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createQuery(hql.toString());

        query.setString("title", title);
        query.setInteger("season", seasonId);
        return (Movie) query.uniqueResult();
    }

    @Override
    public ArrayList<Movie> getMoviesByDirector(final String directorName) {
        final StringBuffer hql = new StringBuffer(
                "select movie from Movie movie ");
        hql.append(" where movie.directorName=:name ");
        final Query query = getHibernateTemplate().getSessionFactory()
                .getCurrentSession().createQuery(hql.toString());

        query.setString("name", directorName);
        return (ArrayList<Movie>) query.list();
    }

    @Override
    public void removeAllMovies() {
        final Collection<Movie> movies = getAll();
        for (final Movie movie : movies) {
            delete(movie);
        }
    }

    @Override
    public boolean contains(final String title) {
        return getMovieByTitle(title) != null;
    }

    @Override
    public boolean contains(final String title, final Integer season) {
        final Movie movieByTitle = getMovieByTitle(title);
        if (movieByTitle == null) {
            return false;
        }

        final Integer seasonId = season == -1 ? null : season;
        return movieByTitle.getSeason() == seasonId;
    }
}