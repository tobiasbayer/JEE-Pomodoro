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

import org.tby.jeesample.model.Pomodoro;
import org.tby.jeesample.model.ToDo;
import org.tby.jeesample.model.ToDoList;
import org.tby.jeesample.service.PomodoroService;
import org.tby.jeesample.service.ToDoListService;
import org.tby.jeesample.service.ToDoService;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class Today implements Serializable {

    @Inject
    private ToDoListService toDoListService;

    @Inject
    private ToDoService toDoService;

    @Inject
    private PomodoroService pomodoroService;

    private ToDoList currentToDoList;

    private Long toDoToAddId;

    private DataModel<ToDo> currentToDos;

    @PostConstruct
    public void init() {
        currentToDoList = getTodayToDoList();
        reload();
    }

    public void reload() {
        currentToDos = new ListDataModel<ToDo>();
        if (currentToDoList != null) {
            currentToDos.setWrappedData(toDoListService.getToDosForList(currentToDoList));
        }
    }

    public void removeFromList() {
        ToDo todo = currentToDos.getRowData();
        toDoListService.removeFromList(currentToDoList, todo);
        reload();
    }

    public DataModel<ToDo> getCurrentToDos() {
        return currentToDos;
    }

    public ToDoList getCurrentToDoList() {
        return currentToDoList;
    }

    public void setCurrentToDoList(ToDoList aCurrentToDoList) {
        currentToDoList = aCurrentToDoList;
    }

    public ToDoList getTodayToDoList() {
        Date now = new Date();
        ToDoList toDoList = toDoListService.findByDay(now);
        if (toDoList == null) {
            toDoList = toDoListService.create(now);
            toDoListService.save(toDoList);
        }

        currentToDoList = toDoList;
        return toDoList;
    }

    public List<ToDo> getAvailableToDos() {
        return toDoService.findAll();
    }

    public void addToDoToCurrentList() {
        ToDo todo = toDoService.find(getToDoToAddId());
        toDoListService.addToDoToList(todo, currentToDoList);
        reload();
    }

    public void setToDoToAddId(Long aToDoToAddId) {
        toDoToAddId = aToDoToAddId;
    }

    public Long getToDoToAddId() {
        return toDoToAddId;
    }

    public void startPomodoro() {
        ToDo todo = currentToDos.getRowData();
        toDoService.addPomodoro(todo, pomodoroService.create());
        reload();
    }

    public void finishCurrentPomodoro() {
        ToDo todo = currentToDos.getRowData();
        toDoService.finishCurrentPomodoro(todo);
        reload();
    }

    public String getPomodoroState() {
        String state = "";
        ToDo toDo = currentToDos.getRowData();
        Pomodoro latestPomodoro = toDoService.getLatestPomodoro(toDo);

        if (latestPomodoro == null) {
            state = "No Pomodoros";
        }
        else if (!latestPomodoro.isFinished() && !latestPomodoro.isVoidPomodoro()) {
            state = "Pomodoro running...: " + getInterrupts(latestPomodoro);
        }
        else if (latestPomodoro.isFinished()) {
            state = "Pomodoro finished: " + getInterrupts(latestPomodoro);
        }

        return state;
    }

    public void addExternalInterrupt() {
        toDoService.addExternalInterrupt(currentToDos.getRowData());
    }

    public void addInternalInterrupt() {
        toDoService.addInternalInterrupt(currentToDos.getRowData());
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
