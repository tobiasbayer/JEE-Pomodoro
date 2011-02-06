package org.tby.jeesample.service;

import java.util.ArrayList;
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
import org.tby.jeesample.model.ToDo;
import org.tby.jeesample.model.ToDoList;

@Stateless
public class ToDoListService {

    @PersistenceContext
    private EntityManager entityManager;

    public ToDoList create(Date aDay) {
        DateTime dt = new DateTime(aDay);
        dt.toDateMidnight();
        ToDoList toDoList = new ToDoList();
        toDoList.setActionDate(dt.toDateMidnight().toDate());

        return toDoList;
    }

    public void delete(ToDoList aToDoList) {
        ToDoList todoList = entityManager.merge(aToDoList);
        entityManager.remove(todoList);
    }

    public ToDoList find(Long aId) {
        return entityManager.find(ToDoList.class, aId);
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
        entityManager.persist(aToDoList);
    }

    public void update(ToDoList aToDoList) {
        entityManager.merge(aToDoList);
    }

    public List<ToDoList> findAll() {
        // TODO find only for the current user
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ToDoList> query = cb.createQuery(ToDoList.class);
        query.select(query.from(ToDoList.class));
        return entityManager.createQuery(query).getResultList();
    }

    public ToDoList findByDay(Date aDay) {
        DateTime dt = new DateTime(aDay);
        dt.toDateMidnight();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ToDoList> query = cb.createQuery(ToDoList.class);
        Root<ToDoList> root = query.from(ToDoList.class);
        query.select(root);
        Predicate restriction = cb.equal(root.get("actionDate"), dt.toDateMidnight().toDate());
        query.where(restriction);
        List<ToDoList> resultList = entityManager.createQuery(query).getResultList();
        return resultList.size() > 0 ? resultList.get(0) : null;
    }

    public void addToDoToList(ToDo aToDo, ToDoList aList) {
        ToDo todo = entityManager.merge(aToDo);
        ToDoList list = entityManager.merge(aList);
        list.addToDo(todo);
        update(list);
    }

    public List<ToDo> getToDosForList(ToDoList aList) {
        return new ArrayList<ToDo>(entityManager.merge(aList).getToDo());
    }

    public void removeFromList(ToDoList aCurrentToDoList, ToDo aToDo) {
        ToDoList currentToDoList = entityManager.merge(aCurrentToDoList);
        ToDo toDo = entityManager.merge(aToDo);

        currentToDoList.getToDo().remove(toDo);
        entityManager.merge(currentToDoList);
    }

    public int getTotalEstimate(ToDoList currentToDoList) {
        ToDoList list = entityManager.merge(currentToDoList);
        int estimate = 0;
        for (ToDo todo : list.getToDo()) {
            estimate += todo.getEstimate();
        }

        return estimate;
    }
}
