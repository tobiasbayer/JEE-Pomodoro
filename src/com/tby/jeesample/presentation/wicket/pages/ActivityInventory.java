package com.tby.jeesample.presentation.wicket.pages;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import com.tby.jeesample.model.ToDo;
import com.tby.jeesample.service.ToDoService;

public class ActivityInventory extends WebPage {

    @Inject
    private ToDoService toDoService;

    public ActivityInventory() {
        add(new Link<String>("linkToday") {
            @Override
            public void onClick() {
                setResponsePage(Today.class);
            }
        });

        Form<ToDo> form = new Form<ToDo>("addTodoForm");
        add(form);

        IModel<List<ToDo>> availableTodos = new LoadableDetachableModel<List<ToDo>>() {
            public List<ToDo> load() {
                return toDoService.findAll();
            }
        };

        final TextField<String> descriptionTextField = new TextField<String>("newDescription", new Model<String>());
        form.add(descriptionTextField);

        final TextField<String> estimateTextField = new TextField<String>("newEstimate", new Model<String>());
        form.add(estimateTextField);

        form.add(new Button("addToDo") {
            @Override
            public void onSubmit() {
                ToDo todo = toDoService.create();
                todo.setDescription(descriptionTextField.getModelObject());
                todo.setEstimate(Integer.valueOf(estimateTextField.getModelObject()));
                toDoService.save(todo);
            }
        });

        add(new ListView<ToDo>("todoList", availableTodos) {

            @Override
            protected void populateItem(ListItem<ToDo> aItem) {
                final ToDo todo = aItem.getModelObject();
                aItem.add(new Label("description", todo.getDescription()));
                aItem.add(new Label("estimate", String.valueOf(todo.getEstimate())));
                aItem.add(new Label("finished", String.valueOf(todo.isFinished())));
                aItem.add(new Link<ToDo>("deleteToDo") {
                    @Override
                    public void onClick() {
                        toDoService.delete(todo);
                    }
                });
            }

        });
    }
}
