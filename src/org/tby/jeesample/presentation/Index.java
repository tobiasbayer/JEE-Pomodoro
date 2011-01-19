package org.tby.jeesample.presentation;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.tby.jeesample.model.ToDo;
import org.tby.jeesample.service.ToDoService;

@Named
@RequestScoped
public class Index {

    @Inject
    private ToDoService mToDoService;

    private ToDo mToDo = new ToDo();

    public String getDescription() {
        return mToDo.getDescription();
    }

    public void setDescription(String aDescription) {
        mToDo.setDescription(aDescription);
    }

    public List<String> getAllActivities() {
        List<String> result = new ArrayList<String>();
        List<ToDo> all = mToDoService.findAll();
        for (ToDo todo : all) {
            result.add(todo.getDescription());
        }

        return result;
    }

    public void save() {
        mToDoService.save(mToDo);
    }
}
