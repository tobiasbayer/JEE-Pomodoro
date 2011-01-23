package org.tby.jeesample.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.tby.jeesample.model.Pomodoro;
import org.tby.jeesample.model.ToDoList;

@Stateless
public class PomodoroService {

    @PersistenceContext
    private EntityManager mEntityManager;

    public Pomodoro create() {
        return new Pomodoro();
    }

    public void delete(Pomodoro aPomodoro) {
        Pomodoro pomodoro = mEntityManager.merge(aPomodoro);
        mEntityManager.remove(pomodoro);
    }

    public ToDoList find(Long aId) {
        return mEntityManager.find(ToDoList.class, aId);
    }

    public void save(Pomodoro aPomodoro) {
        mEntityManager.persist(aPomodoro);
    }

    public void update(Pomodoro aPomodoro) {
        mEntityManager.merge(aPomodoro);
    }

    public List<Pomodoro> findAll() {
        CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<Pomodoro> query = cb.createQuery(Pomodoro.class);
        query.select(query.from(Pomodoro.class));
        return mEntityManager.createQuery(query).getResultList();
    }
}
