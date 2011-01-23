package org.tby.jeesample.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.DateTime;
import org.tby.jeesample.model.SystemUser;
import org.tby.jeesample.model.ToDoList;

@Stateless
public class ToDoListService {

    @PersistenceContext
    private EntityManager mEntityManager;

    public ToDoList create(Date aDay) {
        DateTime dt = new DateTime(aDay);
        dt.toDateMidnight();
        ToDoList toDoList = new ToDoList();
        toDoList.setActionDate(dt.toDateMidnight().toDate());

        return toDoList;
    }

    public void delete(ToDoList aToDoList) {
        ToDoList todoList = mEntityManager.merge(aToDoList);
        mEntityManager.remove(todoList);
    }

    public ToDoList find(Long aId) {
        return mEntityManager.find(ToDoList.class, aId);
    }

    public void save(ToDoList aToDoList) {
        // TODO replace with user from session once user management is
        // implemented
        if (aToDoList.getOwner() == null) {
            SystemUser user = new SystemUser();
            user.setFullName("Name");
            user.setLogin("Login");
            user.setPassword("Password");
            aToDoList.setOwner(user);
        }
        mEntityManager.persist(aToDoList);
    }

    public void update(ToDoList aToDoList) {
        mEntityManager.merge(aToDoList);
    }

    public List<ToDoList> findAll() {
        CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<ToDoList> query = cb.createQuery(ToDoList.class);
        query.select(query.from(ToDoList.class));
        return mEntityManager.createQuery(query).getResultList();
    }

    public ToDoList findByDay(Date aDay) {
        DateTime dt = new DateTime(aDay);
        dt.toDateMidnight();
        CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
        CriteriaQuery<ToDoList> query = cb.createQuery(ToDoList.class);
        Root<ToDoList> root = query.from(ToDoList.class);
        query.select(root);
        Predicate restriction = cb.equal(root.get("actionDate"), dt.toDateMidnight().toDate());
        query.where(restriction);
        List<ToDoList> resultList = mEntityManager.createQuery(query).getResultList();
        return resultList.size() > 0 ? resultList.get(0) : null;
    }
}
