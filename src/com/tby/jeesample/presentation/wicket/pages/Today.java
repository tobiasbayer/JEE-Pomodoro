package com.tby.jeesample.presentation.wicket.pages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;

import com.tby.jeesample.model.Pomodoro;
import com.tby.jeesample.model.ToDo;
import com.tby.jeesample.model.ToDoList;
import com.tby.jeesample.service.PomodoroService;
import com.tby.jeesample.service.ToDoListService;
import com.tby.jeesample.service.ToDoService;

public class Today extends WebPage {

    @Inject
    private ToDoListService toDoListService;

    @Inject
    private ToDoService toDoService;

    @Inject
    private PomodoroService pomodoroService;

    private ToDoList currentToDoList;

    private List<ToDo> currentToDos;

    public Today() {

        reload();

        Form form = new Form("addTodoForm");
        add(form);

        DropDownChoice<ToDo> addTodoChoice = new DropDownChoice<ToDo>("todoToAdd", toDoService.findAll(),
                new IChoiceRenderer<ToDo>() {

                    @Override
                    public Object getDisplayValue(ToDo aToDo) {
                        return aToDo.getDescription();
                    }

                    @Override
                    public String getIdValue(ToDo aToDo, int arg1) {
                        return String.valueOf(aToDo.getId());
                    }
                });
        form.add(addTodoChoice);

        add(new ListView<ToDo>("todoList", new PropertyModel(this, "currentToDos")) {

            @Override
            protected void populateItem(ListItem<ToDo> aItem) {
                final ToDo todo = aItem.getModelObject();
                aItem.add(new Label("description", todo.getDescription()));
                aItem.add(new Label("estimate", String.valueOf(todo.getEstimate())));
                aItem.add(new Label("finished", String.valueOf(todo.isFinished())));
                aItem.add(new Label("state", getPomodoroState(todo)));
                aItem.add(new Label("finishedPomodoros", getNumberOfFinishedPomodoros(todo)));
                aItem.add(new Label("voidPomodoros", getNumberOfVoidPomodoros(todo)));
                aItem.add(new Link("addPomodoro") {

                    @Override
                    public void onClick() {
                        toDoService.addPomodoro(todo, pomodoroService.create());
                    }
                });

                aItem.add(new Link("addInternalInterrupt") {

                    @Override
                    public void onClick() {
                        toDoService.addInternalInterrupt(todo);
                    }
                });

                aItem.add(new Link("addExternalInterrupt") {

                    @Override
                    public void onClick() {
                        toDoService.addExternalInterrupt(todo);
                    }
                });

                aItem.add(new Link("finishPomodoro") {

                    @Override
                    public void onClick() {
                        toDoService.finishCurrentPomodoro(todo);
                    }
                });

                aItem.add(new Link("voidPomodoro") {

                    @Override
                    public void onClick() {
                        toDoService.voidCurrentPomodoro(todo);
                    }
                });

                aItem.add(new Link("removeTodo") {

                    @Override
                    public void onClick() {
                        toDoListService.removeFromList(currentToDoList, todo);
                        reload();
                    }
                });
            }

        });
    }

    public void reload() {
        currentToDoList = getTodayToDoList();
        toDoListService.getToDosForList(currentToDoList);
        currentToDos = new ArrayList<ToDo>(toDoListService.getToDosForList(currentToDoList));
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

    private String getPomodoroState(ToDo aToDo) {
        String state = "";
        Pomodoro latestPomodoro = toDoService.getLatestPomodoro(aToDo);

        if (latestPomodoro == null) {
            state = "No Pomodoros";
        }
        else if (!latestPomodoro.isFinished() && !latestPomodoro.isVoidPomodoro()) {
            state = "Pomodoro running...: " + getInterrupts(latestPomodoro);
        }
        else if (latestPomodoro.isFinished()) {
            state = "Pomodoro finished: " + getInterrupts(latestPomodoro);
        }
        else if (latestPomodoro.isVoidPomodoro()) {
            state = "Pomodoro void: " + getInterrupts(latestPomodoro);
        }

        return state;
    }

    private String getNumberOfFinishedPomodoros(ToDo aToDo) {
        return String.valueOf(toDoService.getNumberOfFinishedPomodoros(aToDo));
    }

    private String getNumberOfVoidPomodoros(ToDo aToDo) {
        return String.valueOf(toDoService.getNumberOfVoidPomodoros(aToDo));
    }

    private String getInterrupts(Pomodoro pomodoro) {
        if (pomodoro == null) {
            return "";
        }

        StringBuffer interrupts = new StringBuffer();
        for (int i = 0; i < pomodoro.getExternalInterrupts(); i++) {
            interrupts.append("X");
        }

        for (int i = 0; i < pomodoro.getInternalInterrupts(); i++) {
            interrupts.append("-");
        }

        return interrupts.toString();
    }

}
