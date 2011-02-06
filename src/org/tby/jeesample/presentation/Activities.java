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
    private ToDoService toDoService;

    private DataModel<ToDo> dataModel;

    private ToDo currentToDo;

    @PostConstruct
    public void init() {
        reload();
    }

    public DataModel<ToDo> getToDos() {
        return dataModel;
    }

    public void reload() {
        dataModel = new ListDataModel<ToDo>();
        dataModel.setWrappedData(toDoService.findAll());
    }

    public String create() {
        currentToDo = toDoService.create();
        return Pages.EDIT_TODO;
    }

    public String delete() {
        ToDo todo = dataModel.getRowData();
        toDoService.delete(todo);
        reload();
        return Pages.ACTIVITIES;
    }

    public String edit() {
        currentToDo = dataModel.getRowData();
        reload();
        return Pages.EDIT_TODO;
    }

    public String save() {
        toDoService.update(currentToDo);
        reload();
        return Pages.ACTIVITIES;
    }

    public String cancel() {
        reload();
        return Pages.ACTIVITIES;
    }

    public ToDo getCurrentToDo() {
        return currentToDo;
    }

    public void setCurrentToDo(ToDo aCurrentToDo) {
        currentToDo = aCurrentToDo;
    }
}
