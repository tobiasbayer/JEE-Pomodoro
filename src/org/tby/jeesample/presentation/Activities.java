package org.tby.jeesample.presentation;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.tby.jeesample.model.ToDo;
import org.tby.jeesample.service.ToDoService;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class Activities implements Serializable {

    @Inject
    private ToDoService mToDoService;

    private DataModel<ToDo> mDataModel;

    private ToDo mCurrentToDo;

    @PostConstruct
    public void init() {
        reload();
    }

    public DataModel<ToDo> getToDos() {
        return mDataModel;
    }

    public void reload() {
        mDataModel = new ListDataModel<ToDo>();
        mDataModel.setWrappedData(mToDoService.findAll());
    }

    public String create() {
        mCurrentToDo = mToDoService.create();
        return Pages.EDIT_TODO;
    }

    public String delete() {
        ToDo todo = mDataModel.getRowData();
        mToDoService.delete(todo);
        reload();
        return Pages.ACTIVITIES;
    }

    public String edit() {
        mCurrentToDo = mDataModel.getRowData();
        reload();
        return Pages.EDIT_TODO;
    }

    public String save() {
        mToDoService.update(mCurrentToDo);
        reload();
        return Pages.ACTIVITIES;
    }

    public String cancel() {
        reload();
        return Pages.ACTIVITIES;
    }

    public ToDo getCurrentToDo() {
        return mCurrentToDo;
    }

    public void setCurrentToDo(ToDo aCurrentToDo) {
        mCurrentToDo = aCurrentToDo;
    }
}
