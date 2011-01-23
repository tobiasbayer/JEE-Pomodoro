package org.tby.jeesample.presentation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.tby.jeesample.model.ToDo;
import org.tby.jeesample.model.ToDoList;
import org.tby.jeesample.service.ToDoListService;
import org.tby.jeesample.service.ToDoService;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class Today implements Serializable {

    @Inject
    private ToDoListService mToDoListService;

    @Inject
    private ToDoService mToDoService;

    private ToDoList mCurrentToDoList;

    private Long mToDoToAddId;

    private DataModel<ToDo> mCurrentToDos;

    @PostConstruct
    public void init() {
        mCurrentToDoList = getTodayToDoList();
        reload();
    }

    public void reload() {
        mCurrentToDos = new ListDataModel<ToDo>();
        if (mCurrentToDoList != null) {
            mCurrentToDos.setWrappedData(mToDoListService.getToDosForList(mCurrentToDoList));
        }
    }

    public void removeFromList() {
        ToDo todo = mCurrentToDos.getRowData();
        mToDoListService.removeFromList(mCurrentToDoList, todo);
        reload();
    }

    public DataModel<ToDo> getCurrentToDos() {
        return mCurrentToDos;
    }

    public ToDoList getCurrentToDoList() {
        return mCurrentToDoList;
    }

    public void setCurrentToDoList(ToDoList aCurrentToDoList) {
        mCurrentToDoList = aCurrentToDoList;
    }

    public ToDoList getTodayToDoList() {
        Date now = new Date();
        ToDoList toDoList = mToDoListService.findByDay(now);
        if (toDoList == null) {
            toDoList = mToDoListService.create(now);
            mToDoListService.save(toDoList);
        }

        mCurrentToDoList = toDoList;
        return toDoList;
    }

    public List<ToDo> getAvailableToDos() {
        return mToDoService.findAll();
    }

    public void addToDoToCurrentList() {
        ToDo todo = mToDoService.find(getToDoToAddId());
        mToDoListService.addToDoToList(todo, mCurrentToDoList);
        reload();
    }

    public void setToDoToAddId(Long aToDoToAddId) {
        mToDoToAddId = aToDoToAddId;
    }

    public Long getToDoToAddId() {
        return mToDoToAddId;
    }
}
