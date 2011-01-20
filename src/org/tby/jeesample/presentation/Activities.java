package org.tby.jeesample.presentation;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.tby.jeesample.model.ToDo;
import org.tby.jeesample.service.ToDoService;

@Named
@SessionScoped
public class Activities {

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
        return null;
    }

    public String delete() {
        ToDo todo = mDataModel.getRowData();
        mToDoService.delete(todo);
        reload();
        return null;
    }

    public String edit() {
        mCurrentToDo = mDataModel.getRowData();
        reload();
        return null;
    }

    public String save() {
        mToDoService.save(mCurrentToDo);
        reload();
        return null;
    }

    public String cancel() {
        reload();
        return null;
    }

    public ToDo getCurrentToDo() {
        return mCurrentToDo;
    }

    public void setCurrentToDo(ToDo aCurrentToDo) {
        mCurrentToDo = aCurrentToDo;
    }
}
