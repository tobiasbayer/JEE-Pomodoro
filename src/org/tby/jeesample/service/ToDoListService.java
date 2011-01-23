package org.tby.jeesample.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
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
    private EntityManager mEntityManager;

    @Inject
    private ToDoService mToDoService;

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
        // TODO find only for the current user
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

    public void addToDoToList(ToDo aToDo, ToDoList aList) {
        ToDo todo = mToDoService.find(aToDo.getId());
        ToDoList list = find(aList.getId());
        list.addToDo(todo);
        update(list);
    }

    public List<ToDo> getToDosForList(ToDoList aList) {
        return new ArrayList<ToDo>(find(aList.getId()).getToDo());
    }

    public void removeFromList(ToDoList aCurrentToDoList, ToDo aToDo) {
        ToDoList currentToDoList = mEntityManager.merge(aCurrentToDoList);
        ToDo toDo = mEntityManager.merge(aToDo);

        currentToDoList.getToDo().remove(toDo);
        mEntityManager.merge(currentToDoList);
    }
}
