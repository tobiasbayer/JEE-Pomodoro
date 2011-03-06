package com.tby.jeesample.presentation.wicket.pages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.tby.jeesample.common.ApplicationException;
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

        add(new Link<String>("linkActivities") {
            @Override
            public void onClick() {
                setResponsePage(ActivityInventory.class);
            }
        });

        Form<ToDo> form = new Form<ToDo>("addTodoForm");
        add(form);

        add(new FeedbackPanel("feedback"));

        IModel<List<ToDo>> availableTodos = new LoadableDetachableModel<List<ToDo>>() {
            public List<ToDo> load() {
                return toDoService.findAll();
            }
        };

        final DropDownChoice<ToDo> addTodoChoice = new DropDownChoice<ToDo>("todoToAdd", new Model<ToDo>(),
                availableTodos, new IChoiceRenderer<ToDo>() {

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

        form.add(new Button("addTodoToList") {
            @Override
            public void onSubmit() {
                ToDo toDo = addTodoChoice.getModelObject();
                toDoListService.addToDoToList(toDo, currentToDoList);
                setResponsePage(Today.class);
            }
        });

        add(new ListView<ToDo>("todoList", new PropertyModel<List<ToDo>>(this, "currentToDos")) {

            @Override
            protected void populateItem(ListItem<ToDo> aItem) {
                final ToDo todo = aItem.getModelObject();
                aItem.add(new Label("description", todo.getDescription()));
                aItem.add(new Label("estimate", String.valueOf(todo.getEstimate())));
                aItem.add(new Label("finished", String.valueOf(todo.isFinished())));
                aItem.add(new Label("state", getPomodoroState(todo)));
                aItem.add(new Label("finishedPomodoros", getNumberOfFinishedPomodoros(todo)));
                aItem.add(new Label("voidPomodoros", getNumberOfVoidPomodoros(todo)));
                aItem.add(new Link<ToDo>("addPomodoro") {

                    @Override
                    public void onClick() {
                        try {
                            toDoService.addPomodoro(todo, pomodoroService.create());
                        }
                        catch (ApplicationException e) {
                            error(e.getMessage());
                        }
                    }
                });

                aItem.add(new Link<ToDo>("addInternalInterrupt") {

                    @Override
                    public void onClick() {
                        try {
                            toDoService.addInternalInterrupt(todo);
                        }
                        catch (ApplicationException e) {
                            error(e.getMessage());
                        }
                    }
                });

                aItem.add(new Link<ToDo>("addExternalInterrupt") {

                    @Override
                    public void onClick() {
                        try {
                            toDoService.addExternalInterrupt(todo);
                        }
                        catch (ApplicationException e) {
                            error(e.getMessage());
                        }
                    }
                });

                aItem.add(new Link<ToDo>("finishPomodoro") {

                    @Override
                    public void onClick() {
                        toDoService.finishCurrentPomodoro(todo);
                    }
                });

                aItem.add(new Link<ToDo>("voidPomodoro") {

                    @Override
                    public void onClick() {
                        toDoService.voidCurrentPomodoro(todo);
                    }
                });

                aItem.add(new Link<ToDo>("removeTodo") {

                    @Override
                    public void onClick() {
                        toDoListService.removeFromList(currentToDoList, todo);
                        reload();
                    }
                });

                aItem.add(new Link<ToDo>("finishTodo") {

                    @Override
                    public void onClick() {
                        toDoService.finish(todo);
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

        if (aToDo.isFinished()) {
            state = "Todo finished";
        }
        else {
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
