package com.tby.jeesample.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;


import com.tby.jeesample.common.PomodoroAddDateComparator;
import com.tby.jeesample.model.Pomodoro;
import com.tby.jeesample.model.SystemUser;
import com.tby.jeesample.model.ToDo;

@Stateless
public class ToDoService {

    @PersistenceContext
    private EntityManager entityManager;

    public ToDo create() {
        return new ToDo();
    }

    public void delete(ToDo aToDo) {
        ToDo todo = entityManager.merge(aToDo);
        entityManager.remove(todo);
    }

    public ToDo find(Long aId) {
        return entityManager.find(ToDo.class, aId);
    }

    public void update(ToDo aToDo) {
        // TODO replace with user from session once user management is
        // implemented
        if (aToDo.getOwner() == null) {
            SystemUser user = new SystemUser();
            user.setFullName("Name");
            user.setLogin("Login");
            user.setPassword("Password");
            aToDo.setOwner(user);
        }
        entityManager.merge(aToDo);
    }

    public List<ToDo> findAll() {
        // TODO find only for the current user
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ToDo> query = cb.createQuery(ToDo.class);
        query.select(query.from(ToDo.class));
        return entityManager.createQuery(query).getResultList();
    }

    public void addPomodoro(ToDo aToDo, Pomodoro aPomodoro) {
        ToDo todo = entityManager.merge(aToDo);
        aPomodoro.setAddDate(new Date());
        todo.addPomodoro(aPomodoro);
        update(todo);
    }

    public Pomodoro getLatestPomodoro(ToDo aToDo) {
        ToDo toDo = entityManager.merge(aToDo);
        List<Pomodoro> pomodoroList = new ArrayList<Pomodoro>(toDo.getPomodoro());
        if (pomodoroList.isEmpty()) {
            return null;
        }

        Collections.sort(pomodoroList, new PomodoroAddDateComparator());

        return pomodoroList.get(0);
    }

    public void finishCurrentPomodoro(ToDo aToDo) {
        ToDo todo = entityManager.merge(aToDo);
        Pomodoro latestPomodoro = getLatestPomodoro(todo);
        if (latestPomodoro != null) {
            latestPomodoro.setFinished(true);
        }
    }

    public void voidCurrentPomodoro(ToDo aToDo) {
        ToDo todo = entityManager.merge(aToDo);
        Pomodoro latestPomodoro = getLatestPomodoro(todo);
        if (latestPomodoro != null) {
            latestPomodoro.setVoidPomodoro(true);
        }
    }

    public void addExternalInterrupt(ToDo aToDo) {
        ToDo todo = entityManager.merge(aToDo);
        Pomodoro latestPomodoro = getLatestPomodoro(todo);
        if (latestPomodoro != null) {
            latestPomodoro.setExternalInterrupts(latestPomodoro.getExternalInterrupts() + 1);
        }
    }

    public void addInternalInterrupt(ToDo aToDo) {
        ToDo todo = entityManager.merge(aToDo);
        Pomodoro latestPomodoro = getLatestPomodoro(todo);
        if (latestPomodoro != null) {
            latestPomodoro.setInternalInterrupts(latestPomodoro.getInternalInterrupts() + 1);
        }
    }

    public int getNumberOfFinishedPomodoros(ToDo aToDo) {
        int number = 0;
        ToDo todo = entityManager.merge(aToDo);
        for (Pomodoro p : todo.getPomodoro()) {
            if (p.isFinished()) {
                number++;
            }
        }
        return number;
    }

    public int getNumberOfVoidPomodoros(ToDo aToDo) {
        int number = 0;
        ToDo todo = entityManager.merge(aToDo);
        for (Pomodoro p : todo.getPomodoro()) {
            if (p.isVoidPomodoro()) {
                number++;
            }
        }
        return number;
    }
}
