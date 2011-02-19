package com.tby.jeesample.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.tby.jeesample.model.Pomodoro;
import com.tby.jeesample.model.ToDoList;

@Stateless
public class PomodoroService {

    @PersistenceContext
    private EntityManager entityManager;

    public Pomodoro create() {
        return new Pomodoro();
    }

    public void delete(Pomodoro aPomodoro) {
        Pomodoro pomodoro = entityManager.merge(aPomodoro);
        entityManager.remove(pomodoro);
    }

    public ToDoList find(Long aId) {
        return entityManager.find(ToDoList.class, aId);
    }

    public void save(Pomodoro aPomodoro) {
        entityManager.persist(aPomodoro);
    }

    public void update(Pomodoro aPomodoro) {
        entityManager.merge(aPomodoro);
    }

    public List<Pomodoro> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pomodoro> query = cb.createQuery(Pomodoro.class);
        query.select(query.from(Pomodoro.class));
        return entityManager.createQuery(query).getResultList();
    }
}
