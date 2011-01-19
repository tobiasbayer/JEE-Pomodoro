package org.tby.jeesample.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class MovieService {

    @PersistenceContext
    private EntityManager mEntityManager;

    // public void save(Movie aMovie) {
    // mEntityManager.persist(aMovie);
    // }
    //
    // public List<Movie> findAll() {
    // CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
    // CriteriaQuery<Movie> query = cb.createQuery(Movie.class);
    // query.select(query.from(Movie.class));
    // return mEntityManager.createQuery(query).getResultList();
    // }
}
