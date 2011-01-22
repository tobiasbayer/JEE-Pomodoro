package org.tby.jeesample.presentation;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.tby.jeesample.model.ToDo;
import org.tby.jeesample.service.ToDoService;

@ManagedBean
@SessionScoped
public class Activities {

    private final static String ACTIVITIES_PAGE = "activities.xhtml";

    private final static String EDIT_TODO_PAGE = "editToDo.xhtml";

    @EJB
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
        return EDIT_TODO_PAGE;
    }

    public String delete() {
        ToDo todo = mDataModel.getRowData();
        mToDoService.delete(todo);
        reload();
        return ACTIVITIES_PAGE;
    }

    public String edit() {
        mCurrentToDo = mDataModel.getRowData();
        reload();
        return EDIT_TODO_PAGE;
    }

    public String save() {
        mToDoService.update(mCurrentToDo);
        reload();
        return ACTIVITIES_PAGE;
    }

    public String cancel() {
        reload();
        return ACTIVITIES_PAGE;
    }

    public ToDo getCurrentToDo() {
        return mCurrentToDo;
    }

    public void setCurrentToDo(ToDo aCurrentToDo) {
        mCurrentToDo = aCurrentToDo;
    }
}
