package org.tby.jeesample.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.tby.jeesample.model.ToDo;
import org.tby.jeesample.model.SystemUser;

@Stateless
public class ToDoService {

    @PersistenceContext
    private EntityManager mEntityManager;

    public void save(ToDo aToDo) {
        // TODO replace with user from session once user management is
        // implemented
        if (aToDo.getOwner() == null) {
            SystemUser user = new SystemUser();
            user.setFullName("Name");
            user.setLogin("Login");
            user.setPassword("Password");
            aToDo.setOwner(user);
        }
        mEntityManager.persist(aToDo);
    }

    public List<ToDo> findAll() {
        CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<ToDo> query = cb.createQuery(ToDo.class);
        query.select(query.from(ToDo.class));
        return mEntityManager.createQuery(query).getResultList();
    }
}