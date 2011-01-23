package org.tby.jeesample.presentation;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.tby.jeesample.model.ToDoList;
import org.tby.jeesample.service.ToDoListService;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class Today implements Serializable {

    @Inject
    private ToDoListService mToDoListService;

    private DataModel<ToDoList> mDataModel;

    private ToDoList mCurrentToDoList;

    @PostConstruct
    public void init() {
        reload();
    }

    public DataModel<ToDoList> getToDos() {
        return mDataModel;
    }

    public void reload() {
        mDataModel = new ListDataModel<ToDoList>();
        mDataModel.setWrappedData(mToDoListService.findAll());
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

        return toDoList;
    }
}
