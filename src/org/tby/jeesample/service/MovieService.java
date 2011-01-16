package org.tby.jeesample.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.tby.jeesample.model.Movie;

@Stateless
public class MovieService {

    @PersistenceContext
    private EntityManager mEntityManager;

    public void save(Movie aMovie) {
        mEntityManager.persist(aMovie);
    }

    public List<Movie> findAll() {
        CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> query = cb.createQuery(Movie.class);
        query.select(query.from(Movie.class));
        return mEntityManager.createQuery(query).getResultList();
    }
}
