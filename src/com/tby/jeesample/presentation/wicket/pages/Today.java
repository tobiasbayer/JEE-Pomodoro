package com.tby.jeesample.presentation.wicket.pages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;

import com.tby.jeesample.model.ToDo;
import com.tby.jeesample.model.ToDoList;
import com.tby.jeesample.service.ToDoListService;

public class Today extends WebPage {

    @Inject
    private ToDoListService toDoListService;

    private ToDoList currentToDoList;

    private List<ToDo> currentToDos;

    public Today() {

        currentToDoList = getTodayToDoList();
        currentToDos = new ArrayList<ToDo>(currentToDoList.getToDo());

        add(new ListView<ToDo>("todoList", new PropertyModel(this, "currentToDos")) {

            @Override
            protected void populateItem(ListItem<ToDo> aItem) {
                ToDo todo = aItem.getModelObject();
                aItem.add(new Label("description", todo.getDescription()));
            }

        });
    }

    public ToDoList getTodayToDoList() {
        Date now = new Date();
        ToDoList toDoList = toDoListService.findByDay(now);
        if (toDoList == null) {
            toDoList = toDoListService.create(now);
            toDoListService.save(toDoList);
        }

        return toDoList;
    }

}
