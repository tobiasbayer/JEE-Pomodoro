package org.tby.jeesample.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.tby.jeesample.model.SystemUser;
import org.tby.jeesample.model.ToDo;

@Stateless
public class ToDoService {

    @PersistenceContext
    private EntityManager mEntityManager;

    public ToDo create() {
        return new ToDo();
    }

    public void delete(ToDo aToDo) {
        ToDo todo = mEntityManager.merge(aToDo);
        mEntityManager.remove(todo);
    }

    public ToDo find(Long aId) {
        return mEntityManager.find(ToDo.class, aId);
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
        mEntityManager.merge(aToDo);
    }

    public List<ToDo> findAll() {
        // TODO find only for the current user
        CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<ToDo> query = cb.createQuery(ToDo.class);
        query.select(query.from(ToDo.class));
        return mEntityManager.createQuery(query).getResultList();
    }
}
